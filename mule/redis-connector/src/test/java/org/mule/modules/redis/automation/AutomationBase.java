package org.mule.modules.redis.automation;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.mule.modules.redis.RedisConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;
import org.redis.LoggerInterface;
import org.redis.PropertyInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import redis.embedded.RedisServer;

public class AutomationBase extends AbstractTestCase<RedisConnector> implements PropertyInterface, LoggerInterface {
	protected static final Logger logger = LoggerFactory.getLogger(AutomationBase.class);
	protected static final Properties properties = new Properties();
	
	public AutomationBase() {
		super(RedisConnector.class);
	}
	
	@Before
	public void setup() {
		getLogger().info("AutomationBase.setup()::START");
		
		loadProperties();

		getLogger().info("AutomationBase.setup()::END");
	}
	
	@After
	public void tearDown() {
		getLogger().debug("AutomationBase.tearDown()::START");
		getLogger().debug("AutomationBase.tearDown()::END");
	}

	@Override
	public Logger getLogger() {
		logger.debug("AutomationBase.getLogger()::START");
		logger.debug("AutomationBase.getLogger()::END");
		return logger;
	}


	@Override
	public Properties getProperties() {
		getLogger().debug("AutomationBase.getProperties()::START");
		getLogger().debug("AutomationBase.getProperties()::END");
		return properties;
	}
	
	
}
