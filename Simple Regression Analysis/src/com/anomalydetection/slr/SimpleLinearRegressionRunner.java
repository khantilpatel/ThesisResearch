package com.anomalydetection.slr;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleLinearRegressionRunner {

	static List<Double> dataset_x = new ArrayList<>(Arrays.asList(1.47, 1.50,
			1.52, 1.55, 1.57, 1.60, 1.63, 1.65, 1.68, 1.70, 1.73, 1.75, 1.78,
			1.80, 1.83));

	static List<Double> dataset_y = new ArrayList<>(Arrays.asList(52.21, 53.12,
			54.48, 55.84, 57.20, 58.57, 59.93, 61.29, 63.11, 64.47, 66.28,
			68.10, 69.92, 72.19, 74.46));

	static int N = 10;
	static double ALPHA = 5.25;
	static int error_function_window_length = 6500;

	public static void main(String[] args) {

		String pathCSV_positive = "C:/Users/KhantilPatel/Dropbox/Thesis/Data-set/Andrews-Gavagai dataset/labeled/positivity_labelled.csv";
		String pathCSV_negative = "C:/Users/KhantilPatel/Dropbox/Thesis/Data-set/Andrews-Gavagai dataset/labeled/negativity_labelled.csv";
		String pathCSV_frequency = "C:/Users/KhantilPatel/Dropbox/Thesis/Data-set/Andrews-Gavagai dataset/labeled/frequency_labelled.csv";

		List<TimeSeriesDataPoint> dataPoints_positive = AlgorithmUtility
				.readFromCSV(pathCSV_positive);

		List<TimeSeriesDataPoint> dataPoints_negative = AlgorithmUtility
				.readFromCSV(pathCSV_negative);

		List<TimeSeriesDataPoint> dataPoints_frequency = AlgorithmUtility
				.readFromCSV(pathCSV_frequency);

		List<ResultAnalysis> resultAnalysis = new ArrayList<ResultAnalysis>(0);
		int countAnomaliesReference = 0;

		List<TimeSeriesDataPoint> dataPoints;
		String result_filename;
		for (int dataset_id = 1; dataset_id <= 3; dataset_id++) {

			resultAnalysis = new ArrayList<ResultAnalysis>(0);
			
			switch (dataset_id) {

			case 1:
				dataPoints = dataPoints_frequency;
				result_filename = "frequency_results";
				break;

			case 2:
				dataPoints = dataPoints_positive;
				result_filename = "positive_results";
				break;

			case 3:
				dataPoints = dataPoints_negative;
				result_filename = "negative_results";
				break;

			default:
				dataPoints = dataPoints_frequency;
				result_filename = "frequency_results";
				break;

			}

			for (TimeSeriesDataPoint dt : dataPoints) {

				if (dt.isAnomaly())
					countAnomaliesReference++;
				// System.out.println("Date:" + dt.getDate() + " || value_y::"
				// + dt.getValue_y() + " || isAnomaly::" + dt.isAnomaly()
				// + " || countAnomalies::" + countAnomaliesReference);
			}

			//for (int n = 24; n <= 336; n += 24) {
			for (int n = 5; n <= 50; n += 5) {
				for (double alpha = 1; alpha <= 3; alpha += 0.25) {

					SimpleLinearRegression slr = new SimpleLinearRegression(n,
							alpha);

					int i = 1;
					int countAnomaliesDetected = 0;
					int countTrueAnomalies = 0; // True-Positives
					for (TimeSeriesDataPoint dt : dataPoints) {

						dt.setValue_x(i);

						boolean isAnomalyDetected = slr
								.processCurrentDataPoint(dt);

						if (isAnomalyDetected) {

							countAnomaliesDetected++;

							if (dt.isAnomaly() && isAnomalyDetected) {
								countTrueAnomalies++;
							}
						}

						i++;
					}

					// Positive Class
					double positive = countAnomaliesReference;
					double true_positive = countTrueAnomalies;
					double false_negative = countAnomaliesReference
							- true_positive;

					// Negative Class
					double negative = dataPoints.size()
							- countAnomaliesReference;
					double true_negative = (double) ((dataPoints.size() - countAnomaliesDetected) - false_negative);
					double false_positive = countAnomaliesDetected
							- true_positive;

					// Type 1:: False Positive Rate
					double false_positive_rate = false_positive / negative;

					// TYpe 2:: False Negative Rate
					double false_negative_rate = false_negative / positive;

					double precession = (double) (countTrueAnomalies)
							/ (countAnomaliesDetected);
					double recall = (double) (countTrueAnomalies)
							/ (countAnomaliesReference);
					double f_score = (double) 2
							* ((precession * recall) / (precession + recall));

					ResultAnalysis analysis = new ResultAnalysis(n, alpha,
							error_function_window_length, precession, recall,
							f_score, false_positive_rate, false_negative_rate);

					// System.out.println(analysis.toString());

					resultAnalysis.add(analysis);

				}

			}

			System.out.println("\n\n:::: Sorted by F-score :::::");

			Collections.sort(resultAnalysis);

//			for (ResultAnalysis resultAnalysis2 : resultAnalysis) {
//				System.out.println(resultAnalysis2.toString());
//			}

			try {
				String delimiter = ",";
				FileWriter fw_csv = new FileWriter(
						"C:/Users/KhantilPatel/Dropbox/Thesis/Data-set/Andrews-Gavagai dataset/results/"
								+ result_filename + ".csv");
				fw_csv.write("window_length" + delimiter + "threshold"
						+ delimiter + "error_function_window_length"
						+ delimiter + "precession" + delimiter + "recall"
						+ delimiter + "f_score" + delimiter + "type1_error"
						+ delimiter + "type2_error"
						+ System.getProperty("line.separator"));

				// int counter =1;
				for (ResultAnalysis resultAnalysis2 : resultAnalysis) {

					fw_csv.write(resultAnalysis2.getWindow_length() + delimiter
							+ resultAnalysis2.getThreshold() + delimiter
							+ resultAnalysis2.getError_function_window_length()
							+ delimiter + resultAnalysis2.getPrecession()
							+ delimiter + resultAnalysis2.getRecall()
							+ delimiter + resultAnalysis2.getF_score()
							+ delimiter + resultAnalysis2.getType1_error()
							+ delimiter + resultAnalysis2.getType2_error()
							+ delimiter + System.getProperty("line.separator"));
					
					// counter++;
				}
				fw_csv.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
