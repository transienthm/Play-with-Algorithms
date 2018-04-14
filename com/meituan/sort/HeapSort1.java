package com.meituan.sort;

public class HeapSort1 {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		int n = arr.length;

		MaxHeap<Integer> maxHeap = new MaxHeap(16, Integer.class);
		for (int i = 0; i < n; i++) {
			maxHeap.insert(arr[i]);
		}

		for (int i = n - 1; i >= 0; i--) {
			arr[i] = maxHeap.extractMax();
		}
	}
}