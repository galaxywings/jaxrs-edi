from django.conf.urls import url, include
from rest_framework_bulk.routes import BulkRouter

from . import views

router = BulkRouter()
router.register('schemas', views.ServiceSchemaViewSet)
router.register('generics', views.GenericServiceViewSet)
router.register('ftps', views.FtpServiceViewSet)
router.register('protocols', views.ProtocolServiceViewSet)
router.register('templates', views.TemplateServiceViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),
]
