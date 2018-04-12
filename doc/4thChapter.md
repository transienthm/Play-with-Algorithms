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

#### 创始一个最大堆
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

#### Shift Up操作
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