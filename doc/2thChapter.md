
# 算法与数据结构
## 准备工作，SortTestHelper
### 生成随机数
```
import java.util.Random;

public class SortTestHelper{

	public static int[] generateRandomArray(int n, int rangeL, int rangeR) {
		int[] arr = new int[n];

		long seed = System.nanoTime();
		//**不用currentTimeMillis的原因是：当多线程调用时，由于CPU速率很快，因此currentTimeMillis很可能相等，使得随机数结果也会相等
		* nanoTime()返回最准确的可用系统计时器的当前值,以毫微秒为单位。此方法只能用于测量已过的时间,与系统或钟表时间的其他任何时间概念无关。
		*/
		Random seedRandom = new Random(seed); 
		for (int i = 0; i < n; i++) {
			//产生在[rangeL, rangeR]范围内随机数的标准做法
			arr[i] = rangeL + seedRandom.nextInt(rangeR - rangeL + 1);
		}

		return arr;
	}
}
```

**总结** 
1. 随机数种子的产生方法
2. 产生在[rangeL, rangeR]范围内随机数的标准做法

## 排序算法 
### O(n^2)的排序算法 
#### 选择排序 
思想：每次找到剩余元素中最小的，放到应该的位置上。
```
public class SelectionSort {

	private SelectionSort(){}

	public static void sort(int[] arr) {
		if (arr == null) {
			return;
		}

		int n = arr.length;
		for (int i = 0; i < n; i++) {
			int minIndex = i;
			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}

			swap(arr, i, minIndex);
		}
	}

	private static void swap(int[] arr, int i; int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}

```

升级版,可以排序实现了Comparable接口的所有类
```
public class SelectionSort {

	private SelectionSort(){}

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
}

class Student implements Comparable {

	String name;
	int score;

	@Override
	public int compareTo(Student stu) {
		return this.score - stu.score;
	}
}
```

## 完善SortTestHelper
### 排序性能方法
```
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

	public static boolean isSorted(Comparable[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i].compareTo(arr[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}
```

总结：
1. .getClass()与.class的区别
	- 最明显的区别就是.getClass()是一个对象实例的方法，只有对象实例才有这个方法，具体的类是没有的。类的Class类实例是通过.class获得的，显然，类没有.getClass()方法。
	- 出现的时期不同：Class.forName()和getClass()是在运行时加载；Class.class是在编译时加载，即.class是静态加载，.getClass()是动态加载。
	- 举个例子，Iterator it = s.iterator();得到的it的真正类型是KeyIterator，是Iterator的子类，按常理来说应该可以执行next()方法，但是值得注意的是，KeyIterator是hashmap的内部类，JAVA给的提示是cannot access a member of class java.util.HashMap$KeyIterator withmodifiers "public"
       从上面的那些例子上也能看出，除内部类外的其他类的应用上.class功能完全等于.getClass()!只是一个是用类直接获得的，一个是用实例获得的。
2. Method.invoke()，If the underlying method is static, then the specified obj argument is ignored. It may be null.

## 排序算法
### O(n^2)的排序算法 
#### 插入排序
思想：像打扑克一样，每次从后一位选择一张牌插入应该的位置
第一个版本
```
public class InsertionSort {

	public static void sort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}

		int n = arr.length;
		for (int i = 0; i < n; i++) {
			for (int j = i; j > 0 && arr[j] > arr[j - 1]; j--) {
				swap(arr, j, j - 1);
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
总结：
插入排序允许提前退出循环，因此理论上性能应该高于选择排序。

增加SortTestHelper.copyIntArray方法
```
public static int[] copyIntArray(int[] arr) {
	if (arr == null || arr.length == 0) {
		return new int[0];
	}

	int n = arr.length;
	int[] res = new int[n];
	for (int i = 0; i < n; i++) {
		res[i] = arr[i];
	}
	return res;
}
```

性能对比后发现，插入排序的效率并没有高于选择排序，问题出现在哪呢？
通过分析代码发现，每次循环都要完成一次交换，此处性能有损失，是否有方法解决呢？
插入排序第二个版本
```
public static void sort(int[] arr) {
	if (arr == null || arr.length == 0) {
		return;
	}

	int n = arr.length;

	for (int i = 0; i < arr.length; i++) {
		int key = arr[i];
		int j;
		for (j = i; j > 0 && arr[j - 1] > arr[j]; j--) {
			arr[j] = arr[j - 1];
		}
		arr[j] = key;
	}
}

```
总结：
每次内循环中只完成一次赋值，整个内循环完成后再完成一次赋值，效率提升。

##### 性能再谈
从源码分析入手发现，插入排序对于近乎有序的数组进行排序时，效率极高。为了验证，需要产生一个近乎有序的数组。
```
public static int[] generateNearlyOrderedArray(int n, int swapTimes) {
	int[] arr = new int[n];

	for (int i = 0; i < n; i++) {
		arr[i] = i;
	}

	long seed = System.nanoTime();
	Random seedRandom = new Random(seed);

	for (int i = 0; i < swapTime; i++) {
		int posx = Math.abs(seedRandom.nextInt() % n);
		int posy = Math.abs(seedRandom.nextInt() % n);

		swap(arr, posx, posy);
	}
}
```
通过效率对比，在近乎有序的排序中，插入排序的效率极高，甚至比O(nlogn)级别的排序算法效率都高。
