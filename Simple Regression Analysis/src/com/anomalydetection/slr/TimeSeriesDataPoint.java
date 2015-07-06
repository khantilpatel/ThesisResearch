package com.anomalydetection.slr;

import java.util.Date;

public class TimeSeriesDataPoint {
	
	Date date;
	double value_y;
	double value_x;
	boolean anomaly;
	
	
	public TimeSeriesDataPoint() {
		
	}
	
	public TimeSeriesDataPoint(double value_x, double value_y) {
		super();
		this.value_y = value_y;
		this.value_x = value_x;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isAnomaly() {
		return anomaly;
	}

	public void setAnomaly(boolean anomaly) {
		this.anomaly = anomaly;
	}

	public double getValue_y() {
		return value_y;
	}

	public void setValue_y(double value_y) {
		this.value_y = value_y;
	}

	public double getValue_x() {
		return value_x;
	}

	public void setValue_x(double value_x) {
		this.value_x = value_x;
	}
	
	

}
