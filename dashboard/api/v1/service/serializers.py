from jsonschema.exceptions import ValidationError as JsonValidationError
from jsonschema.validators import validate
from rest_framework.exceptions import ValidationError
from rest_framework.fields import CharField, JSONField, BooleanField
from rest_framework_bulk.serializers import BulkSerializerMixin, \
    BulkListSerializer
from api.v1.misc.serializers import ContentTypeSerializer
from common.serializers import DynamicFieldsModelSerializer
from service.models import ServiceSchema, GenericService, Ftp
from service.utils.ftp import exec_ftp_cmds, create_ftp_transfer_folders


class ServiceSchemaSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    code = CharField(required=True)
    # drf's JSONField only auto bound with postgres
    # please refer to rest_framework.compat.py
    extra_schema = JSONField(required=False)
    content_type = ContentTypeSerializer()

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
    
    def validate(self, data):
        if 'extra_params' in data and data['extra_params']:
            if not self.instance and 'extra_schema' not in data:
                raise ValidationError(detail='`extra_schema` is required when validating `extra_params`')
            if 'extra_schema' in data:
                service_schema = data['extra_schema'] 
            else:
                service_schema = self.instance.extra_schema
            try:
                validate(data['extra_params'], service_schema.extra_schema)
            except JsonValidationError as e:
                raise ValidationError(detail='invalid extra_params, %s ' % e.message)
        
        return data

class FtpServiceSerializer(GenericServiceSerializer):
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