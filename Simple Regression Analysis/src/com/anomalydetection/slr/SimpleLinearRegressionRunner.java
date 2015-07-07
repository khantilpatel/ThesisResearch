package com.anomalydetection.slr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleLinearRegressionRunner {

	static List<Double> dataset_x = new ArrayList<>(Arrays.asList(1.47, 1.50,
			1.52, 1.55, 1.57, 1.60, 1.63, 1.65, 1.68, 1.70, 1.73, 1.75, 1.78,
			1.80, 1.83));

	static List<Double> dataset_y = new ArrayList<>(Arrays.asList(52.21, 53.12,
			54.48, 55.84, 57.20, 58.57, 59.93, 61.29, 63.11, 64.47, 66.28,
			68.10, 69.92, 72.19, 74.46));

	public static void main(String[] args) {

		String pathCSV = "C:/Users/KhantilPatel/Dropbox/Thesis/Data-set/Andrews-Gavagai dataset/labeled/positivity_labelled.csv";

		List<TimeSeriesDataPoint> dataPoints = AlgorithmUtility
				.readFromCSV(pathCSV);

		int countAnomaliesReference = 0;
		for (TimeSeriesDataPoint dt : dataPoints) {

			if (dt.isAnomaly())
				countAnomaliesReference++;
			System.out.println("Date:" + dt.getDate() + " || value_y::"
					+ dt.getValue_y() + " || isAnomaly::" + dt.isAnomaly()
					+ " || countAnomalies::" + countAnomaliesReference);

		}

		for (double N = 5; N <= 50; N += 5) {
			for (double alpha = 5; N <= 50; N += 5) {
			SimpleLinearRegression slr = new SimpleLinearRegression(2, 2);

			// for(int i=0; i <dataset_x.size() ; i++){
			int i = 1;
			int countAnomaliesDetected = 0;
			int countTrueAnomalies = 0; // True-Positives
			for (TimeSeriesDataPoint dt : dataPoints) {
				// TimeSeriesDataPoint dataPoint = new
				// TimeSeriesDataPoint(dataset_x.get(i), dataset_y.get(i));

				dt.setValue_x(i);

				boolean isAnomalyDetected = slr.processCurrentDataPoint(dt);

				if (isAnomalyDetected) {
					countAnomaliesDetected++;

					if (dt.isAnomaly() == isAnomalyDetected) {
						countTrueAnomalies++;
					}
				}

				i++;
			}

			System.out.println("ReferenceAnomalies::" + countAnomaliesReference
					+ " || AnomaliesDetected::" + countAnomaliesDetected
					+ " || TrueAnomalies::" + countTrueAnomalies);

			double true_positive = countTrueAnomalies;
			double false_positive = countAnomaliesDetected - true_positive;
			double false_negative = countAnomaliesReference - true_positive;

			double precession = (double) (countTrueAnomalies)
					/ (countAnomaliesDetected);
			double recall = (double) (countTrueAnomalies)
					/ (countAnomaliesReference);
			double f_score = (double) 2
					* ((precession * recall) / (precession + recall));
			System.out.println("Precession::" + precession + " || Recall::"
					+ recall + " || F-Score::" + f_score);
			System.out.println("false_positive::" + false_positive
					+ " || false_negative" + false_negative);
		}
		// int countAnomalies = 0;
		// for (TimeSeriesDataPoint dt : dataPoints) {
		//
		// if(dt.isAnomaly())
		// countAnomalies++;
		// System.out.println("Date:"+dt.getDate()+ " || value_y::" +
		// dt.getValue_y() + " || isAnomaly::" + dt.isAnomaly()
		// +" || countAnomalies::"+countAnomalies);
		//
		//
		// }

	}
}
