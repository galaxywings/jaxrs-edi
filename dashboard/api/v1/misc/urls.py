from django.conf.urls import url, include
from rest_framework_bulk.routes import BulkRouter


from .views import ContentTypeViewSet, DbFileViewSet, \
    ReadOnlyDbBasedFileViewSet


router = BulkRouter()
router.register('contenttypes', ContentTypeViewSet)
router.register('dbfiles', ReadOnlyDbBasedFileViewSet)

urlpatterns = [
    url(r'^dbfiles/file/$', 
        DbFileViewSet.as_view({'post': 'save_file'})
    ),
    url(r'^dbfiles/text/$', 
        DbFileViewSet.as_view({'post': 'save_text'})
    ),
    url(r'^dbfiles/download/(?P<filename>[\w.]{0,256})$', 
        DbFileViewSet.as_view({'get': 'download'}), 
        name='dbfile_dowload'
    ),
    url(r'^dbfiles/view/(?P<filename>[\w.]{0,256})$', 
        DbFileViewSet.as_view({'get': 'view_text'}),
        name='dbfile_view_text'
    ),
    url(r'^', include(router.urls)),
]
