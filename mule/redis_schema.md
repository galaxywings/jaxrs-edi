# Redis Scheduler Schema

mule:jobs   zset

    redis-cli ZADD mule:jobs "$(date +%s)" job1
    redis-cli ZADD mule:jobs "$(date +%s)" job2
    redis-cli ZRANGEBYSCORE dashboard:jobs -inf "$(date +%s)"


mule:frequency   hash

    redis-cli hset mule:frequency job1 30
    redis-cli hset mule:frequency job2 60

mule:job:12345:seq -> list

mule:job:12345   -> hash

    seq
    job1
    job2
    job3