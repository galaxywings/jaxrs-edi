from jsonschema.exceptions import ValidationError as JsonValidationError
from jsonschema.validators import validate
from rest_framework.exceptions import ValidationError
from rest_framework.fields import CharField, JSONField, BooleanField, \
    SerializerMethodField
from rest_framework_bulk.serializers import BulkSerializerMixin, \
    BulkListSerializer

from common.serializers import DynamicFieldsModelSerializer
from customer.utils import get_id_customer_dict
from service.models import ServiceSchema, GenericService, Ftp, Protocol, \
    Template
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
    customer_code = SerializerMethodField()
    
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
    
    def get_customer_code(self, obj):
        id_customer_dict = get_id_customer_dict()
        customer = id_customer_dict.get(obj.customer_id, None)
        return (customer and customer.code) or ''

class ProtocolServiceSerializer(GenericServiceSerializer):
    sender_code = SerializerMethodField()
    receiver_code = SerializerMethodField()
    class Meta:
        model = Protocol
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    
    def get_sender_code(self, obj):
        id_customer_dict = get_id_customer_dict()
        customer = id_customer_dict.get(obj.sender_id, None)
        return (customer and customer.code) or ''
    
    def get_receiver_code(self, obj):
        id_customer_dict = get_id_customer_dict()
        customer = id_customer_dict.get(obj.receiver_id, None)
        return (customer and customer.code) or ''
        
class TemplateServiceSerializer(GenericServiceSerializer):
    extra_schema_name = SerializerMethodField()
    
    class Meta:
        model = Template
        list_serializer_class = BulkListSerializer
        fields = '__all__'

    def get_extra_schema_name(self, obj):
        return obj.extra_schema.name or ''                