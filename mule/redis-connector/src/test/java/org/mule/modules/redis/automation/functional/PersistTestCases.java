package org.mule.modules.redis.automation.functional;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mule.modules.redis.automation.AutomationBase;

public class PersistTestCases extends AutomationBase {
	
	@Test
	public void verify() throws Exception {
		getLogger().debug("PersistTestCases.verify()::START");
		String key = "hello";
		String value = "world";
		
		java.lang.String expectedSetResult = "OK,-1";
		
		String resultSet = getConnector().set(key, value, -1);
		assertEquals(resultSet, expectedSetResult);
		
		java.lang.Long expected = new Long(0);
		assertEquals(getConnector().persist(key), expected);
		getLogger().debug("PersistTestCases.verify()::END");
	}

}