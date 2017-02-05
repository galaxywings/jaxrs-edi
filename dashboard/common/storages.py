
from django.conf import settings

import redis as redis_module

redis = redis_module.StrictRedis(
            host=settings.REDIS_HOST, 
            port=settings.REDIS_PORT,
            password=settings.REDIS_PASSWORD,
            db=settings.REDIS_MULE_DB
        )