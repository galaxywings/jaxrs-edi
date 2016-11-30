from rest_framework import serializers
from rest_framework_bulk.serializers import BulkSerializerMixin, BulkListSerializer
from customer.models import Customer


class CustomerSerializer(BulkSerializerMixin, serializers.ModelSerializer):
    class Meta:
        model = Customer
        list_serializer_class = BulkListSerializer
        fields = '__all__'


