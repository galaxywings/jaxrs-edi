package org.mule.modules.redis.automation.functional;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mule.modules.redis.automation.AutomationBase;

public class GetTtlTestCases extends AutomationBase {

	@Test
	public void verify() throws Exception {
		getLogger().debug("GetTtlTestCases.verify()::START");
		String key = "hello";
		String value = "world";
		int timeout = 10;
		
		java.lang.String expectedSetResult = "OK,1";
		assertEquals(expectedSetResult, getConnector().set(key, value, timeout));
		
		Long resultTTL = getConnector().getTtl(key);
		getLogger().debug("GetTtlTestCases.verify()::resultTTL="+resultTTL);
		
		Long expectedTTL = Long.valueOf("10");
		getLogger().debug("GetTtlTestCases.verify()::expectedTTL="+expectedTTL);
		
		getLogger().debug("GetTtlTestCases.verify()::expectedTTL==resultTTL="+(expectedTTL==resultTTL));
		assertEquals(expectedTTL, resultTTL);
		
		getLogger().debug("GetTtlTestCases.verify()::END");
	}

}