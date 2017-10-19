package com.softserve.jmx.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.jmx.bean.ServerMXBean;
import com.softserve.jmx.client.bean.JMXClient;
import com.softserve.jmx.client.dto.ServerDTO;

@RestController
@RequestMapping(value = "/api/jmx")
public class ServerController {

	private JMXClient jmxClient;

	@Autowired
	public ServerController(JMXClient jmxClient) {
		this.jmxClient = jmxClient;
	}

	@RequestMapping(value = "/server", method = RequestMethod.GET)
	public ServerDTO getServerInfo() {
		ServerMXBean serverMXBean = jmxClient.getServerMXBean();
		return new ServerDTO(serverMXBean.getPort(), serverMXBean.getCacheSize(), serverMXBean.getLogLevel(),
				serverMXBean.getStatus());
	}

	@RequestMapping(value = "/server/start", method = RequestMethod.POST)
	public String startServer() {
		return jmxClient.getServerMXBean().start();
	}

	@RequestMapping(value = "/server/stop", method = RequestMethod.POST)
	public String stopServer() {
		return jmxClient.getServerMXBean().stop();
	}

}
