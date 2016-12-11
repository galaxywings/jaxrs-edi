
import jsonschema
from rest_framework.decorators import detail_route
from rest_framework.exceptions import ValidationError
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework_bulk.generics import BulkModelViewSet

from api.v1.task.filtersets import TaskServiceGenericFilterSet, \
    TaskProcessGenericFilterSet, TaskStepGenericFilterSet
from api.v1.task.serializers import TaskServiceSerializer, TaskProcessSerializer, \
    TaskStepSerializer, ProcessStepSerializer
from authx.permissions import IsAdminUser
from task.models import Service, Process, Step
from django.db.transaction import atomic


#from rest_framework.permissions import IsAuthenticated
class ServiceViewSet(BulkModelViewSet):
    queryset = Service.objects.all()
    serializer_class = TaskServiceSerializer
    filter_class = TaskServiceGenericFilterSet
    search_fields = ('code', 'name')
    #permission_classes = (IsAuthenticated, )
    
    def create(self, request, *args, **kwargs):
        '''
            this method could be repeatly invoked for the same schema
        '''
        # for validation
        items = request.data if isinstance(request.data, list) else [request.data]
        serializer = self.get_serializer(data=items, many=True)
        serializer.is_valid(raise_exception=True)
        
        result = []
        for item in serializer.data:
            s, created = Service.objects.get_or_create(code=item.get('code', ''),
                            defaults={
                                'name': item.get('name', ''),
                                'params_schema': item.get('params_schema', {}),
                            })
            if not created:
                s.name = item.get('name', '')
                s.params_schema = item.get('params_schema', {})
                s.save()
            result.append(s)
        serializer = self.get_serializer(result, many=True)
        return Response(serializer.data)

class ProcessViewSet(BulkModelViewSet):
    queryset = Process.objects.all()
    serializer_class = TaskProcessSerializer
    filter_class = TaskProcessGenericFilterSet
    search_fields = ('name', 'customer__code',)
    #permission_classes = (IsAdminUser, )
    
    @detail_route(
        ['POST', 'PUT'],
        #permission_classes = (IsAdminUser, ),
        url_path='save-steps'
    )
    @atomic
    def save_steps(self, request, *args, **kwargs):
        instance = self.get_object()
        id_service_dict = dict((service.id, service) for service in Service.objects.all())
            
        steps = request.data or []
        errors = []
        for index, step in enumerate(steps):
            # a little validation
            err_msg = 'index: %s,' % (index, )
            service_id = step.get('service', 0)
            if service_id not in id_service_dict:
                errors.append(ValidationError('%s invalid service id: %s' % (err_msg, service_id) ))
                continue
            service = id_service_dict[service_id]
            try:
                jsonschema.validate(step.get('params_value', {}), service.params_schema)
            except Exception as e:
                errors.append(ValidationError('%s %s'% (err_msg, e) ))
        if errors:
            raise ValidationError(errors)
        result = []
        for index, step in enumerate(steps):
            service = id_service_dict[step.get('service', 0)]
            s, created = Step.objects.get_or_create(process=instance, seq=index,
                                       defaults={'service': service, 
                                                 'params_value': step.get('params_value', {})
                                    })
            if not created:
                s.service = service
                s.params_value = step.get('params_value', {})
                s.save()
            result.append(s)
        # remove extra steps if any
        Step.objects.filter(process=instance, seq__gt=index).delete()
        serializer = TaskStepSerializer(result, many=True)
        return Response(serializer.data)

class StepViewSet(BulkModelViewSet):
    queryset = Step.objects.all()
    serializer_class = TaskStepSerializer
    filter_class = TaskStepGenericFilterSet
    #permission_classes = (IsAdminUser, )