"""dashboard URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf import settings
from django.conf.urls import url, include

from .views import index

urlpatterns = [
    url(r'^$', index),
    url(r'^api/', include('api.urls', namespace='api')),
]

if settings.DEBUG:
    from django.views.static import serve as static_serve
    from django.contrib.staticfiles.urls import staticfiles_urlpatterns
    urlpatterns = [
                      url(r'^media/(?P<path>.*)$', static_serve,
                          {'document_root': settings.MEDIA_ROOT, 'show_indexes': True}),
                  ] + staticfiles_urlpatterns() + urlpatterns
