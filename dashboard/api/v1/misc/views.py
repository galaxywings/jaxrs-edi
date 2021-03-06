
from urllib import parse

from django.contrib.contenttypes.models import ContentType
from django.core.files.base import ContentFile
from django.urls.base import reverse_lazy
from django.utils.encoding import force_bytes
from rest_framework import status
from rest_framework.decorators import list_route
from rest_framework.exceptions import ValidationError
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.viewsets import ReadOnlyModelViewSet, ViewSet
from rest_framework_bulk.generics import BulkModelViewSet

from api.v1.misc.filtersets import ContentTypeFilterSet
from api.v1.misc.filtersets import DbBasedFileGenericFilterSet
from api.v1.misc.serializers import ContentTypeSerializer, DbBasedFileSerializer
from misc.models import DbBasedFile
from misc.storages import dbfile_storage


class ContentTypeViewSet(BulkModelViewSet):
    queryset = ContentType.objects.all()
    serializer_class = ContentTypeSerializer
    filter_class = ContentTypeFilterSet
    search_fields = ('app_label', 'model')
    #permission_classes = (IsAuthenticated, )

class DbFileViewSet(ViewSet):
    @staticmethod
    def get_available_filename(filename, overwrite):
        if overwrite:
            result = filename
        else:
            result = dbfile_storage.get_available_name(filename, 
                                                         max_length=128)
        return result
    
    def save_file(self, request):
        file = request.data.get('file', None)
        if file is None:
            raise ValidationError('`file` is required')
        overwrite = request.data.get('overwrite', False)
        a_filename = self.get_available_filename(file.name, overwrite)
        filename = dbfile_storage.save(a_filename, file)
        result = {
            'filename': filename, 
            'download_url': reverse_lazy('api:v1:misc:dbfile_dowload', 
                                         kwargs={'filename': filename}),
        }
        return Response(result, status=status.HTTP_201_CREATED)
    
    def save_text(self, request):
        filename = request.data.get('filename', None)
        txt_content = request.data.get('content', None)
        if filename is None:
            raise ValidationError('`filename` is required')
        if txt_content is None:
            raise ValidationError('`content` is required')
        
        # tested we need to unquote it to make json call, form-data or x-www-form-urlencode to work
        txt_content = parse.unquote(txt_content)
        overwrite = request.data.get('overwrite', False)
        a_filename = self.get_available_filename(filename, overwrite)
        # force_bytes in order to make it compatible with those via posting file
        # always saving bytes
        file = ContentFile(force_bytes(txt_content), name=a_filename)
        filename = dbfile_storage.save(file.name, file)
        result = {
            'filename': filename, 
            'download_url': reverse_lazy('api:v1:misc:dbfile_dowload', 
                                         kwargs={'filename': filename}),
            'view_url': reverse_lazy('api:v1:misc:dbfile_view_text', 
                                     kwargs={'filename': filename}),
        }
        return Response(result, status=status.HTTP_201_CREATED)
    
    def download(self, request, filename):
        file_obj = dbfile_storage.open(filename)
        headers = {
            'Content-Disposition': 'attachment; filename="%s"' % filename, 
        }
        return Response(file_obj, content_type='application/octet-stream', headers=headers)
    
    def view_text(self, request, filename):
        file_obj = dbfile_storage.open(filename)
        
        content = file_obj.read()
        return Response(content)

class ReadOnlyDbBasedFileViewSet(ReadOnlyModelViewSet):
    queryset = DbBasedFile.objects.all()
    serializer_class = DbBasedFileSerializer
    filter_class = DbBasedFileGenericFilterSet
    search_fields = ('filename', )
    
    
