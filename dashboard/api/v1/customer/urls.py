from django.conf.urls import url, include
from rest_framework_bulk.routes import BulkRouter

from . import views

router = BulkRouter()
router.register('customers', views.CustomerViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),
]
