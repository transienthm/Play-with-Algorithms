package com.meituan.sort;

public class InsertionSort implements Sort {
/*	public static void sort(int[] arr) {
		if (arr == null && arr.length == 0) {
			return;
		}

		int n = arr.length;

		for (int i = 1; i < n; i++) {
			//插入排序允许提前退出循环
			for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) {
				swap(arr, j, j - 1);
			}
		}
	}*/

	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}

		int n = arr.length;

		for (int i = 0; i < arr.length; i++) {
			int key = arr[i];
			int j;
			for (j = i; j > 0 && arr[j - 1] > arr[j]; j--) {
				arr[j] = arr[j - 1];
			}
			arr[j] = key;
		}
	}
	
	private static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	public static void main(String[] args) {
		int[] arr = SortTestHelper.generateRandomArray(10, 0, 20);
		System.out.println("排序前");
		SortTestHelper.printArray(arr);

		InsertionSort.sort(arr);
		System.out.println("排序后");
		SortTestHelper.printArray(arr);	
	}
}