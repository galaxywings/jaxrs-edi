
import rest_framework_filters as filters
from django.contrib.contenttypes.models import ContentType

class ContentTypeFilterSet(filters.FilterSet):
    class Meta:
        model = ContentType
        fields = {
            'app_label': ('exact', 'in',),
            'model': ('exact', 'in', ),
        }