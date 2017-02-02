
from rest_framework.permissions import IsAuthenticated
from rest_framework.viewsets import ReadOnlyModelViewSet

from api.v1.customer.filtersets import CustomerGenericFilterSet
from api.v1.customer.serializers import CustomerSerializer
from api.v1.mule.serializers import ProcessSerializer, StepSerializer
from api.v1.task.filtersets import ProcessGenericFilterSet, \
    StepGenericFilterSet
from customer.models import Customer
from task.models import Process, Step


class CustomerViewSet(ReadOnlyModelViewSet):
    queryset = Customer.objects.all()
    serializer_class = CustomerSerializer
    filter_class = CustomerGenericFilterSet
    search_fields = ('code', 'name')
    #permission_classes = (IsAdminUser, )

class ProcessViewSet(ReadOnlyModelViewSet):
    queryset = Process.objects.all()
    serializer_class = ProcessSerializer
    filter_class = ProcessGenericFilterSet
    search_fields = ('name', )
    #permission_classes = (IsAdminUser, )

class StepViewSet(ReadOnlyModelViewSet):
    queryset = Step.objects.all()
    serializer_class = StepSerializer
    filter_class = StepGenericFilterSet
    #permission_classes = (IsAdminUser, )