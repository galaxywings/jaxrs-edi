package org.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

public class Redis implements LoggerInterface{
	protected static final Logger logger = LoggerFactory.getLogger(Redis.class);
	private JedisPool jedisPool = null;
	
	@Override
	public Logger getLogger() {
		logger.debug("Redis.getLogger()::START");
		logger.debug("Redis.getLogger()::END");
		return logger;
	}
	
	public Redis(JedisPool jedisPool) throws Exception{
		getLogger().debug("Redis.(JedisPool jedisPool="+jedisPool+")::START");
		this.jedisPool = jedisPool;
		getLogger().debug("Redis.(JedisPool jedisPool="+jedisPool+")::END");
	}
	
	public byte[] get(String key) throws Exception{
		getLogger().debug("Redis.Get(String key="+"key"+")::START");
		byte[] result = null;
		try (Jedis jedis = jedisPool.getResource();) {
			byte[] keyAsBytes = SafeEncoder.encode(key);
			result = jedis.get(keyAsBytes);
		} 
		getLogger().debug("Redis.Get(String "+"key"+")::END = "+result);
		return result;
	}
	
	public String set(String key, String value, String encoding) throws Exception{
		getLogger().debug("Redis.set(String key="+key+", String value="+value+", String encoding="+encoding+")::START");
		String result = null;

		byte[] keyAsBytes = SafeEncoder.encode(key);
		byte[] valueAsBytes = RedisUtils.toBytes(value, encoding);
		result = this.set(keyAsBytes, valueAsBytes, -1);

		getLogger().debug("Redis.set(String key="+key+", String value="+value+", String encoding="+encoding+")::END = "+result);
		return result;
	}
	
	public String set(String key, byte[] valueAsBytes, String encoding) throws Exception{
		getLogger().debug("Redis.set(String key="+key+", byte[] valueAsBytes="+valueAsBytes+", String encoding"+encoding+")::START");
		String result = null;

		byte[] keyAsBytes = SafeEncoder.encode(key);
		result = this.set(keyAsBytes, valueAsBytes, -1);

		getLogger().debug("Redis.set(String key="+key+", byte[] valueAsBytes="+valueAsBytes+", String encoding"+encoding+")::END = "+result);
		return result;
	}

	public String set(String key, String value, int timeout, String encoding) throws Exception{
		getLogger().debug("Redis.set(String key="+key+", String value="+value+", int timeout="+timeout+", String encoding="+encoding+")::START");
		String result = null;

		byte[] keyAsBytes = SafeEncoder.encode(key);
		byte[] valueAsBytes = RedisUtils.toBytes(value, encoding);
		result = this.set(keyAsBytes, valueAsBytes, timeout);

		getLogger().debug("Redis.set(String key="+key+", String value="+value+", int timeout="+timeout+", String encoding="+encoding+")::END = "+result);
		return result;
	}
	
	public String set(String key, byte[] valueAsBytes, int timeout, String encoding) throws Exception{
		getLogger().debug("Redis.set(String key="+key+", byte[] valueAsBytes="+valueAsBytes+", int timeout="+timeout+", String encoding="+encoding+")::START");
		String result = null;

		byte[] keyAsBytes = SafeEncoder.encode(key);
		result = this.set(keyAsBytes, valueAsBytes, timeout);

		getLogger().debug("Redis.set(String key="+key+", byte[] valueAsBytes="+valueAsBytes+", int timeout="+timeout+", String encoding="+encoding+")::END = "+result);
		return result;
	}
	
	public String set(byte[] keyAsBytes, byte[] valueAsBytes, int timeout) throws Exception{
		getLogger().debug("Redis.set(byte[] keyAsBytes="+keyAsBytes+", byte[] valueAsBytes="+valueAsBytes+", int timeout="+timeout+")::START");
		String set_result = null;
		Long set_timeout = (long) -1;

		try (Jedis jedis = jedisPool.getResource();) {

			set_result = jedis.set(keyAsBytes, valueAsBytes);
			if(timeout > -1){
				set_timeout = jedis.expire(keyAsBytes, timeout);
			}

		} 

		String result = set_result + "," + set_timeout ;
		getLogger().debug("Redis.set(byte[] keyAsBytes="+keyAsBytes+", byte[] valueAsBytes="+valueAsBytes+", int timeout="+timeout+")::END = "+result);
		return result;
	}
	
	public Long expire(String key, int timeout) throws Exception{
		getLogger().debug("Redis.expire(String key="+key+", int timeout="+timeout+")::START");
		Long set_timeout = (long) 0;

		try (Jedis jedis = jedisPool.getResource();) {

			byte[] keyAsBytes = SafeEncoder.encode(key);

			set_timeout = jedis.expire(keyAsBytes, timeout);

		} 

		Long result = set_timeout;
		getLogger().debug("Redis.expire(String key="+key+", int timeout="+timeout+")::END = "+result);
		return result ;
	}

	public Long persist(String key) throws Exception{
		getLogger().debug("Redis.persist(String key="+key+")::START");
		Long set_timeout = (long) 0;

		try (Jedis jedis = jedisPool.getResource();) {

			byte[] keyAsBytes = SafeEncoder.encode(key);

			set_timeout = jedis.persist(keyAsBytes);

		} 

		Long result = set_timeout;
		getLogger().debug("Redis.persist(String key="+key+")::END = "+result);
		return result ;
	}
	
	public Boolean exists(String key) throws Exception{
		getLogger().debug("Redis.exists(String key="+key+")::START");
		Boolean result = false;


		try (Jedis jedis = jedisPool.getResource();) {

			byte[] keyAsBytes = SafeEncoder.encode(key);

			result = jedis.exists(keyAsBytes);

		} 

		getLogger().debug("Redis.exists(String key="+key+")::END = "+result);
		return result ;
	}
	
	public Long delete(String key) throws Exception{
		getLogger().debug("Redis.delete(String key="+key+")::START");
		Long result = (long) -1;

		try (Jedis jedis = jedisPool.getResource();) {

			byte[] keyAsBytes = SafeEncoder.encode(key);

			result = jedis.del(keyAsBytes);

		} 

		getLogger().debug("Redis.delete(String key="+key+")::END = "+result);
		return result ;
	}
	
	public Long ttl(String key) throws Exception{
		getLogger().debug("Redis.ttl(String key="+key+")::START");
		Long result = (long) -1;


		try (Jedis jedis = jedisPool.getResource();) {

			byte[] keyAsBytes = SafeEncoder.encode(key);

			result = jedis.ttl(keyAsBytes);

		} 

		getLogger().debug("Redis.ttl(String key="+key+")::END = "+result);
		return result ;
	}
	
	public String ping() throws Exception{
		getLogger().debug("Redis.ping()::START");
		String result = null;


		try (Jedis jedis = jedisPool.getResource();) {

			result = jedis.ping();

		} 

		getLogger().debug("Redis.ping()::END = "+result);
		return result ;
	}
	

}
