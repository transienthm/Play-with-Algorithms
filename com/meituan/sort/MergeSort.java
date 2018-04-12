package com.meituan.sort;

import java.util.Arrays;

public class MergeSort {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return ;
		}

		int n = arr.length;
		mergeSort(arr, 0, n - 1);
	}

	public static void mergeSort(int[] arr, int low, int high) {
		if (low >= high) {
			return ;
		}

		int mid = low + (high - low) / 2;

		mergeSort(arr, low, mid);
		mergeSort(arr, mid + 1, high);
		merge(arr, low, mid, high);
	}

	public static void merge(int[] arr, int low, int mid, int high) {
		if (arr == null || arr.length == 0) {
			return ;
		}

		int n = arr.length;
		int[] aux = Arrays.copyOfRange(arr, 0, n + 1);

		int i = low, j = mid + 1;
		for (int k = low; k <= high; k++) {

			if (i > mid) {
				//[low, mid]已经处理完了
				arr[k] = aux[j - low];
				j++;
			} else if (j > high) {
				//[mid + 1, high]已经处理完了 
				arr[k] = aux[i - low];
			} else if (aux[i - low] < aux[j - low]) {
				arr[k] = arr[i - low];
				i++;
			} else {
				arr[k] = arr[j - low];
				j++;
			}
		}
	}
}