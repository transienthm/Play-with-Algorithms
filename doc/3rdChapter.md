# O(nlog)级别的排序算法 
## 归并排序
归并排序（Merge）是将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列。

归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。 将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。归并排序算法稳定，数组需要O(n)的额外空间，链表需要O(log(n))的额外空间，时间复杂度为O(nlog(n))，算法不是自适应的，不需要对数据的随机读取。

实现原理：
1. 申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列；

2. 设定两个指针变量，最初位置分别指向两个已经排好序的数组序列的起始位置；

3. 比较两个指针所指向的元素，选择相对小的元素放入到申请的合并空间里，并移动此指针到下一位置；

4. 继续重复步骤3直到某一指针达到序列尾；

5. 当一个指针到达一个序列尾时，将另一序列剩下的所有元素直接复制到合并序列尾。

### 第一个版本
```
public class MergeSort {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return ;
		}

		int n = arr.length;
		mergeSort(arr, 0, n - 1);
	}

	public static void mergeSort(int[] arr, int low, int high) {
		if (low >= high) {
			return ;
		}

		int mid = low + (high - low) / 2;

		mergeSort(arr, low, mid);
		mergeSort(arr, mid + 1, high);
		merge(arr, low, mid, high);
	}

	public static void merge(int[] arr, int low, int mid, int high) {
		if (arr == null || arr.length == 0) {
			return ;
		}

		int n = arr.length;
		int[] aux = Arrays.copyOfRange(arr, 0, n + 1);

		int i = low, j = mid + 1;
		for (int k = low; k <= high; k++) {

			if (i > mid) {
				//[low, mid]已经处理完了
				arr[k] = aux[j - low];
				j++;
			} else if (j > high) {
				//[mid + 1, high]已经处理完了 
				arr[k] = aux[i - low];
			} else if (aux[i - low] < aux[j - low]) {
				arr[k] = arr[i - low];
				i++;
			} else {
				arr[k] = arr[j - low];
				j++;
			}
		}
	}
}
```

性能分析：
在近乎有序的数组排序时，效率较低

### 优化后的版本
方向 ：
1. 何时不需要归并？
当arr[mid] <= arr[mid + 1]时，事实上是不需要归并操作的，因此可以增加判断，arr[mid] > arr[mid + 1]时才归并，注意此时并不能将归并排序的时间复杂度降至O(n)级别(而插入排序可以做到)。
2. 是否有必要递归到底？
可以在数组长度小于某个阈值后，使用插入排序。
```
	public static void mergeSort(int[] arr, int low, int high) {
		//优化点
		if (high - low <= 15) {
			insertionSort(arr, low, high);
			return ;
		}

		int mid = low + (high - low) / 2;

		mergeSort(arr, low, mid);
		mergeSort(arr, mid + 1, high);
		//优化点
		if (arr[mid] > arr[mid + 1]) {
			merge(arr, low, mid, high);
		}	
	}
```

### 自底向上的归并 排序
```
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

```

