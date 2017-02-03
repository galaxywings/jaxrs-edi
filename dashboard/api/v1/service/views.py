
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
        