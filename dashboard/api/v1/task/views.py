
from django.db.transaction import atomic
from rest_framework.decorators import list_route, detail_route
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework_bulk.generics import BulkModelViewSet

from api.v1.task.filtersets import ProcessGenericFilterSet, \
    StepGenericFilterSet
from api.v1.task.serializers import ProcessSerializer, StepSerializer, \
    StepServiceSerializer
from authx.permissions import IsAdminUser
from task.models import Process, Step


class ProcessViewSet(BulkModelViewSet):
    queryset = Process.objects.all()
    serializer_class = ProcessSerializer
    filter_class = ProcessGenericFilterSet
    search_fields = ('name', )
    #permission_classes = (IsAdminUser, )
     
    @detail_route(
        methods=('post', ),
        url_path='save'
    )
    @atomic
    def save_with_steps(self, request, *args, **kwargs):
        # copy from restframework.mixins.UpdateModelMixin.update
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        self.perform_update(serializer)
        
        steps = request.data.get('steps')
        for index, step in enumerate(steps):
            step['seq'] = index
            step['process'] = instance.id
    
        instance.step_set.all().delete()
        if steps:
            step_serializer = StepSerializer(data=steps, many=True, 
                        context=self.get_serializer_context())
            step_serializer.is_valid(raise_exception=True)
            step_serializer.save()
        result = serializer.data
        result['steps'] = step_serializer.data
        return Response(result)

class StepViewSet(BulkModelViewSet):
    queryset = Step.objects.all()
    serializer_class = StepSerializer
    filter_class = StepGenericFilterSet
    #permission_classes = (IsAdminUser, )
    
    @list_route(
        methods=('get', ),
        url_path='detail'
    )
    def list_detail(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)
    
    @detail_route(
        methods=('get', ),
        url_path='detail'
    )
    def object_detail(self, request, *args, **kwargs):
        return self.retrieve(request, *args, **kwargs)
    
    def get_serializer_class(self):
        if self.action in {'list_detail', 'object_detail', }:
            return StepServiceSerializer
        return super().get_serializer_class()
        