package org.mule.modules.redis.automation.functional;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mule.modules.redis.automation.AutomationBase;

public class GetTestCases extends AutomationBase {

	@Test
	public void verify() throws Exception {
		getLogger().debug("GetTestCases.verify()::START");
		String key = "hello";
		String key2 = "helloDoesNotExist";
		String value = "world";
		int timeout = -1;
		
		java.lang.String expectedSetResult = "OK,-1";
		assertEquals(getConnector().set(key, value, timeout), expectedSetResult);
		
		getExistingKey(key,value);
		
		getNonExistingKey(key2);
		getLogger().debug("GetTestCases.verify()::END");
	}
	
	public void getExistingKey(String key, String value) throws Exception{
		getLogger().debug("GetTestCases.getExistingKey(key="+key+", value="+value+")::START");
		byte[] resultByte = getConnector().get(key);
		String result = new String(resultByte, "UTF-8");
		assertEquals(result, value);
		getLogger().debug("GetTestCases.getExistingKey()::END");
	}
	
	public void getNonExistingKey(String key) throws Exception{
		getLogger().debug("GetTestCases.getNonExistingKey(key="+key+")::START");
		byte[] resultByte = getConnector().get(key);

		assertEquals(resultByte, null);
		getLogger().debug("GetTestCases.getNonExistingKey()::END");
	}

}