package org.mule.modules.redis;

import com.google.common.collect.ImmutableList;
import org.mule.RequestContext;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.redis.config.ConnectorConfig;
import org.redis.Redis;
import org.redis.RedisFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Set;

@Connector(name = "redis", friendlyName = "Redis", description = "Mule Connector for Redis", schemaVersion = "1.0")
public class RedisConnector {
	private JedisPool jedisPool;

	String luaScript = "local queueKey = KEYS[1]\n" +
			"local freqKey = KEYS[2]\n" +
			"local now = ARGV[1]\n" +
			"local payloads = redis.call('ZRANGEBYSCORE', queueKey, '-inf', now)\n" +
			"for k,payload in pairs(payloads) do\n" +
			"\tlocal frequency = redis.call('HGET', freqKey, payload)\n" +
			"\tif frequency then\n" +
			"\t\tredis.call('ZINCRBY', queueKey, frequency, payload)\n" +
			"\telse\n" +
			"\t\tredis.call('ZREM', queueKey, payload)\n" +
			"\tend\n" +
			"end\n" +
			"return payloads\n";

	@Config
	ConnectorConfig config;

	@PostConstruct
	public void initializeJedis() throws Exception
	{
		jedisPool = RedisFactory.CreateConnection(config.getHost(), config.getPort(), config.getTimeout(), config.getMaxTotal(), config.getMaxIdle());
	}

	@PreDestroy
	public void destroyJedis()
	{
		jedisPool.destroy();
	}

	/**
	 * Set the string value as value of the key.
	 *
	 * {@sample.xml ../../../doc/redis-connector.xml.sample set}
	 *
	 * @param key specified key
	 * @param key value value to be stored
	 * @return Status code reply
	 */
	@Processor(name = "set")
	public String set(@Default("Hello") String key, @Default("World") String value, @Default("-1") int timeout) throws Exception {

		Redis redis = new Redis(this.jedisPool);

		String encoding = RequestContext.getEvent().getEncoding();

		String result = null;

		if(timeout==-1){
			result= redis.set(key, value, encoding);
		} else {
			result= redis.set(key, value, timeout, encoding);
		}
		return result;
	}

	/**
	 * Get the value of the specified key.
	 *
	 * {@sample.xml ../../../doc/redis-connector.xml.sample set}
	 *
	 * @param key
	 *            specified key
	 * @return value of the specified key
	 * @throws Exception
	 */
	@Processor(name = "get")
	public byte[] get(String key) throws Exception {
		Redis redis = new Redis(this.jedisPool);
		return redis.get(key);
	}

	/**
	 * Set a specified cache timeout for a key.
	 *
	 * {@sample.xml ../../../doc/redis-connector.xml.sample set}
	 *
	 * @param key specified key
	 * @param timeout timeout for the key/value to expire in seconds
	 * @return value of the specified key
	 * @throws Exception
	 */
	@Processor(name = "expire")
	public Long expire(String key, int timeout) throws Exception {
		Redis redis = new Redis(this.jedisPool);
		return redis.expire(key, timeout);
	}

	/**
	 * Persist select key/value (remove cache timeout).
	 *
	 * {@sample.xml ../../../doc/redis-connector.xml.sample set}
	 *
	 * @param key specified key
	 * @return Status code reply
	 * @throws Exception
	 */
	@Processor(name = "persist")
	public Long persist(String key) throws Exception {
		Redis redis = new Redis(this.jedisPool);
		return redis.persist(key);
	}

	/**
	 * Get the value of the specified key.
	 *
	 * {@sample.xml ../../../doc/redis-connector.xml.sample set}
	 *
	 * @param key specified key
	 * @return Boolean true/false depending if the key exists
	 * @throws Exception
	 */
	@Processor(name = "exists")
	public Boolean exists(String key) throws Exception {
		Redis redis = new Redis(this.jedisPool);
		return redis.exists(key);
	}

	/**
	 * Delete key/value of the specified key.
	 *
	 * {@sample.xml ../../../doc/redis-connector.xml.sample set}
	 *
	 * @param key specified key
	 * @return Status code reply
	 * @throws Exception
	 */
	@Processor(name = "delete")
	public Long delete(String key) throws Exception {
		Redis redis = new Redis(this.jedisPool);
		return redis.delete(key);
	}

	/**
	 * Retrieve the TTL for a key/value of the specified key.
	 *
	 * {@sample.xml ../../../doc/redis-connector.xml.sample set}
	 *
	 * @param key specified key
	 * @return Time to live in seconds
	 * @throws Exception
	 */
	@Processor(name = "getTtl")
	public Long getTtl(String key) throws Exception {
		Redis redis = new Redis(this.jedisPool);
		return redis.ttl(key);
	}

	/**
	 * Return 'Pong' if connectivity has been established to the Redis instance.
	 *
	 * {@sample.xml ../../../doc/redis-connector.xml.sample set}
	 *
	 * @return String 'Pong' if connectivity has been established
	 * @throws Exception
	 */
	@Processor(name = "ping")
	public String ping() throws Exception {
		Redis redis = new Redis(this.jedisPool);
		return redis.ping();
	}

	@Processor(name = "zrangeByScore")
	public Set<String> zrangeByScore(String key, @Optional Double min, @Optional Double max) throws Exception{
		Redis redis = new Redis(this.jedisPool);
		return redis.zrangeByScore(key, java.util.Optional.ofNullable(min).orElse(Double.MIN_VALUE),
				java.util.Optional.ofNullable(max).orElse(Double.MAX_VALUE));
	}

	@Processor(name = "getScheduleJobs")
	public List<String> getScheduleJobs(String queueKey, String frequencyKey) {
		List<String> results;
		try (Jedis jedis = jedisPool.getResource()) {
			results = (List<String>) jedis.eval(luaScript, ImmutableList.of(queueKey, frequencyKey),
					ImmutableList.of(String.valueOf(System.currentTimeMillis() / 1000L)));
		}

		return results;
	}

	public ConnectorConfig getConfig() {
		return config;
	}

	public void setConfig(ConnectorConfig config) {
		this.config = config;
	}

}