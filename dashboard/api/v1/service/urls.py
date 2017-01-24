from django.conf.urls import url, include
from rest_framework_bulk.routes import BulkRouter

from . import views

router = BulkRouter()
router.register('schemas', views.ServiceSchemaViewSet)
router.register('generics', views.GenericServiceViewSet)
router.register('ftps', views.FtpServiceViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),
]
