from django.conf.urls import url, include
from rest_framework_bulk.routes import BulkRouter

from . import views

router = BulkRouter()
router.register('customers', views.CustomerViewSet)
router.register('processes', views.ProcessViewSet)
router.register('steps', views.StepViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),
]