问题解答：
1. 在sz double之前，是否数组中有一部分元素无法被处理到？
在内层循环中解决了，sz += sz之前，i会对[i, i+sz-1], [i+sz,Math.min(i+sz+sz-1, n-1]这两部分进行处理,因此不会有元素不被处理到的情形
2. 归并时如何解决问题区间a[i, i+sz-1]与区间b[i+sz,Math.min(i+sz+sz-1, n-1]两者长度不一致，是否可以归并？
可以的，每次归并时，会对比两个区间中数据的大小，直到有个区间先处理完为止，与区间长度无关。

##快速排序
快速排序是C.R.A.Hoare于1962年提出的一种划分交换排序。它采用了一种分治的策略，通常称其为分治法(Divide-and-ConquerMethod)。

该方法的基本思想是：

1. 先从数列中取出一个数作为基准数。

2. 分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。

3. 再对左右区间重复第二步，直到各区间只有一个数。

### 第一个版本
```
public static void sort(int[] arr) {
	if (arr == null || arr.length == 0) {
		return;
	}
	int n = arr.length;
	quickSort(arr, 0, n - 1);
}

public static void quickSort(int[] arr, int low, int high) {
	if (low >= high) {
		return;
	}

	int p = partition(arr, low, high);
	quickSort(arr, low, p - 1);
	quickSort(arr, p + 1, high);
}

public static int partition(int[] arr, int low, int high) {
	int v = arr[low];

	//arr[low + 1....j] < v ; arr[j + 1...i] > v
	int j = low;
	for (int i = low + 1; i < high; i++) {
		if (arr[i] < v) {
			swap(arr, j + 1, i);
			j++;
		}
	}
	swap(arr, low, j);

	return j;
}

//最后一次展示swap方法
private static void swap(int[] arr, int i, int j) {
	int t = arr[i];
	arr[i] = arr[j];
	arr[j] = t;
}
```

测试时，如果数据量较大，会报java.lang.StackOverflowError，因为递归太多，栈内存不够用了。

### 优化版本
方向：
1. 数据量较小后转化为插入排序
2. 在近乎有序的排序中，快排的递归树的平衡度比归并的递归树的平衡度要差很多，且树的深度可能不是logn，最坏情况是待排序数组完全有序，此时快排的时间复杂度退化为O(n^2)，因此在partition过程中，选择pivot值时的策略为随机选择是很好的解决方案。

```
public static void quickSort(int[] arr, int low, int high) {
	if (high - low < 16) {
		insertSort(arr, low, high);
	}

	int p = partition(arr, low, high);
	quickSort(arr, low, p - 1);
	quickSort(arr, p + 1, high);
}

public static int partition(int[] arr, int low, int high) {
	long seed = System.nanoTime();
	Random random = new Random(seed);

	int randomPos = random.nextInt(high - low + 1) + low;
	swap(arr, low, randomPos);

	int v = arr[low];

	int j = low;
	for (int i = low + 1; i < high; i++) {
		if (arr[i] < v) {
			swap(arr, j + 1, i);
			j++;
		}
	}
	swap(arr, low, j);

	return j;
}
```

3. 如果数组中包含大量相同元素，上述快排依旧会退化成O(n^2)。可以修改patition过程中，根据pivot值分组的处理算法。
```
public static int partition(int[] arr, int low, int high) {
	long seed = System.nanoTime();
	Random random = new Random(seed);

	int randomPos = random.nextInt(high - low + 1) + low;
	swap(arr, low, randomPos);
	int v = arr[low];

	int i = low + 1, j = high;
	while (true) {
		while (i < high && arr[i] < v) {
			i++;
		}

		while (j > low && arr[j] > v) {
			j--;
		}

		if (i > j) {
			break;
		}

		swap(arr, i, j);
		i++;
		j--;
	}

	return j;
}
```
我写得最6的快排
```
public static int partition(int[] arr, int low, int high) {
	long seed = System.nanoTime();
	Random random = new Random(seed);

	int randomPos = random.nextInt(high - low + 1) + low;
	swap(arr, low, randomPos);

	int v = arr[low];

	while (low < high) {
		while (low < high && arr[high] > v) {
			high--;
		}
		arr[low] = arr[high];
		while (low < high && arr[high] < v) {
			low++;
		}
		arr[high] = arr[low];
	}

	arr[low] = v;
	return low;
}
```

### 三路快排
如果存在大量相同元素，三路快排性能更佳！

三路快排中途发展图示
![default](https://user-images.githubusercontent.com/16509581/38616207-c0d36302-3dc5-11e8-8644-0e1c2a21bfac.png)

三路快排结束时图示
![default](https://user-images.githubusercontent.com/16509581/38607006-3083df9a-3daa-11e8-9edf-866d84d9782b.png)

```
import java.util.Random;

public class QuickSort3Ways {
	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}

		int n = arr.length;
		quickSort3Ways(arr, 0, n - 1);
	}

	public static void quickSort3Ways(int[] arr, int low, int high) {
		if (low >= high) {
			return;
		}

		long seed = System.nanoTime();
		Random random = new Random(seed);

		int pos = random.nextInt(high - low + 1) + low;
		swap(arr, low, pos);

		int v = arr[low];

		int lt = low; //arr[low+1...lt] < v 初始区间为空
		int gt = high + 1;// arr[gt...high] > v 初始区间为空
		int i = low + 1; // arr[lt+1...i] = v 初始区间为空

		while (i < gt) {
			if (arr[i] > v) {
				swap(arr, i, gt - 1);
				gt--;
				i++;
			} else if (arr[i] < v) {
				swap(arr, lt + 1, i);
				lt++;
			} else {
				i++;
			}
		}

		swap(arr, lt, low);
		quickSort3Ways(arr, low, lt - 1);
		quickSort3Ways(arr, gt, high);
	}
} 
```

核心在于三个区间的边界确定，结合上述两幅图示好好理解。

思考题：
1. 求一组数的逆序对
2. 用O(n)级别的算法求解第n大的元素