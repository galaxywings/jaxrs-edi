
from rest_framework.decorators import list_route
from rest_framework.response import Response
from rest_framework_bulk.generics import BulkModelViewSet

from api.v1.customer.filtersets import CustomerGenericFilterSet
from api.v1.customer.serializers import CustomerSerializer
from authx.permissions import IsAdminUser
from customer.models import Customer


class CustomerViewSet(BulkModelViewSet):
    queryset = Customer.objects.all()
    serializer_class = CustomerSerializer
    filter_class = CustomerGenericFilterSet
    search_fields = ('code', 'name')
    # permission_classes = (IsAdminUser, )
    
    @list_route(
        methods=['get'],
        url_path='validate-code'
    )
    def validate_code(self, request):
        qs = self.get_queryset().filter(code=request.query_params.get('q'))
        if qs.exists():
            result = {'detail': 'Duplicated code found'}
        else:
            result = {'detail': 'OK'}
        return Response(result)
