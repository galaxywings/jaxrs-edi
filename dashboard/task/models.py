
import collections

from django.contrib.contenttypes.fields import GenericForeignKey
from django.contrib.contenttypes.models import ContentType
from django.db import models
from django.utils.translation import ugettext_lazy as _
from jsonfield.fields import JSONField, JSONCharField

class Process(models.Model):
    customer = models.ForeignKey('customer.Customer')
    name = models.CharField(_('name'), max_length=64, help_text=_('Display name'))
    interval = models.PositiveIntegerField(_('interval'), default=300)
    active = models.BooleanField(_('active'), default=False)
    # TODO to be deprecated...
    services = models.ManyToManyField('task.Service', through='task.Step')
    class Meta:
        unique_together = (('customer', 'name',), )
    
    def __str__(self):
        return 'customer_id: {}, name: {}'.format(self.customer_id, self.name)

# TODO will be deprecated
class Service(models.Model):
    code = models.CharField(_('code'), max_length=128, help_text=_('Code for communication between different systems'))
    name = models.CharField(_('name'), max_length=128, help_text=_('Display name'))
    params_schema = JSONField(load_kwargs={'object_pairs_hook': collections.OrderedDict},
                            help_text=_('JSON schema for detailing service params'))
    
    class Meta:
        unique_together = (('code',), )
    
    def __str__(self):
        return self.code

class Step(models.Model):
    process = models.ForeignKey('task.Process', on_delete=models.CASCADE)
    seq = models.SmallIntegerField(_('seq'),help_text=_('Step seq for a detail process'))
    
    # below is copied from 
    # https://docs.djangoproject.com/en/1.10/ref/contrib/contenttypes/#generic-relations
    content_type = models.ForeignKey(ContentType, on_delete=models.CASCADE)
    object_id = models.PositiveIntegerField()
    content_object = GenericForeignKey('content_type', 'object_id')
    
    # TODO to be deprecated...
    service = models.ForeignKey('task.Service', on_delete=models.CASCADE)
    params_value = JSONCharField(load_kwargs={'object_pairs_hook': collections.OrderedDict},
                            max_length=4*1024,
                            help_text=_('JSON object for storing service params value'))
    
    class Meta:
        unique_together = (('process', 'seq'), )
    
    def __str__(self):
        return 'process_id: {}, seq: {}'.format(self.process_id, self.seq)
    