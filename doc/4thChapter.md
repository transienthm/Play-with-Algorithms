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

public class MaxHeap<Item> {
	private Item[] data;
	private int count;

	public MaxHeap(int capacity) {
		//泛型数组的经典处理方式 
		data = (Item[]) new Object[capacity + 1];//索引0不存储数据
		count = 0;
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

#### Shift Up操作
