package com.anomalydetection.evaluation;

import java.util.ArrayList;
import java.util.List;

public class SRARunner {

	static List<Double> error_list = new ArrayList<Double>(0);
	static int[] dataset_y = { 2, 5, 8, 1, 4, 11, 6, 9, 8, 1, 3, 5, 6, 3, 4, 5,
			6, 5, 5, 3, 6, 7, 3, 5, 6, 4, 14, 3, 5, 6, 4, 6, 7, 3 };

	// static int[] dataset_x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	static int[] dataset_x = new int[50];// = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	static double S_yy = 0;
	static double S_y = 0;
	static double S_xx = 0;
	static double S_x = 0;
	static double S_xy = 0;

	public static void main(String[] args) {

		for (int i = 0; i < dataset_y.length; i++) {
			dataset_x[i] += 1;
		}

		for (int i = 0; i < dataset_y.length - 1; i++) {
			// int i = 7;

			// S_yy
			for (int k = 0; k <= i; k++) {
				S_yy += dataset_y[k] * dataset_y[k];
			}

			// S_y
			for (int k = 0; k <= i; k++) {
				S_y += dataset_y[k];
			}

			// S_xx
			for (int k = 0; k <= i; k++) {
				S_xx += dataset_x[k] * dataset_x[k];
			}

			// S_x
			for (int k = 0; k <= i; k++) {
				S_x += dataset_x[k];
			}

			// S_xy
			for (int k = 0; k <= i; k++) {
				S_xy += dataset_x[k] * dataset_y[k];
			}

			double num = (((i + 1) * S_xy) - S_x * S_y);
			double deno = (((i + 1) * S_xx) - (S_x * S_x));
			double beta_cap = num / deno;

			int test = dataset_y[i + 1];

			double y_cap = beta_cap * dataset_x[i + 1];

			double error = (test - y_cap);
			System.out.println(i + ". " + "S_x:" + S_x);
			System.out.println(i + ". " + "S_xx:" + S_xx);

			System.out.println(i + ". " + "S_y:" + S_y);
			System.out.println(i + ". " + "S_yy:" + S_yy);

			System.out.println(i + ". " + "S_xy:" + S_xy);

			System.out.println(i + ". " + "Beta_cap:" + beta_cap);

			System.out.println(i + ". " + "y_cap for " + test + ":" + y_cap);

			System.out.println(i + ". " + "error:" + error + "\n\n");

			error_list.add(error);

		}

		int index = 0;
		for (double error : error_list) {
			System.out.println(index+". error:" + error + "\n");
			index++;
		}
	}
}
