
from rest_framework.permissions import IsAuthenticated
from rest_framework_bulk.generics import BulkModelViewSet

from api.v1.service.filtersets import ServiceSchemaGenericFilterSet, \
    GenericServiceFilterSet, FtpServiceFilterSet
from api.v1.service.serializers import ServiceSchemaSerializer, \
    GenericServiceSerializer, FtpServiceSerializer
from authx.permissions import IsAdminUser
from service.models import ServiceSchema, GenericService, Ftp


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