

from django.core.files.base import ContentFile
from django.core.files.storage import Storage
from django.urls import reverse
from django.utils.deconstruct import deconstructible

from misc.models import DbBasedFile


@deconstructible
class DbStorage(Storage):
    '''
        Class DatabaseStorage provides storing files in the database.
        Inspiring from `django-storages` DatabaseStorage
    '''

    def _open(self, name, mode='rb'):
        '''
            Open a file from database.
        '''
        if self.exists(name):
            mem_file_content = DbBasedFile.objects.filter(filename=name).values_list('content', flat=True)[0]
        else:
            mem_file_content = b''
        result = ContentFile(mem_file_content, name=name)
        return result

    def _save(self, name, content):
        """Save 'content' as file named 'name'.

        @note '\' in path will be converted to '/'.
        """

        name = name.replace('\\', '/')
        blob = content.read()
        size = content.size
#         DbBasedFile.objects.update_or_create(filename=name, content=blob, size=size
#                                     , defaults={'content': blob, 'size': size, })
#         DbBasedFile.objects.create(filename=name, content=blob, size=size)
        DbBasedFile(filename=name, content=blob, size=size).save()
        return name

    def exists(self, name):
        return DbBasedFile.objects.filter(filename=name).exists()

    def delete(self, name):
        return DbBasedFile.objects.filter(filename=name).delete()

    def url(self, name):
        result = reverse('api:misc:dbfiles', kwargs={'filename': name})
        return result

    def size(self, name):
        if not self.exists(name):
            return 0
        return DbBasedFile.objects.filter(filename=name).values_list('size', flat=True)[0]


dbfile_storage = DbStorage()