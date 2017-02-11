from django.contrib.contenttypes.models import ContentType
from django.urls.base import reverse
from rest_framework.fields import SerializerMethodField
from rest_framework_bulk.serializers import BulkSerializerMixin, \
    BulkListSerializer

from common.serializers import DynamicFieldsModelSerializer
from misc.models import DbBasedFile
from misc.storages import dbfile_storage


class ContentTypeSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    class Meta:
        model = ContentType
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    
class DbBasedFileSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    content = SerializerMethodField()
    download_url = SerializerMethodField()
    view_url = SerializerMethodField()

    class Meta:
        model = DbBasedFile
        exclude = ('content', )
    
    def get_download_url(self, obj):
        return reverse('api:v1:misc:dbfile_dowload', 
                        kwargs={'filename': obj.filename})
    
    def get_view_url(self, obj):
        return reverse('api:v1:misc:dbfile_view_text', 
                        kwargs={'filename': obj.filename})
    
    def get_content(self, obj):
        file_obj = dbfile_storage.open(obj.filename)
        return file_obj.read()