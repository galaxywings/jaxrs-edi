import collections

from django.contrib.contenttypes.models import ContentType
from django.db import models
from django.utils.translation import ugettext_lazy as _
from jsonfield.fields import JSONField, JSONCharField
import reversion

@reversion.register()
class ServiceSchema(models.Model):
    code = models.CharField(_('code'), max_length=128, help_text=_('Code for communication between different systems'))
    name = models.CharField(_('name'), max_length=128, help_text=_('Display name'))
    extra_schema = JSONField(load_kwargs={'object_pairs_hook': collections.OrderedDict},
                             blank=True,
                            help_text=_('JSON schema for detailing service params'))
    content_type = models.ForeignKey(ContentType, on_delete=models.CASCADE)
    
    class Meta:
        unique_together = (('code',), )
    
    def __str__(self):
        return self.code

class AbstractBaseService(models.Model):
    name = models.CharField(_('name'), max_length=128, help_text=_('Display name'))
    extra_schema = models.ForeignKey(ServiceSchema, on_delete=models.CASCADE)
    extra_params = JSONCharField(load_kwargs={'object_pairs_hook': collections.OrderedDict},
                            blank=True,
                            max_length=4*1024,
                            help_text=_('JSON object for storing service params value'))
        
    class Meta:
        unique_together = (('name',), )
        abstract = True
    
@reversion.register()
class GenericService(AbstractBaseService):
    pass
        
@reversion.register()
class Ftp(AbstractBaseService):
    username = models.CharField(_('username'), max_length=32)
    password = models.CharField(_('password'), max_length=128)
    host = models.CharField(_('host'), max_length=32)
    port = models.PositiveIntegerField(_('port'))
    path = models.CharField(_('file'), max_length=255)   