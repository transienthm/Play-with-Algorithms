package com.meituan.sort;

public class SelectionSort1 implements Sort{

	private SelectionSort1(){}

	public static void sort(Comparable[] arr) {
		if (arr == null) {
			return;
		}
		int n = arr.length;
		for (int i = 0; i < n; i++) {

			int minIndex = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[j].compareTo(arr[minIndex]) < 0) {
					minIndex = j;
				}
			}
			swap(arr, i, minIndex);
		}
	}

	public static void swap(Object[] arr, int i, int j) {
		Object temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		Student a = new Student("A", 70);
		Student b = new Student("B", 30);
		Student c = new Student("C", 40);

		Student[] stus = new Student[]{a, b, c};

		SelectionSort1.sort(stus);

		SortTestHelper.testSort("com.meituan.SelectionSort1", stus);

		for (Student stu : stus) {
			Student.print(stu);
		}
	}
}

class Student implements Comparable<Student> {

	String name;
	int score;

	public Student(){

	}

	public Student(String name, int score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public int compareTo(Student stu) {
		return this.score - stu.score;
	}

	public static void print(Student stu){
		String  str = "name: " + stu.name + ", score: " + stu.score;
		System.out.println(str);
	}
}