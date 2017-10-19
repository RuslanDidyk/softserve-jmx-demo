package com.softserve.jmx.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.softserve.jmx.bean.ServerMXBean;
import com.softserve.jmx.server.bean.NotificationWebServerMXBean;
import com.softserve.jmx.server.bean.WebServerMXBean;
import com.softserve.jmx.server.bean.notification.filter.CacheNotificationFilter;
import com.softserve.jmx.server.bean.notification.filter.LogLevelNotificationFilter;
import com.softserve.jmx.server.bean.notification.listener.CacheNotificationListener;
import com.softserve.jmx.server.bean.notification.listener.LogLevelNotificationListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Application {

	public static void main(String[] args) {
		log.info("Creating server bean instance...");
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ServerMXBean server = createWebServerBean();
		// or server = createNotificationWebServerBean() for notifications example
		try {
			ObjectName jmxBeanName = new ObjectName(ServerMXBean.JMXBeanName);
			log.info("Registed bean on MBeanServer...");
			mbs.registerMBean(server, jmxBeanName);
			log.info("Ready to work!");

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String currentLine = null;
			while (!"exit".equalsIgnoreCase(currentLine)) {
				currentLine = reader.readLine();
			}
			log.info("Unregisted bean from MBeanServer...");
			mbs.unregisterMBean(jmxBeanName);
			log.info("Stopped!");
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
	}

	private static ServerMXBean createWebServerBean() {
		return new WebServerMXBean(8080, 1024, "INFO");
	}

	private static ServerMXBean createNotificationWebServerBean() {
		NotificationWebServerMXBean server = new NotificationWebServerMXBean(8080, 1024, "INFO");
		server.addNotificationListener(new CacheNotificationListener(), new CacheNotificationFilter(), null);
		server.addNotificationListener(new LogLevelNotificationListener(), new LogLevelNotificationFilter(), null);
		return server;
	}
}
