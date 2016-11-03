from django.conf.urls import url, include

import authx.api.v1.urls as authx_urls

urlpatterns = [
    url(r'^authx/', include(authx_urls, namespace='authx')), # just to align the naming pattern
    url(r'^auth/', include(authx_urls, namespace='auth')), # use this default
]