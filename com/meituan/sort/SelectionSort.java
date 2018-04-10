package com.meituan.sort;

public class SelectionSort implements Sort {

	public static void sort(int[] arr) {

		for (int i = 0; i < arr.length; i++) {
			// 寻找[i, n)区间里的最小值
			int minIndex = i; 
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] <arr[minIndex]) {
					minIndex = j;
				}
			}
			swap(arr, i, minIndex);
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		int[] arr = SortTestHelper.generateRandomArray(10, 0, 20);
		System.out.println("排序前：");
		SortTestHelper.printArray(arr);

		SelectionSort.sort(arr);
		System.out.println("排序后：");
		SortTestHelper.printArray(arr);
	}
}