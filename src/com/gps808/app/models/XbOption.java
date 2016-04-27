package com.gps808.app.models;

public class XbOption {

	private int monitorInterval;
	private int trackInterval;
	private int stopDisplayInterval;

	public int getStopDisplayInterval() {
		return stopDisplayInterval;
	}

	public void setStopDisplayInterval(int stopDisplayInterval) {
		this.stopDisplayInterval = stopDisplayInterval;
	}

	public int getMonitorInterval() {
		return monitorInterval;
	}

	public void setMonitorInterval(int monitorInterval) {
		this.monitorInterval = monitorInterval;
	}

	public int getTrackInterval() {
		return trackInterval;
	}

	public void setTrackInterval(int trackInterval) {
		this.trackInterval = trackInterval;
	}

}
