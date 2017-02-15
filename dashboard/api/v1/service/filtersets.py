
from django.contrib.contenttypes.models import ContentType
from rest_framework_filters.filters import RelatedFilter

from api.v1.misc.filtersets import ContentTypeFilterSet
from customer.models import Customer
import rest_framework_filters as filters
from service.models import ServiceSchema, GenericService, Ftp, Protocol, \
    Template


class ServiceSchemaGenericFilterSet(filters.FilterSet):

    content_type = filters.RelatedFilter(ContentTypeFilterSet, name='content_type', 
                                         # should restrict to app_label='service' 
                                         queryset=ContentType.objects.filter(app_label='service').exclude(model='serviceschema'))
    class Meta:
        model = ServiceSchema
        fields = {
            'id': ('exact', 'in',),
            'code': ('exact', 'icontains', 'contains', 'in',),
            'name': ('exact', 'icontains', 'contains', 'in',),
        }

class GenericServiceFilterSet(filters.FilterSet):
    extra_schema = filters.RelatedFilter(ServiceSchemaGenericFilterSet,
                                         queryset=ServiceSchema.objects.all())
    class Meta:
        model = GenericService
        fields = {
            'id': ('exact', 'in',),
            'name': ('exact', 'icontains', 'contains', 'in',),
        }

class FtpServiceFilterSet(filters.FilterSet):
    extra_schema = filters.RelatedFilter(ServiceSchemaGenericFilterSet,
                                         queryset=ServiceSchema.objects.all())
    class Meta:
        model = Ftp
        fields = {
            'id': ('exact', 'in',),
            'name': ('exact', 'icontains', 'contains', 'in',),
            'username': ('exact', 'icontains', 'contains', 'in', 'startswith',),
            'password': ('exact', 'icontains', 'contains', 'in', 'startswith',),
            'host': ('exact', 'icontains', 'contains', 'in', 'startswith',),
            'port': ('exact', 'in',),
            'path': ('exact', 'icontains', 'contains', 'in', 'startswith',),
        }

class ProtocolServiceFilterSet(filters.FilterSet):
    extra_schema = filters.RelatedFilter(ServiceSchemaGenericFilterSet,
                                         queryset=ServiceSchema.objects.all())
    sender = RelatedFilter('api.v1.customer.filtersets.CustomerGenericFilterSet', 
                             queryset=Customer.objects.all())
    receiver = RelatedFilter('api.v1.customer.filtersets.CustomerGenericFilterSet', 
                             queryset=Customer.objects.all())
    class Meta:
        model = Protocol
        fields = {
            'id': ('exact', 'in',),
            'name': ('exact', 'icontains', 'contains', 'in',),
            'filename': ('exact', 'icontains', 'contains', 'in',),
            'src_format': ('exact', 'icontains', 'contains', 'in',),
            'dest_format': ('exact', 'icontains', 'contains', 'in',),
        }

class TemplateServiceFilterSet(filters.FilterSet):
    extra_schema = filters.RelatedFilter(ServiceSchemaGenericFilterSet,
                                         queryset=ServiceSchema.objects.all())

    class Meta:
        model = Template
        fields = {
            'id': ('exact', 'in',),
            'name': ('exact', 'icontains', 'contains', 'in',),
            'filename': ('exact', 'icontains', 'contains', 'in',),
        }
        