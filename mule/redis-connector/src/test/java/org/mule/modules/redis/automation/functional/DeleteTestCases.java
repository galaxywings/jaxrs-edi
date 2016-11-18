package org.mule.modules.redis.automation.functional;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mule.modules.redis.automation.AutomationBase;

public class DeleteTestCases extends AutomationBase {
	
	@Test
	public void verify() throws Exception {
		getLogger().debug("DeleteTestCases.verify()::START");
		String key = "hello";
		String value = "world";
		
		java.lang.String expectedSetResult = "OK,-1";
		
		String resultSet = getConnector().set(key, value, -1);
		assertEquals(expectedSetResult, resultSet);
		
		byte[] resultByte = getConnector().get(key);
		String result = new String(resultByte, "UTF-8");
		assertEquals(value, result);
		
		Long resultDel = getConnector().delete(key);
		Long expectedDel = Long.valueOf("1");
		assertEquals(expectedDel, resultDel);
		
		resultByte = getConnector().get(key);
		assertEquals(null, resultByte);
		
		getLogger().debug("DeleteTestCases.verify()::END");
	}

}