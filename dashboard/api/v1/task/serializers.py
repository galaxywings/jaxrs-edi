from rest_framework.fields import CharField, JSONField, SerializerMethodField
from rest_framework.relations import StringRelatedField, PrimaryKeyRelatedField
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
    service = PrimaryKeyRelatedField(queryset=Service.objects.all())
    service_code = StringRelatedField(source='service')
    params_value = JSONField()
    class Meta:
        model = Step
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    
    def validate_params_value(self, value):
        return value
    
class ProcessStepSerializer(ModelSerializer):
    service_code = StringRelatedField(source='service')
    params_value = JSONField()
    class Meta:
        model = Step
        exclude = ('id', 'process', )

class TaskProcessSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    customer_code = StringRelatedField(source='customer')
    steps = SerializerMethodField()
    class Meta:
        model = Process
        list_serializer_class = BulkListSerializer
        exclude = ('services', )
    
    def get_steps(self, instance):
        steps = instance.step_set.order_by('seq')
        serializer = ProcessStepSerializer(steps, many=True)
        return serializer.data
