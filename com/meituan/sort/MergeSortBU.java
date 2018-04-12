/***
*自底向上的归并排序 
*/
package com.meituan.sort;

import java.util.Arrays;

public class MergeSortBU {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return ;
		}

		int n = arr.length;

		for (int sz = 1; sz <= n; sz += sz) {
			for (int i = 0; i + sz < n; i += sz + sz) {
				//对arr[i...i+sz-1] 和 arr[i+sz...i+sz+sz-1]进行归并
				//注意数组越界问题
				//1. 保证[i+sz,i+sz+sz-1]存在，则，i+sz < n
				//2. 保证i+sz+sz-1不越界
				//System.out.println("排序中：sz = " + sz +", i = " + i);
				//System.out.println("排序区间为: [" + i + ", " + (i + sz - 1) + 
				//	"], 以及[" + (i + sz) + ", " + (i + sz + sz -1) + "].");
				if (arr[i + sz - 1] > arr[i + sz]) {
					merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1));
				}
			}
		}
	}

	public static void merge(int[] arr, int low, int mid,int high) {
		if (arr == null || arr.length == 0) {
			return;
		}

		int n = arr.length;

		int[] aux = Arrays.copyOfRange(arr, 0, n - 1);
		int i = low, j = mid + 1;

		for (int k = low; k <= high; k++) {
			if (i > mid) {
				arr[k] = aux[j - low];
				j++;
			} else if (j > high) {
				arr[k] = aux[i - low];
				i++;
			} else if (arr[i] < arr[j]) {
				arr[k] = aux[i - low];
			} else {
				arr[k] = aux[j - low];
			}
		}
	}
}
