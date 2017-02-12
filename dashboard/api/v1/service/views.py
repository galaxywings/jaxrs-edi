
from django.conf import settings
from django.contrib.contenttypes.models import ContentType
import requests
from rest_framework.decorators import list_route
from rest_framework.exceptions import ValidationError
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework_bulk.generics import BulkModelViewSet

from api.v1.service.filtersets import ServiceSchemaGenericFilterSet, \
    GenericServiceFilterSet, FtpServiceFilterSet
from api.v1.service.serializers import ServiceSchemaSerializer, \
    GenericServiceSerializer, FtpServiceSerializer
from authx.permissions import IsAdminUser
from service.models import ServiceSchema, GenericService, Ftp
from service.utils.ftp import exec_ftp_cmds, test_ftp_connection


#from rest_framework.permissions import IsAuthenticated
class ServiceSchemaViewSet(BulkModelViewSet):
    queryset = ServiceSchema.objects.all()
    serializer_class = ServiceSchemaSerializer
    filter_class = ServiceSchemaGenericFilterSet
    search_fields = ('code', 'name')
    #permission_classes = (IsAuthenticated, )
    
    @list_route(
        methods=('get', 'post', ),
        url_path='sync'
    )
    def sync_schemas(self, request):
        r = requests.get(settings.SCHEMA_SYNC_API_ENDPOINT)
        result = {
            'created': 0,
            'updated': 0,
            'errors': [],
        }
        ct_qs = ContentType.objects.filter(app_label='service').only('id', 'model')
        model_ct_dict = { ct.model: ct for ct in ct_qs }
        for schema_json in r.json():
            model_str = schema_json.pop('category', None)
            if model_str not in model_ct_dict:
                result['errors'].append('category: %s is not valid' % (model_str, ))
                continue
            schema_json['content_type'] = model_ct_dict[model_str]
            try:
                _, created = ServiceSchema.objects.update_or_create(
                        code = schema_json['code'],
                        defaults=schema_json)
            except Exception as e:
                result['errors'].append(str(e))
            else: 
                if created:
                    result['created'] += 1
                else:
                    result['updated'] += 1
        
        return Response(result)

class GenericServiceViewSet(BulkModelViewSet):
    queryset = GenericService.objects.all()
    serializer_class = GenericServiceSerializer
    filter_class = GenericServiceFilterSet
    search_fields = ('name')

class FtpServiceViewSet(BulkModelViewSet):
    queryset = Ftp.objects.all()
    serializer_class = FtpServiceSerializer
    filter_class = FtpServiceFilterSet
    search_fields = ('name', 'username', 'host',)
    
    @list_route(
        methods=('post', ),
        url_path='test-connection'
    )
    def test_connection(self, request):
        data = request.data
        required_param_names = ('username', 'password', 'host', 'port',)
        params = {}
        missing_params = []
        for param_name in required_param_names:
            if param_name not in data:
                missing_params.append(param_name)
                continue
            params[param_name] = data.get(param_name)
        if len(missing_params) > 0:
            raise ValidationError(detail='%s is/are required' % (missing_params, ))
        ftp_service = Ftp(**params)
        exec_ftp_cmds(test_ftp_connection, ftp_service)
        return Response('OK')
