from django.conf.urls import url, include
from rest_framework_bulk.routes import BulkRouter

from . import views

router = BulkRouter()
router.register('services', views.ServiceViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),
]
