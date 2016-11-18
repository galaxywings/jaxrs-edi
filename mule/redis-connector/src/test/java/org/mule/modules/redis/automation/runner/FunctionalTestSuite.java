package org.mule.modules.redis.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
//import org.mule.modules.redis.automation.functional.RedisTestCases;
import org.mule.modules.redis.RedisConnector;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;
import org.redis.LoggerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.embedded.RedisServer;

//import org.mule.modules.redis.automation.functional.AutomationBase;
import org.mule.modules.redis.automation.functional.PingTestCases;
import org.mule.modules.redis.automation.functional.ConnectivityTestCases;
import org.mule.modules.redis.automation.functional.DeleteTestCases;
import org.mule.modules.redis.automation.functional.ExistsTestCases;
import org.mule.modules.redis.automation.functional.ExpireTestCases;
import org.mule.modules.redis.automation.functional.GetTestCases;
import org.mule.modules.redis.automation.functional.GetTtlTestCases;
import org.mule.modules.redis.automation.functional.PersistTestCases;
import org.mule.modules.redis.automation.functional.SetTestCases;

@RunWith(Suite.class)
@SuiteClasses({ 
	PingTestCases.class, 
	SetTestCases.class, 
	GetTestCases.class, 
	ExistsTestCases.class, 
	ExpireTestCases.class, 
	GetTtlTestCases.class, 
	PersistTestCases.class, 
	DeleteTestCases.class, 
	ConnectivityTestCases.class 
	})

public class FunctionalTestSuite implements LoggerInterface {
	protected static final Logger logger = LoggerFactory.getLogger(FunctionalTestSuite.class);
	protected static RedisServer redisServer = null;

	@BeforeClass
	public static void initialiseSuite() {
		logger.debug("FunctionalTestSuite.initialiseSuite()::START");
		
		startRedis();
		
		ConnectorTestContext.initialize(RedisConnector.class);
		
		logger.debug("FunctionalTestSuite.initialiseSuite()::END");
	}

	@AfterClass
	public static void shutdownSuite() {
		logger.debug("FunctionalTestSuite.initialiseSuite()::START");
		
		ConnectorTestContext.shutDown();
		
		stopRedis();
		
		logger.debug("FunctionalTestSuite.initialiseSuite()::END");
	}
	
	@Override
	public Logger getLogger() {
		logger.debug("FunctionalTestSuite.getLogger()::START");
		logger.debug("FunctionalTestSuite.getLogger()::END");
		return logger;
	}
	
	public static void startRedis(){
		logger.debug("FunctionalTestSuite.startRedis()::START");
		try {
			if (redisServer == null) {
				redisServer = new RedisServer(6379);
				redisServer.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("FunctionalTestSuite.startRedis()::END");
	}
	
	public static void stopRedis(){
		logger.debug("FunctionalTestSuite.stopRedis()::START");
		if (redisServer != null) {
			redisServer.stop();
			redisServer = null;
		}
		logger.debug("FunctionalTestSuite.stopRedis()::END");
	}

}