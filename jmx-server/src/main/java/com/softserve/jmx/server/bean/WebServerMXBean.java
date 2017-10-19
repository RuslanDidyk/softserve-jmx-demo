package com.softserve.jmx.server.bean;

import com.softserve.jmx.bean.ServerMXBean;
import com.softserve.jmx.bean.ServerStatus;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WebServerMXBean implements ServerMXBean {

	private Integer port;
	private Integer cacheSize;
	private String level;
	private boolean started;

	/**
	 * Initialize server with port and log level.
	 * 
	 * @param port
	 *            server port
	 * @param cacheSize
	 *            cache size
	 * @param level
	 *            logging level
	 */
	public WebServerMXBean(Integer port, Integer cacheSize, String level) {
		this.port = port;
		this.cacheSize = cacheSize;
		this.level = level;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	@Override
	public String getLogLevel() {
		return level;
	}

	@Override
	public void setLogLevel(String level) {
		log.info(String.format("Call setLogLevel('%s') on server bean.", level));
		this.level = level;
	}

	@Override
	public Integer getCacheSize() {
		return cacheSize;
	}

	@Override
	public synchronized void setCacheSize(Integer cacheSize) {
		log.info(String.format("Call setCacheSize('%s') on server bean.", cacheSize));
		this.cacheSize = cacheSize;
	}

	@Override
	public String getStatus() {
		return started ? ServerStatus.RUNNING.getStatus() : ServerStatus.RUNNABLE.getStatus();
	}

	@Override
	public synchronized String stop() {
		log.info("Call stop() on server bean.");
		if (started) {
			sleep();
			started = false;
			return ServerStatus.STOPPED.getStatus();
		} else {
			return ServerStatus.NOT_STARTED.getStatus();
		}
	}

	@Override
	public synchronized String start() {
		log.info("Call start() on server bean.");
		if (!started) {
			sleep();
			started = true;
			return ServerStatus.STARTED.getStatus();
		} else {
			return ServerStatus.RUNNING.getStatus();
		}
	}

	/**
	 * Emulates some work.
	 */
	private static void sleep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

}
