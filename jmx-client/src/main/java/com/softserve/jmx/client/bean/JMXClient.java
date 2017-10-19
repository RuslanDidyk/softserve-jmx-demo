package com.softserve.jmx.client.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.softserve.jmx.bean.ServerMXBean;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JMXClient {
	@Value("${jmx.connection}")
	private String jmxConnection;

	private ServerMXBean serverMXBean;

	private JMXConnector connector;

	/**
	 * Call this method before client usage for initialization.
	 */
	@PostConstruct
	public void init() {
		try {
			JMXServiceURL serviceURL = new JMXServiceURL(jmxConnection);
			connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection serverConnection = connector.getMBeanServerConnection();
			ObjectName mxBeanName = new ObjectName(ServerMXBean.JMXBeanName);
			serverMXBean = JMX.newMXBeanProxy(serverConnection, mxBeanName, ServerMXBean.class);
		} catch (IOException | MalformedObjectNameException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Call this method to close {@link JMXConnector}.
	 */
	@PreDestroy
	public void destroy() {
		try {
			connector.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public ServerMXBean getServerMXBean() {
		return serverMXBean;
	}

}
