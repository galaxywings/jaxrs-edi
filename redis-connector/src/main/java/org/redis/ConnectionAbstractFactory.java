package org.redis;

public abstract class ConnectionAbstractFactory<T> {
	
	public abstract T getObject();
}
