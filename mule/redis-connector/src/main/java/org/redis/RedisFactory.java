package org.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisFactory {
	protected static final Logger logger = LoggerFactory.getLogger(RedisFactory.class);
	private static JedisPool Pool = null;
	private static String mHost = null;
	private static int mMaxTotal = 8;
	private static int mMaxIdle = 8;
	private static int mPort = 6379;
	private static int mTimeout = 2;
	
	public static void destroy() throws Exception{
		logger.debug("RedisFactory.destroy()::START");
		Pool.destroy();
		Pool = null;
		logger.debug("RedisFactory.destroy()::END");
	}
	
	public static JedisPool CreateConnection(String host, int port, int timeout, int maxTotal, int maxIdle) throws Exception{
		logger.debug("RedisFactory.CreateConnection(String host="+host+", int port="+port+", int timeout="+timeout+", int maxTotal="+maxTotal+", int maxIdle="+maxIdle+")::START");
		
		if(Pool == null || (RedisFactory.mHost!=host || RedisFactory.mPort!=port || RedisFactory.mTimeout!=timeout || RedisFactory.mMaxTotal!=maxTotal || RedisFactory.mMaxIdle!=maxIdle))
		{
			logger.debug("RedisFactory.CreateConnection(String host="+host+", int port="+port+", int timeout="+timeout+", int maxTotal="+maxTotal+", int maxIdle="+maxIdle+"):: New Instance Intialised");
			RedisFactory.mHost=host;
			RedisFactory.mPort=port;
			RedisFactory.mTimeout=timeout;
			RedisFactory.mMaxTotal = maxTotal;
			RedisFactory.mMaxIdle = maxIdle;
			
			logger.debug("RedisFactory.mHost="+RedisFactory.mHost);
			logger.debug("RedisFactory.mPort="+RedisFactory.mPort);
			logger.debug("RedisFactory.mTimeout="+RedisFactory.mTimeout);
			logger.debug("RedisFactory.mMaxTotal="+RedisFactory.mMaxTotal);
			logger.debug("RedisFactory.mMaxIdle="+RedisFactory.mMaxIdle);
			
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(maxTotal); // maximum active connections
			poolConfig.setMaxIdle(maxIdle);  // maximum idle connections
			poolConfig.setTestOnReturn(true);
			poolConfig.setTestOnBorrow(true);
			poolConfig.setTestWhileIdle(true);
			
			Pool = new JedisPool(poolConfig, RedisFactory.mHost, RedisFactory.mPort, RedisFactory.mTimeout);
			logger.debug("RedisFactory.CreateConnection(String host="+host+", int port="+port+", int timeout="+timeout+", int maxTotal="+maxTotal+", int maxIdle="+maxIdle+"):: New Instance Intialised="+Pool);
		} else {
			logger.debug("RedisFactory.CreateConnection(String host="+host+", int port="+port+", int timeout="+timeout+", int maxTotal="+maxTotal+", int maxIdle="+maxIdle+"):: Existing="+Pool);
			logger.debug("Pool.getNumActive()="+Pool.getNumActive());
			logger.debug("Pool.getNumIdle()="+Pool.getNumIdle());
			logger.debug("Pool.getNumWaiters()="+Pool.getNumWaiters());
		}
		
		logger.debug("RedisFactory.CreateConnection(String host="+host+", int port="+port+", int timeout="+timeout+", int maxTotal="+maxTotal+", int maxIdle="+maxIdle+")::END");
		return Pool;
	}
	
	public static JedisPool CreateConnection() throws Exception{
		logger.debug("RedisFactory.CreateConnection()::START");
		RedisFactory.CreateConnection(RedisFactory.mHost, RedisFactory.mPort, RedisFactory.mTimeout, RedisFactory.mMaxTotal, RedisFactory.mMaxIdle);
		logger.debug("RedisFactory.CreateConnection()::END");
		return Pool;
	}
	
	public static void ReleaseConnection(Jedis jedis){
		logger.debug("RedisFactory.ReleaseConnection()::START");
		RedisFactory.Pool.returnResource(jedis);
		logger.debug("RedisFactory.ReleaseConnection()::END");
	}
	
	public static void ReleaseBrokenConnection(Jedis jedis){
		logger.debug("RedisFactory.ReleaseConnection()::START");
		RedisFactory.Pool.returnBrokenResource(jedis);
		logger.debug("RedisFactory.ReleaseConnection()::END");
	}
	

	public String getHost() {
		logger.debug("RedisFactory.getHost()::START");
		logger.debug("RedisFactory.getHost()::END");
		return RedisFactory.mHost;
	}

	public void setHost(String host) {
		logger.debug("RedisFactory.setHost(String host="+host+")::START");
		RedisFactory.mHost = host;
		logger.debug("RedisFactory.setHost(String host="+host+")::END");
	}
	
	public int getMaxTotal() {
		logger.debug("RedisFactory.getMaxTotal()::START");
		logger.debug("RedisFactory.getMaxTotal()::END");
		return RedisFactory.mMaxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		logger.debug("RedisFactory.setMaxTotal(int maxTotal="+maxTotal+")::START");
		RedisFactory.mMaxTotal = maxTotal;
		logger.debug("RedisFactory.setMaxTotal(int maxTotal="+maxTotal+")::END");
	}
	
	public int getMaxIdle() {
		logger.debug("RedisFactory.getMaxIdle()::START");
		logger.debug("RedisFactory.getMaxIdle()::END");
		return RedisFactory.mMaxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		logger.debug("RedisFactory.setMaxIdle(int maxIdle="+maxIdle+")::START");
		RedisFactory.mMaxIdle = maxIdle;
		logger.debug("RedisFactory.setMaxIdle(int maxIdle="+maxIdle+")::END");
	}
	
	public int getPort() {
		logger.debug("RedisFactory.getPort()::START");
		logger.debug("RedisFactory.getPort()::END");
		return RedisFactory.mPort;
	}

	public void setPort(int port) {
		logger.debug("RedisFactory.setPort(int port="+port+")::START");
		RedisFactory.mPort = port;
		logger.debug("RedisFactory.setPort(int port="+port+")::END");
	}
	
	public int getTimeout() {
		logger.debug("RedisFactory.getTimeout()::START");
		logger.debug("RedisFactory.getTimeout()::END");
		return RedisFactory.mTimeout;
	}

	public void setTimeout(int timeout) {
		logger.debug("RedisFactory.setTimeout(int timeout="+timeout+")::START");
		RedisFactory.mTimeout = timeout;
		logger.debug("RedisFactory.setTimeout(int timeout="+timeout+")::END");
	}

}
