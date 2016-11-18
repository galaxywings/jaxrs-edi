package org.mule.modules.redis.automation.functional;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mule.modules.redis.automation.AutomationBase;
import org.mule.modules.redis.automation.runner.FunctionalTestSuite;

public class ConnectivityTestCases extends AutomationBase {

	@Override
	@Before
	public void setup() {
		FunctionalTestSuite.stopRedis();
	}
	
	@Test
	public void verify() throws Exception {
		getLogger().debug("ConnectivityTestCases.verify()::START");
		
		String result = null;
		try{
			result = getConnector().ping();
		}catch(Exception e){
			getLogger().debug("ConnectivityTestCases.verify()::Exception="+e);
			assertEquals(redis.clients.jedis.exceptions.JedisConnectionException.class, e.getClass());
		}
		getLogger().debug("ConnectivityTestCases.verify()::result="+result);
		assertEquals(null, result);
		getLogger().debug("ConnectivityTestCases.verify()::END");
	}
}
