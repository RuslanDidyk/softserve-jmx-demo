package com.softserve.jmx.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServerDTO {
	private Integer port;
	private Integer cacheSize;
	private String logLevel;
	private String status;
}
