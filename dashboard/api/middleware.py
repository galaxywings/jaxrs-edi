from django.core.urlresolvers import resolve
from django.core.urlresolvers import reverse
from django.utils.deprecation import MiddlewareMixin

# plz refer to 
# http://stackoverflow.com/questions/14269719/django-rest-framework-v1-versioning#answer-21839842
# e.g.: 
#    curl -H "X-Version: v1" http://your.domain:8000/v1/myaccount/
# and I've decided to do this in the old way
class VersionSwitch(MiddlewareMixin):
    
    def process_request(self, request):
        r = resolve(request.path_info)
        version = request.META.get('HTTP_X_VERSION', False)
        if r.namespace.startswith('v1:') and version:
            old_version = r.namespace.split(':')[-1]
            request.path_info = reverse(
                                    '{}:{}'.format(
                                        r.namespace.replace(old_version, version), 
                                        r.url_name
                                    ), 
                                    args=r.args, 
                                    kwargs=r.kwargs
                                )