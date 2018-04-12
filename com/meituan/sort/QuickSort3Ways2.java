package com.meituan.sort;

import java.util.Random;

public class QuickSort3Ways2 {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}

		int n = arr.length;
		quickSort3Ways(arr, 0, n - 1);
	}

	public static void quickSort3Ways(int[] arr, int low, int high) {
		if (low >= high) {
			return;
		}

		long seed = System.nanoTime();
		Random random = new Random(seed);

		int pos = random.nextInt(high - low + 1) + low;
		swap(arr, low, pos);

		int v = arr[low];

		int lt = low; //arr[low+1...lt] < v 初始区间为空
		int gt = high + 1; // arr[gt...high] > v 初始区间为空
		int i = low + 1; // arr[lt+1...i] = v 初始区间为空

		while (i < gt) {
			if (arr[i] > v) {
				swap(arr, i, gt - 1);
				gt--;

			} else if (arr[i] < v) {
				swap(arr, lt + 1, i);
				lt++;
				i++;
			} else {
				i++;
			}
		}

		swap(arr, lt, low);
		quickSort3Ways(arr, low, lt - 1);
		quickSort3Ways(arr, gt, high);
	}

	private static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}
} 