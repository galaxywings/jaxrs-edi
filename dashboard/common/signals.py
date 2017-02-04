from common.redis import r
from task.models import Process
import time
from django.dispatch import receiver
from django.db.models.signals import post_save, post_delete

@receiver(post_save, sender=Process)
def uponProcessSaved(sender, instance, **kwargs):
	if(instance.active):	
		r.zadd("mule:jobs", int(time.time()), instance.id)
		r.hset("mule:frequency", instance.id, instance.interval)
	else:
		r.zrem("mule:jobs", instance.id)
		r.hdel("mule:frequency", instance.id)

@receiver(post_delete, sender=Process)
def uponProcessDeleted(sender, instance, **kwargs):
	r.zrem("mule:jobs", instance.id)
	r.hdel("mule:frequency", instance.id)