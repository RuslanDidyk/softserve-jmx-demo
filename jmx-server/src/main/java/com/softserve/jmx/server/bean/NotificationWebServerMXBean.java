package com.softserve.jmx.server.bean;

import java.util.concurrent.atomic.AtomicInteger;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

import com.softserve.jmx.bean.ServerMXBean;
import com.softserve.jmx.bean.ServerStatus;
import com.softserve.jmx.server.bean.notification.NotificationConstants;

import lombok.extern.log4j.Log4j2;

/**
 * To generate notifications, an MBean must implement the interface
 * NotificationEmitter or extend NotificationBroadcasterSupport.
 * 
 * @author rdidyk
 */
@Log4j2
public class NotificationWebServerMXBean extends NotificationBroadcasterSupport implements ServerMXBean {

	public static final String LOG_LEVEL_ATTR = "logLevel";
	public static final String CACHE_ATTR = "cacheSize";

	private Integer port;
	private Integer cacheSize;
	private AtomicInteger cacheSequenceNumber = new AtomicInteger(1);
	private String logLevel;
	private AtomicInteger logLevelSequenceNumber = new AtomicInteger(1);
	private boolean started;

	/**
	 * Initialize server with port and log level.
	 * 
	 * @param port
	 *            server port
	 * @param cacheSize
	 *            cache size
	 * @param logLevel
	 *            logging level
	 */
	public NotificationWebServerMXBean(Integer port, Integer cacheSize, String logLevel) {
		this.port = port;
		this.cacheSize = cacheSize;
		this.logLevel = logLevel;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	@Override
	public String getLogLevel() {
		return logLevel;
	}

	@Override
	public synchronized void setLogLevel(String logLevel) {
		log.info(String.format("Call setLogLevel('%s') on server bean.", logLevel));
		String oldLogLevel = this.logLevel;
		this.logLevel = logLevel;
		Notification notificationn = new AttributeChangeNotification(this, logLevelSequenceNumber.getAndIncrement(),
				System.currentTimeMillis(), NotificationConstants.LOG_LEVEL_NOTIFICATION, LOG_LEVEL_ATTR,
				String.class.getSimpleName(), oldLogLevel, this.logLevel);
		sendNotification(notificationn);
	}

	@Override
	public Integer getCacheSize() {
		return cacheSize;
	}

	@Override
	public synchronized void setCacheSize(Integer cacheSize) {
		log.info(String.format("Call setCacheSize('%s') on server bean.", cacheSize));
		Integer oldCacheSize = this.cacheSize;
		this.cacheSize = cacheSize;

		Notification notificationn = new AttributeChangeNotification(this, cacheSequenceNumber.getAndIncrement(),
				System.currentTimeMillis(), NotificationConstants.CACHE_NOTIFICATION, CACHE_ATTR,
				Integer.class.getSimpleName(), oldCacheSize, this.cacheSize);
		sendNotification(notificationn);
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
			// Send notification to notify about server shutdown!
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
			// Send notification to notify about server turn on!
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

	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
		String name = AttributeChangeNotification.class.getName();
		String description = "An attribute of this NotificationWebServerMXBean has changed";
		MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
		return new MBeanNotificationInfo[] { info };
	}

}
