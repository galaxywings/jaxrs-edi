
from rest_framework_filters import FilterSet

from customer.models import Customer


class CustomerGenericFilterSet(FilterSet):

    class Meta:
        model = Customer
        fields = {
            'id': ('exact', 'in',),
            'code': ('exact', 'icontains', 'contains', 'in',),
            'name': ('exact', 'icontains', 'contains', 'in',),
            'active': ('exact',),
        }
        