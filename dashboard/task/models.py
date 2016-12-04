
import collections
from django.db import models
from django.utils.translation import ugettext_lazy as _
from jsonfield import JSONField, JSONCharField

class Process(models.Model):
    customer = models.ForeignKey('customer.Customer')
    name = models.CharField(_('name'), max_length=64, help_text=_('Display name'))
    active = models.BooleanField(_('active'), default=False)
    services = models.ManyToManyField('task.Service', through='task.Step')
    
    class Meta:
        unique_together = (('customer', 'name',), )
    
    def __str__(self):
        return 'customer_id: {}, name: {}'.format(self.customer_id, self.name)

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
    process = models.ForeignKey('task.Process')
    service = models.ForeignKey('task.Service')
    seq = models.SmallIntegerField(_('seq'),help_text=_('Step seq for a detail process'))
    params_value = JSONCharField(load_kwargs={'object_pairs_hook': collections.OrderedDict},
                            max_length=4*1024,
                            help_text=_('JSON object for storing service params value'))
    
    class Meta:
        unique_together = (('process','service', 'seq'), )
    
    def __str__(self):
        return 'process_id: {}, service_id: {}, seq: {}'.format(self.process_id, self.service_id, self.seq)