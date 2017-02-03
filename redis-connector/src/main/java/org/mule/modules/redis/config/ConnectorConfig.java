package org.mule.modules.redis.config;

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {
    
    /**
     * Connector host
     */
    @Configurable
    @Default("localhost")
	private String host = "localhost";
    
    /**
     * Connector port
     */
    @Configurable
    @Default("6379")
	private int port = 6379;
    
    /**
     * Connector timeout in milliseconds
     */
    @Configurable
    @Default("2000")
	private int timeout = 2;
    
    /**
     * Pool max threads
     */
    @Configurable
    @Default("8")
	private int maxTotal = 8;
    
    /**
     * Pool max idle threads
     */
    @Configurable
    @Default("8")
	private int maxIdle = 8;
	
    /**
     * Get host
     *
     * @return host the host
     */
    public String getHost() {
		return host;
	}

    /**
     * Set host
     *
     * @param reply the reply
     */
	public void setHost(String host) {
		this.host = host;
	}
	
    /**
     * Get maxTotal
     *
     * @return maxTotal the maxTotal
     */
	public int getMaxTotal() {
		return maxTotal;
	}
	
	/**
     * Set maxTotal
     *
     * @param maxTotal the maximum total pool threads
     */
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	/**
     * Get maxTotal
     *
     * @return maxTotal the maxTotal
     */
	public int getMaxIdle() {
		return this.maxIdle;
	}

	/**
     * Set maxIdle
     *
     * @param maxIdle the maximum idle pool threads
     */
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	
	/**
     * Get port
     *
     * @return port the port
     */
	public int getPort() {
		return this.port;
	}

	/**
     * Set port
     *
     * @param port the connector port
     */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
     * Get timeout
     *
     * @return timeout the connector timeout
     */
	public int getTimeout() {
		return this.timeout;
	}

	/**
     * Set timeout
     *
     * @param timeout the connector timeout
     */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}