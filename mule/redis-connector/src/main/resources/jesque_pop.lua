local queueKey = KEYS[1]
local freqKey = KEYS[2]
local now = ARGV[1]
local allowMiss = ARGV[2]
local payloads = redis.call('ZRANGEBYSCORE', queueKey, '-inf', now)
for k,payload in pairs(payloads) do
	local frequency = redis.call('HGET', freqKey, payload)
	if frequency then

		if (allowMiss == "true") then
			redis.call('ZREM', queueKey, payload)
			redis.call('ZADD', queueKey, now + frequency, payload)
		else
			redis.call('ZINCRBY', queueKey, frequency, payload)
		end
	else
		redis.call('ZREM', queueKey, payload)
	end
end
return payloads