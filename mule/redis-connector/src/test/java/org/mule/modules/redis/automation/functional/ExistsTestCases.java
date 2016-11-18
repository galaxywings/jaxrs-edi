package org.mule.modules.redis.automation.functional;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mule.modules.redis.automation.AutomationBase;

public class ExistsTestCases extends AutomationBase {
	
	@Test
	public void verify() throws Exception {
		getLogger().debug("ExistsTestCases.verify()::START");
		String key = "hello";
		String key2 = "helloNoGo";
		String value = "world";
		
		java.lang.String expectedSetResult = "OK,-1";
		
		String resultSet = getConnector().set(key, value, -1);
		assertEquals(expectedSetResult, resultSet);
		
		verifyKeyExists(key);
		
		verifyKeyDeosntExists(key2);
		getLogger().debug("ExistsTestCases.verify()::END");
	}
	
	public void verifyKeyExists(String key) throws Exception{
		getLogger().debug("ExistsTestCases.verifyKeyExists(key="+key+")::START");
		
		java.lang.Boolean expected = true;
		
		assertEquals(expected,getConnector().exists(key));
		
		getLogger().debug("ExistsTestCases.verify()::END");
	}
	
	public void verifyKeyDeosntExists(String key) throws Exception{
		getLogger().debug("ExistsTestCases.verifyKeyDeosntExists(key="+key+")::START");
		java.lang.Boolean expected = false;
		
		assertEquals(expected, getConnector().exists(key));
		getLogger().debug("ExistsTestCases.verify()::END");
	}

}