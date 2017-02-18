
from django.contrib.contenttypes.fields import GenericForeignKey
from django.contrib.contenttypes.models import ContentType
from django.db import models
from django.utils.translation import ugettext_lazy as _
import reversion

@reversion.register()
class Process(models.Model):
    name = models.CharField(_('name'), max_length=64, help_text=_('Display name'))
    interval = models.PositiveIntegerField(_('interval'), default=300)
    active = models.BooleanField(_('active'), default=False)

    class Meta:
        unique_together = (('name',), )
    
    def __str__(self):
        return 'Process name: {}'.format(self.name)

class Step(models.Model):
    process = models.ForeignKey('task.Process', on_delete=models.CASCADE)
    seq = models.SmallIntegerField(_('seq'),help_text=_('Step seq for a detail process'))
    
    # below is copied from 
    # https://docs.djangoproject.com/en/1.10/ref/contrib/contenttypes/#generic-relations
    content_type = models.ForeignKey(ContentType, on_delete=models.CASCADE)
    object_id = models.PositiveIntegerField()
    content_object = GenericForeignKey('content_type', 'object_id')
    
    class Meta:
        unique_together = (('process', 'seq'), )
    
    def __str__(self):
        return 'process_id: {}, seq: {}'.format(self.process_id, self.seq)
    