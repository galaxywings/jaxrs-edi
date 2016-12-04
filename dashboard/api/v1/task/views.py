
from rest_framework.response import Response
from rest_framework_bulk.generics import BulkModelViewSet

from api.v1.task.filtersets import TaskServiceGenericFilterSet
from api.v1.task.serializers import TaskServiceSerializer
from task.models import Service


#from rest_framework.permissions import IsAuthenticated
class ServiceViewSet(BulkModelViewSet):
    queryset = Service.objects.all()
    serializer_class = TaskServiceSerializer
    filter_class = TaskServiceGenericFilterSet
    search_fields = ('code', 'name')
    # permission_classes = (IsAuthenticated, )
    
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
