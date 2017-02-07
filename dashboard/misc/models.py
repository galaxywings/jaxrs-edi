
from django.db import models
from django.utils.translation import ugettext_lazy as _

from common.fields import XBinaryField


class DbBasedFile(models.Model):
    filename = models.CharField(_('filename'), max_length=128)
    content = XBinaryField(_('content'))
    size = models.PositiveIntegerField(_('size'))

    class Meta:
        unique_together = (('filename', ), )
    
    def __str__(self):
        return '{}, filename: {}'.format(self.__class__.__name__, self.filename)