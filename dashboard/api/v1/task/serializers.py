
from generic_relations.relations import GenericRelatedField
from rest_framework.relations import StringRelatedField
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

class StepServiceSerializer(DynamicFieldsModelSerializer):
    content_object = GenericRelatedField({
        Ftp: FtpServiceSerializer(),
        GenericService: GenericServiceSerializer()
    })
    
    class Meta:
        model = Step
        fields = '__all__'

class ProcessSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    customer_code = StringRelatedField(source='customer')
    class Meta:
        model = Process
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    