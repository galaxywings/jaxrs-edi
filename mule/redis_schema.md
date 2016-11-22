# Redis Scheduler Schema

dashboard:jobs

    redis-cli ZADD dashboard:jobs "$(date +%s)" job1
    redis-cli ZADD dashboard:jobs "$(date +%s)" job2
    ZRANGEBYSCORE dashboard:jobs -inf "$(date +%s)"


dashboard:jobs:frequency

    hset dashboard:frequency job1 30
    hset dashboard:frequency job2 60