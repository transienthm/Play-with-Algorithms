package com.meituan.search;

import java.util.Random;

public class BinarySearch {
	public static int binarySearch(Comparable[] arr, Comparable target) {

		if (arr == null || arr.length == 0) {
			return -1;
		}
		int n = arr.length;
		int l = 0, r = n - 1;
		while (l < r) {
			int mid = l + (r - l) / 2;
			if (arr[mid].compareTo(target) == 0) {
				return mid;
			} else if (arr[mid].compareTo(target) > 0) {
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}

		return -1;
	}

	public static int floor(Comparable[] arr, Comparable target) {
		if (arr == null || arr.length == 0) {
			return -1;
		}

		if (arr[0].compareTo(target) > 0) {
			return -1;
		}

		int n = arr.length;
		int l = 0, r = n - 1;
		int floor = 0;
		boolean found = false;
		while (l < r) {
			floor = l + (r - l) / 2;
			if (arr[floor].compareTo(target) == 0) {
				found = true;
				break;
			} else if (arr[floor].compareTo(target) > 0) {
				r = floor - 1;
			} else {
				l = floor + 1;
			}
		}
		if (found) {
			while (floor >= 1 && arr[floor - 1].compareTo(arr[floor]) == 0) {
				floor--;
			}
			return floor;
		} else {
			if (l == r) {
				if (arr[l].compareTo(target) < 0) {
					return l;
				} else {
					return (l - 1) >= 0 ? l - 1 : 0;
				}
			} else {
				return Math.min(l, r);
			}
		}
	}

	public static void main(String[] args) {
		long seed = System.nanoTime();
		Random random = new Random();
		int n = 10;
		Integer[] arr = new Integer[n];
		for (int i = 0; i < n; i++) {
			arr[i] = Math.abs(random.nextInt(n));
		}

		insertionSort(arr);
		for (int i = 0; i < n; i++) {
			System.out.print(arr[i] + " ");
		}
		int floor = BinarySearch.floor(arr, 5);
		System.out.println();
		System.out.println("floor = " + floor);
	}

	public static void insertionSort(Comparable[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}

		int n = arr.length;
		for (int i = 0; i < n; i++) {
			Comparable key = arr[i];
			int j;
			for (j = i; j > 0 && arr[j - 1].compareTo(arr[j]) > 0; j--) {
				arr[j] = arr[j - 1];
				j--;
			}
		}
	}
}