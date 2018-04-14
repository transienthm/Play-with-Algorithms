package com.meituan.sort;


public class TestSort  {
	public static void main(String[] args) {
/*		int[] arr1 = SortTestHelper.generateNearlyOrderedArray(10000, 10);
		int[] arr2 = SortTestHelper.copyIntArray(arr1);

		SortTestHelper.testSort("com.meituan.sort.MergeSort", arr1);
		SortTestHelper.testSort("com.meituan.sort.InsertionSort", arr2);

		System.out.println("**********************************************");


		int[] arr3 = SortTestHelper.generateRandomArray(10000, 0, 20000000);
		int[] arr4 = SortTestHelper.copyIntArray(arr3);

		SortTestHelper.testSort("com.meituan.sort.MergeSort", arr3);
		SortTestHelper.testSort("com.meituan.sort.InsertionSort", arr4);
*/
		int[] arr1 = SortTestHelper.generateRandomArray(1000000, 0, 10);
		int[] arr2 = SortTestHelper.generateNearlyOrderedArray(10000, 2);

		int[] arr3 = SortTestHelper.copyIntArray(arr2);
		int[] arr4 = SortTestHelper.copyIntArray(arr1);

//		SortTestHelper.testSort("com.meituan.sort.QuickSort3Ways", arr1);
//		SortTestHelper.testSort("com.meituan.sort.QuickSort", arr4);
		SortTestHelper.testSort("com.meituan.sort.HeapSort", arr1);
	}	
}
