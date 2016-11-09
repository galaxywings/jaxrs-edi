from django.core.urlresolvers import resolve
from django.utils.deprecation import MiddlewareMixin

# plz refer to 
# http://stackoverflow.com/questions/14269719/django-rest-framework-v1-versioning#answer-21839842
# e.g.: 
#    curl -H "X-Version: v1" http://localhost:8090/api/auth/test/
# and I've decided to do this in the old way
class VersionSwitch(MiddlewareMixin):
    
    def process_request(self, request):
        r = resolve(request.path_info)
        version = request.META.get('HTTP_X_VERSION', False)
        if r.namespace.startswith('api:') and version:
            request.path = request.path.replace('/api', '/api/{}'.format(version))
            request.path_info = request.path_info.replace('/api', '/api/{}'.format(version))