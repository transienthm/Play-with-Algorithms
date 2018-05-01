package com.meituan.graph.weight;

import java.lang.reflect.Array;

public class MinHeap<T extends Comparable> {
	private T[] data;// 索引从1开始的堆
	private int count;
	private Class<T> type;
	private int capacity;

	public MinHeap(int capacity, Class<T> type) {
		this.data = (T[]) Array.newInstance(type, capacity + 1);
		this.count = 0;
		this.capacity = capacity;
		this.type = type;
	}

	public int size() {
		return count;
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public void insert(T item) {
		if (this.count + 1 > this.capacity && this.capacity * 2 + 1 < Integer.MAX_VALUE) {
			this.capacity *= 2;
			T[] newData = (T[]) Array.newInstance(type, this.capacity + 1);
			System.arraycopy(data, 0, newData, 0, count + 1);
			data = newData;
		}

		data[++count] = item;//索引是从1开始的，因此应该为++count,而不是count++
		shiftUp(count);
	}

	private void shiftUp(int i) {
		while (data[i].comapreTo(data[i / 2]) < 0 && i > 1) {
			swap(data, i, i / 2);
			i /= 2;
		}
	}

	public T extractMin() {
		if (count <= 0) {
			return null;
		}
		T res = data[1];
		swap(data, 1, count--);
		shiftDown(1);

		return res;
	}

	private void shiftDown(int i) {
		while (2 * i < count) {
			int j = 2 * i;
			if (j + 1 <= count && data[j + 1].compareTo(data[j]) < 0) {
				j++;
			}
			//data[j] 是 data[j * 2] 和 data[j * 2 + 1]中最小的

			if (data[i].compareTo(data[j]) < 0) {
				break;
			}
			swap(data, i, j);
			i = j;
		}
	}

	private void swap(T[] arr, int i, int j) {
		T t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}
}