
from django.contrib.contenttypes.models import ContentType
from memoize import memoize

from task.models import Step


@memoize()
def get_contenttype_object_service_dict():
    '''
        prepare a dict of <content_type_id, <object_id, service_obj_with_schema_loaded>>, 
    '''
    step_ct_ids = Step.objects.values_list('content_type_id', flat=True).distinct()
    step_ct_qs = ContentType.objects.filter(id__in=step_ct_ids)
    result = {}
    for ct in step_ct_qs:
        ServiceModel = ct.model_class()
        # all ServiceModel would have a foreign reference to extra_schema
        service_qs = ServiceModel.objects.all().select_related('extra_schema')
        result[ct.id] = dict( 
            (service.id, service) for service in service_qs
        )
    return result