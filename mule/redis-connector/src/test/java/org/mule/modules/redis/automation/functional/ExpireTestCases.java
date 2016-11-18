package org.mule.modules.redis.automation.functional;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mule.modules.redis.automation.AutomationBase;

public class ExpireTestCases extends AutomationBase {

	@Test
	public void verify() throws Exception {
		getLogger().debug("ExpireTestCases.verify()::START");
		String key = "hello";
		String value = "world";
		
		java.lang.String expectedSetResult = "OK,-1";
		
		String resultSet = getConnector().set(key, value, -1);
		assertEquals(resultSet, expectedSetResult);
		
		java.lang.Long expected =  Long.valueOf("-1");
		
		Long resultTTL = getConnector().getTtl(key);
		assertEquals(expected, resultTTL);
		
		expected = Long.valueOf("1");

		int timeout = 10;
		assertEquals(expected, getConnector().expire(key, timeout));
		
		expected = Long.valueOf("10");
		
		resultTTL = getConnector().getTtl(key);
		assertEquals(expected, resultTTL);
		getLogger().debug("ExpireTestCases.verify()::END");
	}

}