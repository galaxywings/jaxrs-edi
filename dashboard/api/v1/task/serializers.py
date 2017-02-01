
from rest_framework.relations import RelatedField, StringRelatedField
from rest_framework_bulk.serializers import BulkSerializerMixin, BulkListSerializer

from api.v1.service.serializers import FtpServiceSerializer, \
    GenericServiceSerializer
from common.serializers import DynamicFieldsModelSerializer
from service.models import Ftp, GenericService
from task.models import Step, Process

class StepSerializer(DynamicFieldsModelSerializer):
    class Meta:
        model = Step
        fields = '__all__'

class ServiceObjectRelatedField(RelatedField):
    def to_representation(self, value):
        if isinstance(value, Ftp):
            serializer = FtpServiceSerializer(value)
        elif isinstance(value, GenericService):
            serializer = GenericServiceSerializer(value)
        else:
            raise Exception('Unexpected type of service object')
        return serializer.data

class StepServiceSerializer(DynamicFieldsModelSerializer):
    content_object = ServiceObjectRelatedField(read_only=True)
    
    class Meta:
        model = Step
        fields = '__all__'

class ProcessSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    customer_code = StringRelatedField(source='customer')
    class Meta:
        model = Process
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    