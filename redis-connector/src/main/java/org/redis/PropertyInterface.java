package org.redis;

import java.io.IOException;
import java.util.Properties;

public interface PropertyInterface{
	default public void loadProperties() {
		try {
			getProperties().load(PropertyInterface.class.getResourceAsStream("/it.properties"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	default public String getProp(String name) {
		String result = getProperties().getProperty(name);
		return result;
	}
	
	default public int getPropInt(String name) {
		int result = Integer.parseInt(getProp(name));
		return result;
	}
	
//	public Logger getLogger();
	
//	public void setProperties(Properties prop);
	
	public Properties getProperties();
}
