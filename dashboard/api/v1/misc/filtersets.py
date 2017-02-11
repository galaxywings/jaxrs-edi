
from django.contrib.contenttypes.models import ContentType

from misc.models import DbBasedFile
import rest_framework_filters as filters


class ContentTypeFilterSet(filters.FilterSet):
    class Meta:
        model = ContentType
        fields = {
            'app_label': ('exact', 'in',),
            'model': ('exact', 'in', ),
        }
        
class DbBasedFileGenericFilterSet(filters.FilterSet):
    class Meta:
        model = DbBasedFile
        fields = {
            'id': ('exact', 'in',),
            'filename': ('exact', 'in', 'contains', ),
            'size': ('exact', 'gt', 'lt', 'in', ),
        }