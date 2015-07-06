package com.anomalydetection.slr;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class AlgorithmUtility {

	static double cal_mean(List<Double> bins) {
		double total = 0;
		for (Double bin : bins) {
			total += bin;
		}
		return total / bins.size();
	}
	
	static double cal_mean_x(List<TimeSeriesDataPoint> bins) {
		double total = 0;
		for (TimeSeriesDataPoint bin : bins) {
			total += bin.getValue_x();
		}
		return total / bins.size();
	}
	
	
	static double cal_mean_y(List<TimeSeriesDataPoint> bins) {
		double total = 0;
		for (TimeSeriesDataPoint bin : bins) {
			total += bin.getValue_y();
		}
		return total / bins.size();
	}

	static double calc_std_dev(List<Double> bins) {
		double total_dev = 0.0f;
		double window_mean = cal_mean(bins);
		for (Double bin : bins) {
			double distance = Math.abs(bin - window_mean)
					* Math.abs(bin - window_mean);
			total_dev += distance;
		}

		double window_std_dev = Math.sqrt(total_dev / bins.size());

		return window_std_dev;
	}

	static double calc_std_dev_givenMean(List<Double> bins, double window_mean) {
		double total_dev = 0.0f;
		// double window_mean = cal_mean(bins);
		for (Double bin : bins) {
			double distance = Math.abs(bin - window_mean)
					* Math.abs(bin - window_mean);
			total_dev += distance;
		}

		double window_std_dev = Math.sqrt(total_dev / bins.size());

		return window_std_dev;
	}

	static List<TimeSeriesDataPoint> readFromCSV(String filePath) {
		System.out.println("::Reading CSV::");
		ArrayList<TimeSeriesDataPoint> list = new ArrayList<TimeSeriesDataPoint>(
				0);
		try {

			CSVReader csvReader = new CSVReader(new FileReader(filePath));

			String[] row = null;

		
			row = csvReader.readNext();
			
			System.out.println(row[0] + " # " + row[1] + " #  " + row[2]);
			
			while ((row = csvReader.readNext()) != null) {

				TimeSeriesDataPoint dataPoint = new TimeSeriesDataPoint();
				
				System.out.println(row[0] + " # " + row[1] + " #  " + row[2]);

				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"MM/dd/yyyy hh:mm"); // .parse("2013-06-20 00:00:00")

				dataPoint.setDate(dateFormat.parse(row[0]));

				dataPoint.setValue_y(Integer.parseInt(row[1]));

				dataPoint.setAnomaly(false);
				String anomaly = row[2];

				if (anomaly.equals("1")) {
					dataPoint.setAnomaly(true);
				}

				list.add(dataPoint);
			}
			csvReader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

}
