package org.redis.test;

//import org.testng.Assert;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//import redis.embedded.RedisServer;


public class TestRedis extends TestBase {
/*
	private JedisPool jedisPool = null;
	private Redis redisClient = null;
	private RedisServer redisServer = null;

	/*
	SETUP
	 */
/*
	//@BeforeTest
	public void beforeTest() {
		
		
		logger.debug("TestRedis.beforeTest()::START");
		String host =  getProp("redis.host");
		int port = getPropInt("redis.port");
		int timeout = getPropInt("redis.timeout");
		int maxTotal = getPropInt("redis.maxTotal");
		int maxIdle = getPropInt("redis.maxIdle");
		
		try{
			if(redisServer==null){
				redisServer = new RedisServer(port);
				redisServer.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		ConnectorConfig config = new ConnectorConfig();
		config.setHost(host);
		config.setPort(port);
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		
		try{
			jedisPool = RedisFactory.CreateConnection(host, port, timeout, maxTotal, maxIdle);
			logger.debug("TestRedis.beforeTest()::jedisPool="+jedisPool);
			redisClient = new Redis(jedisPool);
		}catch (Exception e){
			logger.debug("TestRedis.beforeTest()::Exception="+e);
		}

		logger.debug("TestRedis.beforeTest()::END");
	}

	//@Test
	public void pong() {
		logger.debug("TestRedis.pong()::START");
		String result = null;
//		prodClient.publishMessage(getProp("topic"), message);
		try{
			result = redisClient.ping();
			//try (Jedis jedis = jedisPool.getResource();) {
				//logger.info("jedis:"+jedis);
			//}
		}catch(Exception e){
			logger.info("TestRedis.pong()::Exception="+e);
		}
		Assert.assertEquals(result,"PONG");

		logger.info("TestRedis.pong()::END="+result);
	}
	
	//@Test(dependsOnMethods = {"pong"})
	public void send() throws Exception {
		logger.info("TestRedis.send()::START=");
		
		String result = null;
		try{
			result = redisClient.set("hello", "world", "UTF-8");
			logger.info("TestRedis.send()::result="+result);
		}catch(Exception e){
			logger.info("TestRedis.send()::Exception="+e);
		}
		Assert.assertEquals(result,"OK,-1");
		logger.info("TestRedis.send()::END="+result);
	}
	
	//@Test(dependsOnMethods = {"pong", "send"})
	public void get() throws Exception {
		logger.info("TestRedis.get()::START");
		
		String result = null;
		try{
			byte[] bytes = redisClient.get("hello");
			result = new String(bytes, "UTF-8");
			logger.info("TestRedis.send()::result="+result);
		}catch(Exception e){
			logger.info("TestRedis.get()::Exception="+e);
		}
		Assert.assertEquals(result,"world");
		logger.info("TestRedis.get()::END="+result);
	}
	
	//@Test(dependsOnMethods = {"pong", "send", "get"})
	public void destroy() {
		logger.info("TestRedis.destroy()::START");
//		prodClient.close();
//		try (Jedis jedis = jedisPool.getResource();) {
				//RedisFactory.ReleaseConnection(jedis);
//				jedis.destroy()
//		}
		try{
			jedisPool.destroy();;
		}catch(Exception e){
			logger.info("TestRedis.destroy()::Exception="+e);
		}
		logger.info("TestRedis.destroy()::END");
	}

	//@Test(dependsOnMethods = "destroy")
	public void connectionException() {
		logger.info("TestRedis.connectionException()::START");
		String result = null;
		try{
			result = redisClient.ping();
			//try (Jedis jedis = jedisPool.getResource();) {
				//logger.info("jedis:"+jedis);
			//}
		}catch(Exception e){
			logger.info("TestRedis.connectionException()::Exception="+e);
			result = e.toString();
			logger.info("TestRedis.connectionException()::exception.getClass().toString()="+e.getClass().getName());
//			Assert.assertEquals(e.getClass().getName() == "redis.clients.jedis.exceptions.JedisConnectionException", true);
			Assert.assertEquals(e.getClass(), redis.clients.jedis.exceptions.JedisConnectionException.class);
		}
		
		Assert.assertEquals(result,"redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool");
		logger.info("TestRedis.connectionException()::END="+result);

	}
	
	//@AfterTest
	public void afterTest() {
		logger.info("TestRedis.afterTest()::START");
		if(redisServer!=null){
			redisServer.stop();
			redisServer=null;
		}
//		prodClient.close();
//		try (Jedis jedis = jedisPool.getResource();) {
				//RedisFactory.ReleaseConnection(jedis);
//				jedis.destroy()
//		}
//		try{
//			jedisPool.destroy();;
//		}catch(Exception e){
//			logger.info("Exception="+e);
//		}
		logger.info("TestRedis.afterTest()::END");
	}
*/
}
