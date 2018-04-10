package com.meituan.sort;


public class TestSort  {
	public static void main(String[] args) {
		int[] arr1 = SortTestHelper.generateNearlyOrderedArray(100000, 10);
		int[] arr2 = SortTestHelper.copyIntArray(arr1);

		SortTestHelper.testSort("com.meituan.sort.SelectionSort", arr1);
		SortTestHelper.testSort("com.meituan.sort.InsertionSort", arr2);

		int[] arr3 = SortTestHelper.generateNearlyOrderedArray(100, 1);
		SortTestHelper.printArray(arr3);
	}	
}
