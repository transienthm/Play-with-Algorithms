package com.meituan.sort;

import java.util.Random;
import java.lang.reflect.Method;
import java.lang.Class;

public class SortTestHelper {

	public static int[] generateRandomArray(int n, int rangeL, int rangeR) {

		int[] arr = new int[n];

		if (rangeL > rangeR) {
			return arr;
		}

		long seed = System.nanoTime();
		Random seedRandom = new Random(seed);
		for (int i = 0; i < 10; i++) {
			arr[i] = rangeL + seedRandom.nextInt(rangeR - rangeL + 1);
		
		}

		return arr;
	}

	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}

		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void testSort(String sortClassName, Comparable[] arr) {

		try{
			Class clazz = Class.forName(sortClassName);
			Method sortMethod = clazz.getMethod("sort", new Class[]{Comparable[].class});

			Object[] params = new Object[]{arr};

			long startTime = System.currentTimeMillis();
			sortMethod.invoke(null, params);
			long endTime = System.currentTimeMillis();

			assert isSorted(arr); 

			System.out.println(clazz.getSimpleName() + ": " + (endTime - startTime) + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testSort(String sortClassName, int[] arr) {

		try{
			Class clazz = Class.forName(sortClassName);
			Method sortMethod = clazz.getMethod("sort", new Class[]{int[].class});

			Object[] params = new Object[]{arr};

			long startTime = System.currentTimeMillis();
			sortMethod.invoke(null, params);
			long endTime = System.currentTimeMillis();

			assert isSorted(arr); 

			System.out.println(clazz.getSimpleName() + ": " + (endTime - startTime) + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isSorted(Comparable[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i].compareTo(arr[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean isSorted(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1]) {
				return false;
			}
		}
		return true;
	}

	public static int[] copyIntArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}

		int n = arr.length;
		int[] res = new int[n];
		System.arraycopy(arr, 0, res, 0, n);
		return res;

	}

	public static int[] generateNearlyOrderedArray(int n, int swapTimes) {
		int[] res = new int[n];
		for (int i = 0; i < n; i++) {
			res[i] = i;
		}

		long seed = System.nanoTime();
		Random seedRandom = new Random(seed);

		for (int i = 0; i < swapTimes; i++) {
			int posx = Math.abs(seedRandom.nextInt() % n);
			int posy = Math.abs(seedRandom.nextInt() % n);

			swap(res, posx, posy);
		}

		return res;
	}

	private static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	public static void main(String[] args) {
		int[] arr = SortTestHelper.generateRandomArray(10, 5, 20);
		for (int i = 0; i < 10; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}