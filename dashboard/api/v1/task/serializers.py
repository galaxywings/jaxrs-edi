from rest_framework.fields import CharField, JSONField
from rest_framework_bulk.serializers import BulkSerializerMixin, BulkListSerializer

from common.serializers import DynamicFieldsModelSerializer
from task.models import Service


class TaskServiceSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    code = CharField(required=True)
    params_schema = JSONField()
    
    class Meta:
        model = Service
        list_serializer_class = BulkListSerializer
        fields = '__all__'
        # avoid unique_together validation here
        validators = []


