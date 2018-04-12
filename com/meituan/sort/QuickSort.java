package com.meituan.sort;

import java.util.Random;

public class QuickSort {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}

		quickSort(arr, 0, arr.length - 1);
	}	

	private static void quickSort(int[] arr, int low, int high) {
		if (low >= high) {
			return;
		}

		int p = partition(arr, low, high);
		quickSort(arr, low, p - 1);
		quickSort(arr, p + 1, high);
	}	

	private static int partition(int[] arr, int low, int high) {
		long seed = System.nanoTime();
		Random random = new Random(seed);

		int randomPos = random.nextInt(high - low + 1) + low;
		swap(arr, low, randomPos);

		int pivotVal = arr[low];

		while (low < high) {
			while (low < high && arr[high] >= pivotVal) {
				high--;
			}
			arr[low] = arr[high];
			while (low < high && arr[low] <= pivotVal) {
			 	low++;
			}
			arr[high] = arr[low]; 
		}

		arr[low] = pivotVal;
		return low;
	}

	private static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}
}