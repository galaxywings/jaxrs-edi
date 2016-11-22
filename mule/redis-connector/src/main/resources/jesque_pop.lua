local queueKey = KEYS[1]
local freqKey = KEYS[2]
local now = ARGV[1]
local payloads = redis.call('ZRANGEBYSCORE', queueKey, '-inf', now)
for k,payload in pairs(payloads) do
	local frequency = redis.call('HGET', freqKey, payload)
	if frequency then
		redis.call('ZINCRBY', queueKey, frequency, payload)
	else
		redis.call('ZREM', queueKey, payload)
	end
end
return payloads
