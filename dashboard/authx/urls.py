from django.conf.urls import url, include
from rest_framework import routers

from .views import index

router = routers.DefaultRouter()
# router.register('users', views.UserRetrieveUpdateViewSet)

urlpatterns = [
    url(r'^$', index),
]
