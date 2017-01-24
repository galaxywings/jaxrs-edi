from rest_framework.fields import CharField, JSONField
from rest_framework_bulk.serializers import BulkSerializerMixin, \
    BulkListSerializer

from common.serializers import DynamicFieldsModelSerializer
from service.models import ServiceSchema, GenericService, Ftp


class ServiceSchemaSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    code = CharField(required=True)
    # drf's JSONField only auto bound with postgres
    # please refer to rest_framework.compat.py
    extra_schema = JSONField(required=False)
    
    class Meta:
        model = ServiceSchema
        list_serializer_class = BulkListSerializer
        fields = '__all__'

class GenericServiceSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    name = CharField(required=True)
    # drf's JSONField only auto bound with postgres
    # please refer to rest_framework.compat.py
    extra_params = JSONField(required=False)
    
    class Meta:
        model = GenericService
        list_serializer_class = BulkListSerializer
        fields = '__all__'

class FtpServiceSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    name = CharField(required=True)
    # drf's JSONField only auto bound with postgres
    # please refer to rest_framework.compat.py
    extra_params = JSONField(required=False)
    
    class Meta:
        model = Ftp
        list_serializer_class = BulkListSerializer
        fields = '__all__'