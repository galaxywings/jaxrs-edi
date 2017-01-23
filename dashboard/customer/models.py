from django.db import models
from django.utils.translation import ugettext_lazy as _
import reversion

@reversion.register()
class Customer(models.Model):
    code = models.CharField(_('code'), max_length=128, help_text=_('Code for communication between different systems'))
    name = models.CharField(_('name'), max_length=128, help_text=_('Display name'))
    active = models.BooleanField(_('active'), default=False)
    
    class Meta:
        unique_together = (('code',), )
    
    def __str__(self):
        return self.code