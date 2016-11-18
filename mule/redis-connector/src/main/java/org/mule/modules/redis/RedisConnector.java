package org.mule.modules.redis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.mule.RequestContext;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;

import redis.clients.jedis.JedisPool;

import org.mule.modules.redis.config.ConnectorConfig;
import org.redis.LoggerInterface;
import org.redis.Redis;
import org.redis.RedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Connector(name = "redis", friendlyName = "Redis", description = "Mule Connector for Redis", schemaVersion = "1.0")
public class RedisConnector implements LoggerInterface {
	protected static final Logger logger = LoggerFactory.getLogger(RedisConnector.class);
	
	private JedisPool jedisPool;
	
	@Override
	public Logger getLogger() {
		logger.debug("Redis.getLogger()::START");
		logger.debug("Redis.getLogger()::END");
		return logger;
	}
	
	@Config
	ConnectorConfig config;

    @PostConstruct
    public void initializeJedis() throws Exception
    {
    	getLogger().debug("RedisConnector.initializeJedis()::START");
    	jedisPool = RedisFactory.CreateConnection(config.getHost(), config.getPort(), config.getTimeout(), config.getMaxTotal(), config.getMaxIdle());
    	getLogger().debug("RedisConnector.initializeJedis()::END");
    }
    
    @PreDestroy
    public void destroyJedis()
    {
    	getLogger().debug("RedisConnector.destroyJedis()::START");
        jedisPool.destroy();
    	getLogger().debug("RedisConnector.destroyJedis()::START");
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
    	getLogger().debug("RedisConnector.set(String key, String value, int timeout)::START");

    	Redis redis = new Redis(this.jedisPool);
    	
    	String encoding = RequestContext.getEvent().getEncoding();
    	
    	String result = null;
    	
    	if(timeout==-1){
    		result= redis.set(key, value, encoding);
    	} else {
    		result= redis.set(key, value, timeout, encoding);
    	}
    	getLogger().debug("RedisConnector.set(String key, String value)::END");
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
		getLogger().debug("RedisConnector.get(String key)::START");
    	Redis redis = new Redis(this.jedisPool);
		getLogger().debug("RedisConnector.get(String key)::END");
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
    	getLogger().debug("RedisConnector.expire(String key, int timeout)::START");

    	Redis redis = new Redis(this.jedisPool);
    	getLogger().debug("RedisConnector.expire(String key, int timeout)::END");
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
    	getLogger().debug("RedisConnector.persist(String key)::START");

    	Redis redis = new Redis(this.jedisPool);
    	getLogger().debug("RedisConnector.persist(String key)::END");
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
    	getLogger().debug("RedisConnector.exists(String key)::START");

    	Redis redis = new Redis(this.jedisPool);
    	getLogger().debug("RedisConnector.exists(String key)::END");
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
    	getLogger().debug("RedisConnector.delete(String key)::START");

    	Redis redis = new Redis(this.jedisPool);
    	getLogger().debug("RedisConnector.delete(String key)::END");
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
    	getLogger().debug("RedisConnector.getTtl(String key)::START");

    	Redis redis = new Redis(this.jedisPool);
    	getLogger().debug("RedisConnector.getTtl(String key)::END");
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
    	getLogger().debug("RedisConnector.ping()::START");

    	Redis redis = new Redis(this.jedisPool);
    	getLogger().debug("RedisConnector.ping()::END");
    	return redis.ping();
    }
    
    public ConnectorConfig getConfig() {
    	getLogger().debug("RedisConnector.getConfig()::START");
    	getLogger().debug("RedisConnector.getConfig()::END");
        return config;
    }

    public void setConfig(ConnectorConfig config) {
    	getLogger().debug("RedisConnector.setConfig(ConnectorConfig config)::START");
        this.config = config;
        getLogger().debug("RedisConnector.setConfig(ConnectorConfig config)::END");
    }

}