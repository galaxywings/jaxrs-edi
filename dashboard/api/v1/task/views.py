
from rest_framework.permissions import IsAuthenticated
from rest_framework_bulk.generics import BulkModelViewSet

from api.v1.task.filtersets import ProcessGenericFilterSet, \
    StepGenericFilterSet
from api.v1.task.serializers import ProcessSerializer, StepSerializer
from authx.permissions import IsAdminUser
from task.models import Process, Step


class ProcessViewSet(BulkModelViewSet):
    queryset = Process.objects.all()
    serializer_class = ProcessSerializer
    filter_class = ProcessGenericFilterSet
    search_fields = ('name', )
    #permission_classes = (IsAdminUser, )
     
   

class StepViewSet(BulkModelViewSet):
    queryset = Step.objects.all()
    serializer_class = StepSerializer
    filter_class = StepGenericFilterSet
    #permission_classes = (IsAdminUser, )