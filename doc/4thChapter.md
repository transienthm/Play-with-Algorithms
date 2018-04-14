# 堆排序
## 1. 优先队列
普通队列：先进先出；后进后出
优先队列：出队顺序和入队顺序无关，而与优先级有关

在N个元素中选出前M个元素，例如在1000000个元素中选出前100名这类问题，排序的时间复杂度为O(nlogn)，而优先队列为O(Mlogn)。
| | 入队 | 出队 | 
|-- | -- | -- |
| 普通数组 | O(1) | O(n) |
| 顺序数组 | O(n) | O(1) |
| 堆 | O(lgn) | O(lgn) |

## 2. 堆的基本实现 
经典实现 ： 二叉堆
特点：
1. 堆中某个节点的值总是不大于父节点的值(最大堆)
2. 堆总是一棵完全二叉树

### 2.1 用数组存储二叉堆
结点的性质
parent(i) = i / 2
left child (i) = 2 * i
right child (i) = 2 * i + 1

### 2.2 创始一个最大堆
```
package com.meituan.sort;

import java.lang.reflect.Array;

public class MaxHeap<Item, T> {
	private Item[] data;
	private int count;
	private Class<T> type;

	public MaxHeap(int capacity, Class<T> type) {
		//泛型数组的经典处理方式 
		this.data = (Item[]) Array.newInstance(type, capacity + 1);//索引0不存储数据
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

	public static void main(String[] args) {
		MaxHeap<Integer> maxHeap = new MaxHeap(100);
		System.out.println(maxHeap.size());
	}
}
```

总结：
创建泛型数组：参考https://segmentfault.com/a/1190000005179147

### 2.3 Shift Up操作
向最大堆中存入数据，需要调整数据到正确的位置，从而保证该堆依旧是最大堆，因此需要shiftUp操作。
```
package com.meituan.sort;

import java.lang.reflect.Array;

public class MaxHeap<Item extends Comparable, T> {
	private Item[] data;
	private int count;
	private Class<T> type;
	private int capacity;

	public MaxHeap(int capacity, Class<T> type) {
		//泛型数组的经典处理方式 
		this.data = (Item[]) Array.newInstance(type, capacity + 1);//索引0不存储数据
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

	public void insert(Item item) {
		//首先要保证数组不越界
		if (this.count + 1 >= this.capacity) {
			this.capacity = this.capacity * 2 + 1;
			Item[] newData = (Item[]) Array.newInstance(type, capacity);
			System.arraycopy(data, 0, newData, 0, count + 1);
			data = newData;
		}

		data[++count] = item;
		shiftUp(count);
	}

	private void shiftUp(int k) {
		while (k > 1 && data[k / 2].compareTo(data[k]) < 0) {
			swap(data, k / 2, k);
			k /= 2;
		}
	}

	private void swap(Item[] arr, int i, int j) {
		Item t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	public static void main(String[] args) {
		MaxHeap<Integer> maxHeap = new MaxHeap(100);
		System.out.println(maxHeap.size());
	}
}
```

总结：
1. 数组扩容机制
	```
	补充ArrayList实现
	```
2. while循环中，k > 1边界条件的发现

### 2.4 shiftDown操作
从堆中取出一个元素，只能取出堆顶的元素，然后将堆最后的元素放至堆顶（保证是完全二叉树），然后做shiftDown操作（保证是最大堆）
```
	public Item extractMax() {
		if (count <= 0) {
			return null;
		}

		Item res = data[1];
		swap(data, 1, count--);
		shiftDown(1);

		return res;
	}
	private void shiftDown(int k) {
		while (k <= (count - 1) / 2 && data[k].compareTo(max(data[k * 2], data[k * 2 + 1])) < 0) {
			if (data[k * 2].compareTo(data[k * 2 + 1]) > 0) {
				swap(data, k, k * 2);
				k = k * 2;
			} else {
				swap(data, k, k * 2 + 1);
				k = k * 2 + 1;
			}
		}
	}
	private Item max(Item a, Item b) {
		if (a == null) {
			return b;
		}

		if (b == null) {
			return a;
		}

		if (a.compareTo(b) > 0) {
			return a;
		} else {
			return b;
		}
	}
```

## 3. 堆排序
拥有一个最大堆之后，对数组的排序就变成了元素入堆，然后出堆的过程
### 第一个版本
```
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
```

### 优化版本
将数组构建成堆的过程Heapify
完全二叉树的性质：
第一个不是叶子节点的索引位置为n/2，其中n为堆中节点总数
Heapify过程：
从n/2这个位置(因为叶子结点已经是最大堆了)开始至树顶位置1依次递减，对每个节点做shiftDown操作
```
//给MaxHeap添加一个构造方法
public MaxHeap(int[] arr, Class<Item> type) {
	int n = arr.length;
	data = (Item[]) Array.newInstance(type, n + 1);
	capacity = n;
	for (int i = 0; i <= n; i++) {
		data[i + 1] = arr[i];
	}
	count = n;

	for (int i = n / 2; i >= 0; i--) {
		shiftDown(i);
	}
}
```

将n个元素逐个插入到一个空堆中，算法复杂度是O(nlogn)，heapify的过程，算法复杂度为O(n)

## 4. 原地堆排序
一个数组就可以看成一个堆，此时的性质为
parent(i) = (i - 1) / 2
left child(i) = 2 * i + 1
right child(i) = 2 * i + 2
最后一个非叶子节点的索引(count - 1) / 2
```
public class HeapSort {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		int n = arr.length;
		// 1. heapify
		for (int i  = (n - 1) / 2; i >= 0; i--) {
			shiftDown(arr, n, i);
		}
		// 2. 堆排序
		for (int i = n - 1; i > 0; i--) {
			swap(arr, 0, i);
			shiftDown(arr, i, 0);
		}
	}

	private static void shiftDown(int[] arr, int n, int k) {
		while (k <= (n - 3) / 2 
			&& arr[k] < Math.max(arr[2 * k + 1], arr[2 * k + 2])) {
				if (arr[2 * k + 1] > arr[2 * k + 2]) {
					swap(arr, k, 2 * k + 1);
					k = 2 * k + 1;
				} else {
					swap(arr, k, 2 * k + 2);
					k = 2 * k + 2;
				}
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}
}
```