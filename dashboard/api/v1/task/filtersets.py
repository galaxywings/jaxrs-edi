
from rest_framework_filters import FilterSet, RelatedFilter

from customer.models import Customer
from task.models import Service, Step, Process


class TaskServiceGenericFilterSet(FilterSet):

    class Meta:
        model = Service
        fields = {
            'id': ['exact', 'in'],
            'code': ['exact', 'icontains', 'contains', 'in'],
            'name': ['exact', 'icontains', 'contains', 'in'],
        }

class TaskProcessGenericFilterSet(FilterSet):
    customer = RelatedFilter('api.v1.customer.filtersets.CustomerGenericFilterSet', queryset=Customer.objects.all())
    steps = RelatedFilter('api.v1.task.filtersets.TaskStepGenericFilterSet', queryset=Step.objects.all())
    
    class Meta:
        model = Process
        fields = {
            'id': ['exact', 'in'],
            'name': ['exact', 'icontains', 'contains', 'in'],
            'active': ['exact'],
        }

class TaskStepGenericFilterSet(FilterSet):
    process = RelatedFilter('api.v1.task.filtersets.TaskProcessGenericFilterSet', queryset=Process.objects.all())
    service = RelatedFilter('api.v1.task.filtersets.TaskServiceGenericFilterSet', queryset=Service.objects.all())
    
    class Meta:
        model = Step
        fields = {
            'id': ['exact', 'in'],
            'seq': '__all__', #['exact', 'in', 'lt', 'lte', 'gte', 'gte']
        }
        
