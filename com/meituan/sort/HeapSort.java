package com.meituan.sort;

public class HeapSort {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		int n = arr.length;
		// 1. heapify
		for (int i  = (n - 1) / 2; i >= 0; i--) {
			shiftDown(arr, n, i);
		}
		// 2. 堆排序
		for (int i = n - 1; i > 0; i--) {
			swap(arr, 0, i);
			shiftDown(arr, i, 0);
		}
	}

	private static void shiftDown(int[] arr, int n, int k) {
		while (k <= (n - 3) / 2 
			&& arr[k] < Math.max(arr[2 * k + 1], arr[2 * k + 2])) {
				if (arr[2 * k + 1] > arr[2 * k + 2]) {
					swap(arr, k, 2 * k + 1);
					k = 2 * k + 1;
				} else {
					swap(arr, k, 2 * k + 2);
					k = 2 * k + 2;
				}
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}
}