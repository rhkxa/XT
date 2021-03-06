package com.gps808.app.models;

import java.util.List;

public class XbMonitor {
	private List<XbVehicle> locations;

	private List<StopPointInfo> stopPoints;

	private String mileage;

	private String totalTime;

	public List<StopPointInfo> getStopPoints() {
		return stopPoints;
	}

	public void setStopPoints(List<StopPointInfo> stopPoints) {
		this.stopPoints = stopPoints;
	}

	public List<XbVehicle> getLocations() {
		return locations;
	}

	public void setLocations(List<XbVehicle> locations) {
		this.locations = locations;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

}
