from rest_framework_bulk.serializers import BulkSerializerMixin, BulkListSerializer

from common.serializers import DynamicFieldsModelSerializer
from customer.models import Customer


class CustomerSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    class Meta:
        model = Customer
        list_serializer_class = BulkListSerializer
        fields = '__all__'


