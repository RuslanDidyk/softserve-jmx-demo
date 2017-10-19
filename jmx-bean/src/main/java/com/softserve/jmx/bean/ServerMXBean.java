package com.softserve.jmx.bean;

/**
 * Bean for monitoring server work.
 * 
 * @author rdidyk
 */
public interface ServerMXBean {

	public static final String JMXBeanName = "com.softserve.jmx.bean:type=ServerMXBean";

	public Integer getPort();

	public String getLogLevel();

	public void setLogLevel(String level);

	public Integer getCacheSize();

	public void setCacheSize(Integer size);

	public String getStatus();

	public String stop();

	public String start();

}