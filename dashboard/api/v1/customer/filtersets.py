
from django_filters.filterset import FilterSet

from customer.models import Customer


class CustomerGenericFilterSet(FilterSet):

    class Meta:
        model = Customer
        fields = {
            'id': ['exact', 'in'],
            'code': ['exact', 'icontains'],
            'name': ['exact', 'icontains'],
            'active': ['exact'],
        }
        