package org.redis.test;


import org.redis.LoggerInterface;
import org.redis.PropertyInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.testng.annotations.BeforeSuite;

import java.util.Properties;

public class TestBase implements PropertyInterface, LoggerInterface {

	protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);

	protected static final Properties properties = new Properties();

	//@BeforeSuite
	public void setup() {
		getLogger().debug("TestBase.beforeTest()::START");
		// LOAD Properties
		getLogger().debug("TestBase.beforeTest()::Loading Properties");
		loadProperties();
		getLogger().debug("TestBase.beforeTest()::END");
	}

	//@Override
	public Logger getLogger() {
		logger.debug("TestBase.getLogger()::START");
		logger.debug("TestBase.getLogger()::END");
		return logger;
	}


	//@Override
	public Properties getProperties() {
		getLogger().debug("TestBase.getProperties()::START");
		getLogger().debug("TestBase.getProperties()::END");
		return properties;
	}

}
