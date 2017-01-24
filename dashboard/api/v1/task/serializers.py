
from rest_framework.relations import StringRelatedField
from rest_framework.serializers import ModelSerializer
from rest_framework_bulk.serializers import BulkSerializerMixin, BulkListSerializer

from common.serializers import DynamicFieldsModelSerializer
from task.models import Step, Process
    
class StepSerializer(ModelSerializer):
    class Meta:
        model = Step
        fields = '__all__'

class ProcessSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    customer_code = StringRelatedField(source='customer')
    class Meta:
        model = Process
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    
