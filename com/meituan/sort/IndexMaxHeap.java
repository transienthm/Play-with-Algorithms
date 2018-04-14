package com.meituan.sort;

public class IndexMaxHeap<E> {
	private int count;
	private int capacity;
	private int[] indexes;
	private E[] data;
	private Class<E> type;

	public IndexMaxHeap(int capacity, Class<E> type) {
		this.count = 0;
		this.capacity = capacity;
		this.indexes = new int[capacity + 1];
		this.data = (E[]) Array.newInstance(type, capacity + 1);
	}

	public int size() {
		return count;
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public void insert(int i, T item) {
		assert(count + 1 <= capacity);
		assert( i + 1 >= 1 && i + 1 <= capacity);

		i += 1;
		data[i] = item;
		indexes[count++] = i;
		shiftUp(count);
	}

	private void shiftUp(int k) {
		while (k > 1 && data[indexes[k / 2]] < data[indexes[k]]) {
			swap(indexes, k / 2, k);
			k /= 2;
		}
	}

	public E extractMax() {
		assert(count > 0);

		E item = data[indexes[1]];

		swap(indexes, 1, count);
		count--;
		shiftDown(1);

		return item;
	}

	private void shiftDown(int k) {
		while (k <= (count - 1) / 2 && data[indexes[k]].compareTo(max(data[indexes[k * 2]], data[indexes[k * 2 + 1]])) < 0) {
			if (data[indexes[k * 2]].compareTo(data[indexes[k * 2 + 1]]) > 0) {
				swap(indexes, k, k * 2);
				k = k * 2;
			} else {
				swap(indexes, k, k * 2 + 1);
				k = k * 2 + 1;
			}
		}
	}

	E getItem(int i) {
		return data[i + 1];
	}

	void change(int i, E newItem) {
		i += 1;
		data[i] = newItem;
		
	}

	private void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

}