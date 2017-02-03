from rest_framework.fields import CharField, JSONField, BooleanField
from rest_framework_bulk.serializers import BulkSerializerMixin, \
    BulkListSerializer

from common.serializers import DynamicFieldsModelSerializer
from service.models import ServiceSchema, GenericService, Ftp
from service.utils.ftp import exec_ftp_cmds, create_ftp_transfer_folders


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
    
    folder_creation = BooleanField(write_only=True, required=False)
    
    class Meta:
        model = Ftp
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    
    def create(self, validated_data):
        folder_creation = validated_data.pop('folder_creation', False)
        result = super().create(validated_data)
        if folder_creation:
            exec_ftp_cmds(create_ftp_transfer_folders, result)
        return result
    
    def update(self, instance, validated_data):
        folder_creation = validated_data.pop('folder_creation', False)
        result = super().update(instance, validated_data)
        if folder_creation:
            exec_ftp_cmds(create_ftp_transfer_folders, result)
        return result