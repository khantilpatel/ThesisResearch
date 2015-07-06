package com.anomalydetection.slr;

import java.util.ArrayList;

public class SimpleLinearRegression {

	ArrayList<TimeSeriesDataPoint> history_datapoint_list = new ArrayList<TimeSeriesDataPoint>(
			0);

	ArrayList<Double> history_error_list = new ArrayList<Double>(0);

	final int N = 10;
	final double ALPHA = 2.75;

	private double error_mean = 0;

	private double error_std = 0;

	int countAnomalies = 0;

	boolean processCurrentDataPoint(TimeSeriesDataPoint dt) {

		if (dt.getValue_x() == 4.0) {
			System.out.println("");
		}
		boolean isAnomaly = false;
		// Step 1 : estimate beta
		double predicted_dt_value = calculateBeta(dt);

		// Step 2 : predict current dt
		// double predicted_dt_value = predictDataPoint(dt, beta);

		// Step 3: calculate error (difference)
		double error = dt.getValue_y() - predicted_dt_value;

		// Step 4: update mean and STD for error
		updateErrorFunction(error);

		// Step 5: check the z-value test on the errors
		isAnomaly = z_valueTest(error);

		if (isAnomaly)
			countAnomalies++;

		System.out.println("dt::" + dt.getValue_x()
				+ " dy::"
				+ dt.getValue_y()
				// + " || beta::" + beta
				+ " || y_cap::" + predicted_dt_value + " || error::" + error
				+ "|| erro_mean::" + this.error_mean + " || error_std::"
				+ this.error_std + " || isAnomaly::" + isAnomaly
				+ " || countAnomaly::" + countAnomalies);

		return isAnomaly;
	}

	private double calculateBeta(TimeSeriesDataPoint dt) {

		// double S_y = 0;
		// double S_xx = 0;
		// double S_x = 0;
		// double S_xy = 0;

		// declare function variable
		// double S_yy = 0;

		// STEP 2: calculate the beta from currently slidded window
		// S_yy
		// S_yy += dt.getValue_y() * dt.getValue_y();

		// S_y
		
		// STEP 1: Slide the history window according to N
	

		double xbar = AlgorithmUtility.cal_mean_x(history_datapoint_list);

		double ybar = AlgorithmUtility.cal_mean_y(history_datapoint_list);

		double xxbar = 0;

		double xybar = 0;

		for (TimeSeriesDataPoint d : history_datapoint_list) {

			xxbar += (d.getValue_x() - xbar) * (d.getValue_x() - xbar);
			xybar += (d.getValue_x() - xbar) * (d.getValue_y() - ybar);
			// S_y += d.getValue_y();
			// // S_xx
			// S_xx += d.getValue_x() * d.getValue_x();
			// // S_x
			// S_x += d.getValue_x();
			// // S_xy
			// S_xy += d.getValue_x() * d.getValue_y();
		}

		double beta1 = xybar / xxbar;

		double beta0 = ybar - (beta1 * xbar);

		double predicted_y =  beta0 + beta1 * dt.getValue_x();
		// double num = ((history_datapoint_list.size() * S_xy) - S_x * S_y);
		// double deno = ((history_datapoint_list.size() * S_xx) - (S_x * S_x));
		// double beta_cap = num / deno;

		if (!history_datapoint_list.isEmpty()
				&& history_datapoint_list.size() == N) {
			history_datapoint_list.remove(0);
		}

		history_datapoint_list.add(dt);

		return predicted_y;
	}

	private double predictDataPoint(TimeSeriesDataPoint dt, double beta) {

		double y_cap = dt.getValue_x() * beta;
		return y_cap;
	}

	private void updateErrorFunction(Double error) {
		if (!error.equals(Double.NaN)) {
			
			history_error_list.add(error);
			
			this.error_mean = AlgorithmUtility.cal_mean(history_error_list);

			this.error_std = AlgorithmUtility.calc_std_dev_givenMean(
					history_error_list, this.error_mean);

		
		}
	}

	private boolean z_valueTest(double error) {

		boolean result = false;
		double z_value = Math.abs((error - this.error_mean)) / this.error_std;

		if (z_value > ALPHA) {
			result = true;
		}

		return result;
	}
}
