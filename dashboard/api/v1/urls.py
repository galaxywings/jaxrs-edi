from django.conf.urls import url, include
from .auth import urls as auth_urls
from .customer import urls as customer_urls
from .task import urls as task_urls
from .service import urls as service_urls
from .misc import urls as misc_urls

urlpatterns = [
    url(r'^auth/', include(auth_urls, namespace='auth')),
    url(r'^customer/', include(customer_urls, namespace='customer')),
    url(r'^misc/', include(misc_urls, namespace='misc')),
    url(r'^task/', include(task_urls, namespace='task')),
    url(r'^service/', include(service_urls, namespace='service')),
]
