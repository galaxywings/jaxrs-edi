from rest_framework.fields import CharField, JSONField
from rest_framework.relations import StringRelatedField
from rest_framework.serializers import ModelSerializer
from rest_framework_bulk.serializers import BulkSerializerMixin, BulkListSerializer

from common.serializers import DynamicFieldsModelSerializer
from task.models import Service, Step, Process


class TaskServiceSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    code = CharField(required=True)
    params_schema = JSONField()
    
    class Meta:
        model = Service
        list_serializer_class = BulkListSerializer
        fields = '__all__'
        # avoid unique_together validation here
        validators = []

class TaskStepSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    params_value = JSONField()
    
    class Meta:
        model = Step
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    
class ProcessStepSerializer(ModelSerializer):
    service = StringRelatedField()
    params_value = JSONField()
    class Meta:
        model = Step
        excludes = ('process', )

class TaskProcessSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    customer_code = StringRelatedField(source='customer')
    steps = ProcessStepSerializer(many=True, read_only=True)
    class Meta:
        model = Process
        list_serializer_class = BulkListSerializer
        fields = '__all__'

