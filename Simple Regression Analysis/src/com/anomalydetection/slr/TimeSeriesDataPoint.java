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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (anomaly ? 1231 : 1237);
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value_x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(value_y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeSeriesDataPoint other = (TimeSeriesDataPoint) obj;
		if (anomaly != other.anomaly)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(value_x) != Double
				.doubleToLongBits(other.value_x))
			return false;
		if (Double.doubleToLongBits(value_y) != Double
				.doubleToLongBits(other.value_y))
			return false;
		return true;
	}
	
	

}
