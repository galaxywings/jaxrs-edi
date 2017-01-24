
from rest_framework_filters import FilterSet, RelatedFilter

from customer.models import Customer
from task.models import Step, Process


class ProcessGenericFilterSet(FilterSet):
    customer = RelatedFilter('api.v1.customer.filtersets.CustomerGenericFilterSet', queryset=Customer.objects.all())
    
    class Meta:
        model = Process
        fields = {
            'id': ['exact', 'in'],
            'name': ['exact', 'icontains', 'contains', 'in'],
            'active': ['exact'],
        }

class StepGenericFilterSet(FilterSet):
    process = RelatedFilter('api.v1.task.filtersets.TaskProcessGenericFilterSet', queryset=Process.objects.all())
    
    class Meta:
        model = Step
        fields = {
            'id': ['exact', 'in'],
            'seq': '__all__', #['exact', 'in', 'lt', 'lte', 'gte', 'gte']
        }
        
