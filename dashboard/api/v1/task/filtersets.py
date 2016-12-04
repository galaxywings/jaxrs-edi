
from django_filters.filterset import FilterSet

from task.models import Service


class TaskServiceGenericFilterSet(FilterSet):

    class Meta:
        model = Service
        fields = {
            'id': ['exact', 'in'],
            'code': ['exact', 'icontains', 'in'],
            'name': ['exact', 'icontains'],
        }
        