from common.storages import redis
from task.models import Process
import time
from django.dispatch import receiver
from django.db.models.signals import post_save, post_delete


# Where should this code live?
# Strictly speaking, signal handling and registration code can live anywhere you like, 
# although it’s recommended to avoid the application’s root module 
# and its models module to minimize side-effects of importing code.

# In practice, 
# signal handlers are usually defined in a signals submodule of the application they relate to. 
# Signal receivers are connected in the ready() method of your application configuration class. 
# If you’re using the receiver() decorator, simply import the signals submodule inside ready().

# refer to https://docs.djangoproject.com/en/dev/topics/signals/
@receiver(post_save, sender=Process)
def upon_process_saved(sender, instance, **kwargs):
	if(instance.active):	
		redis.zadd("mule:jobs", int(time.time()), instance.id)
		redis.hset("mule:frequency", instance.id, instance.interval)
	else:
		upon_process_deleted(sender, instance, **kwargs)

@receiver(post_delete, sender=Process)
def upon_process_deleted(sender, instance, **kwargs):
	redis.zrem("mule:jobs", instance.id)
	redis.hdel("mule:frequency", instance.id)