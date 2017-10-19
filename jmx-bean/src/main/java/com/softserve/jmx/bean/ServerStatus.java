package com.softserve.jmx.bean;

/**
 * Enum contains server statuses.
 * 
 * @author rdidyk
 */
public enum ServerStatus {
	RUNNABLE("Server is ready to start."), 
	RUNNING("Server is already running!"), 
	STOPPED("Server is down!"),
	STARTED("Server has started successfully!"), 
	NOT_STARTED("Servier is not started yet!");

	private String status;

	ServerStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return status;
	}
}
