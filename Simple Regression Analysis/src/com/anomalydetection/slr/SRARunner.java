package com.anomalydetection.slr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SRARunner {

	static List<Double> error_list = new ArrayList<Double>(0);
	//static Integer[] dataset_y = { 2, 5, 8, 1, 4, 11, 6, 9, 8, 1, 3, 5, 6, 3, 4, 5,
	//		6, 5, 5, 3, 6, 7, 3, 5, 6, 4, 14, 3, 5, 6, 4, 6, 7, 3 };

	static Integer[] dataset_y = { 5,5,3,6,9,3,4,6,14,3,5,6,4,6,7,3 };

	// static int[] dataset_x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	static Integer[] dataset_x = new Integer[50];// = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	static double S_yy = 0;
	static double S_y = 0;
	static double S_xx = 0;
	static double S_x = 0;
	static double S_xy = 0;

	static double cal_mean(List<Integer> bins) {
		double total = 0;
		for (int bin : bins) {
			total += bin;
		}
		return total / bins.size();
	}
	
	static double calc_std_dev(List<Integer> bins) {
		double total_dev = 0.0f;
		double window_mean = cal_mean(bins);
		for (int bin : bins) {
			double distance = Math.abs(bin - window_mean)
					* Math.abs(bin - window_mean);
			total_dev += distance;
		}

		double window_std_dev = Math.sqrt(total_dev / bins.size());
		
		return window_std_dev;
	}
	
	static List<Integer> getUniqueList(List<Integer> list)
	{
		List<Integer> unique_list = new ArrayList<Integer>(0);
		
		for (Integer integer : list) {
			if(!(unique_list.contains(integer)))
			{
				unique_list.add(integer);
			}
		}
		
		Collections.sort(unique_list);
		return unique_list;
	}
	
	static int getFrequency(int i, List<Integer> sorted_list)
	{
		int freq=0;
		
		for (Integer integer : sorted_list) 
			if(integer.equals(i))
			freq+=1;
		
		return freq;
	}
	
	static List<Integer> relativeFrequency(List<Integer> unique_list, List<Integer> sorted_list)
	{
		List<Integer> frequency_list = new ArrayList<Integer>(0);
		for (Integer integer : unique_list) {
			frequency_list.add(getFrequency(integer, sorted_list));
		}
		
		return frequency_list;
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < dataset_y.length; i++) {
			dataset_x[i] = new Integer(1);
		}
		
		List<Integer> dataset_y_list = new ArrayList<Integer>();
		List<Integer> dataset_x_list = new ArrayList<Integer>();
		
		dataset_y_list = Arrays.asList(dataset_y);

		dataset_x_list = Arrays.asList(dataset_x);

		for (int i = 0; i < dataset_y_list.size() - 1; i++) {
			// int i = 7;

			// S_yy
			for (int k = 0; k <= i; k++) {
				S_yy += dataset_y_list.get(k) * dataset_y_list.get(k);
			}

			// S_y
			for (int k = 0; k <= i; k++) {
				S_y += dataset_y_list.get(k);
			}

			// S_xx
			for (int k = 0; k <= i; k++) {
				S_xx += dataset_x_list.get(k) * dataset_x_list.get(k);
			}

			// S_x
			for (int k = 0; k <= i; k++) {
				S_x += dataset_x_list.get(k);
			}

			// S_xy
			for (int k = 0; k <= i; k++) {
				S_xy += dataset_x_list.get(k) * dataset_y_list.get(k);
			}

			double num = (((i + 1) * S_xy) - S_x * S_y);
			double deno = (((i + 1) * S_xx) - (S_x * S_x));
			double beta_cap = num / deno;

			int test = dataset_y_list.get(i+1);

			double y_cap = beta_cap * dataset_x_list.get(i + 1);

			double error = (test - y_cap);
			System.out.println(i + ". " + "S_x:" + S_x);
			System.out.println(i + ". " + "S_xx:" + S_xx);

			System.out.println(i + ". " + "S_y:" + S_y);
			System.out.println(i + ". " + "S_yy:" + S_yy);

			System.out.println(i + ". " + "S_xy:" + S_xy);

			System.out.println(i + ". " + "Beta_cap:" + beta_cap);

			System.out.println(i + ". " + "y_cap for " + test + ":" + y_cap);

			System.out.println(i + ". " + "error:" + error );
			
			System.out.println(i + ". " + "mean:" + cal_mean(dataset_y_list));
			
			System.out.println(i + ". " + "std_dev:" + calc_std_dev(dataset_y_list) + "\n\n");

			error_list.add(error);

		}

		int index = 0;
		for (double error : error_list) {
			System.out.println(index+". error:" + error + "\n");
			index++;
		}
		
		List<Integer> uniqueList = getUniqueList(dataset_y_list);
		 
		List<Integer> relativeFrequency = relativeFrequency(uniqueList,dataset_y_list);
	
		System.out.println("Values:\n");
		for (int j = 0; j < uniqueList.size(); j++) {
			
			System.out.println(uniqueList.get(j));
		}
		
		System.out.println("Relative Frequency:\n");
		
		double totalFreq=0;
		for (int j = 0; j < relativeFrequency.size(); j++) {
			double frequency = (double)relativeFrequency.get(j)/dataset_y_list.size();
			totalFreq +=frequency;
			System.out.println(frequency);
		}
		 
		System.out.println("TotalFreq:"+totalFreq);
	}
}
