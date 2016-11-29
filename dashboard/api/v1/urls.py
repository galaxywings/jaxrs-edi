from django.conf.urls import url, include
from .auth import urls as auth_urls
from .customer import urls as customer_urls

urlpatterns = [
    url(r'^auth/', include(auth_urls, namespace='auth')),
    url(r'^customer/', include(customer_urls, namespace='customer')),
]
