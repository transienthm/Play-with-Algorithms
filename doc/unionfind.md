# 连接问题Connectivity problem
![default](https://user-images.githubusercontent.com/16509581/38966435-dccf281c-43b4-11e8-83d0-909b755b18ac.png)
任意两点是否连接在一起？
更抽象一点为
+ 网络中节点间的连接状态
	- 网络是个抽象的概念：用户之间形成的网络(facebook中两个人是否认识)
+ 数学中的集合类实现

与路径问题的区别 
连接问题比路径问题要回答的问题少

# 并查集的实现
对于一组数据，主要支持两个动作
 - union(p, q)
 - find(p)
用来回答一个问题
 - isConnected(p, q)

## 并查集的基本数据表示 
quickFind版本，查找的速度非常快，但是并的速度并不快
```
public class UnionFind {
	private int[] id;
	private int count;

	public UnionFind(int n) {
		count = n;
		id = new int[n];
		for (int i = 0; i < n; i++) {
			id[i] = i;
		}
	}

	public int find(int p) {
		assert (p >= 0 && p < count);
		return id[p];
	}

	public void union(int p, int q) {
		int pId = id[p];
		int qId = id[q];

		if (pId == qId) {
			return;
		}

		for (int i = 0; i < count; i++) {
			if (id[i] == pId) {
				id[i] = qId;
			}
		}
	}
}
```
quickUnion版本
思想：将每一个元素，看做是一个节点，包含一个指针，指向自己的父亲，表示两者是连接在一起的。根结点指向自己。
查的效率与树的高度有关，并的效率极大优化
```
public class QuickUnion {
	private int[] parent;
	private int count;

	public QuickUnion(int count) {
		parent = new int[count];
		this.count = count;
		for (int i = 0; i < count; i++) {
			parent[i] = i;
		}
	}

	public int find(int p) {
		assert (p >= 0 && p < count);

		while (p != parent[p]) {
			p = parent[p];
		}
		return p;
	}

	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);

		if (pRoot == qRoot) {
			return;
		}

		parent[pRoot] = qRoot;
	}
}
```

# 并查集的优化
## 初步优化
因为并查集的性能与树的高度有关，因此一个优化方向就是在并的过程中移动更小高度的树，代码如下
```
public class BetterUF {
	private int[] parent;
	private int count;
	private int[] size;

	public QuickUnion(int count) {
		parent = new int[count];
		size = new int[count];
		this.count = count;
		for (int i = 0; i < count; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}

	public int find(int p) {
		assert (p >= 0 && p < count);

		while (p != parent[p]) {
			p = parent[p];
		}
		return p;
	}

	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);

		if (pRoot == qRoot) {
			return;
		}
		if (size[pRoot] < size[qRoot]) {
			parent[pRoot] = qRoot;
			size[qRoot] += size[pRoot];
		} else {
			parent[qRoot] = pRoot;
			size[pRoot] += size[qRoot];
		}
	}
}
```

## 基于rank的优化
单纯以节点数量进行优化依旧不够准确，更好的方式是基于树的高度进行优化，即基于rank的优化
```
public class RankUF {
	private int[] parent;
	private int count;
	private int[] rank;

	public QuickUnion(int count) {
		parent = new int[count];
		rank = new int[count];
		this.count = count;
		for (int i = 0; i < count; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
	}

	public int find(int p) {
		assert (p >= 0 && p < count);

		while (p != parent[p]) {
			p = parent[p];
		}
		return p;
	}

	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);

		if (pRoot == qRoot) {
			return;
		}
		if (rank[pRoot] < rank[qRoot]) {
			parent[pRoot] = qRoot;
		} else if (rank[qRoot] < rank[pRoot]) {
			parent[qRoot] = pRoot;
		} else {
			parent[pRoot] = qRoot;
			rank[qRoot] += 1;
		}
	}
}
```
## 路径的压缩
![default](https://user-images.githubusercontent.com/16509581/38976186-0d3f7e88-43e3-11e8-818f-e77910cb60d9.png)

修改代码
```
	public int find(int p) {
		assert (p >= 0 && p < count);

		while (p != parent[p]) {
			parent[p] = parent[parent[p]];//增加一行
			p = parent[p];
		}
		return p;
	}
```

第二种路径压缩 ，压缩到底！
```
public int find(int p) {
	assert(p >= 0 && p < count);
	if (p != parent[p]) {
		parent[p] = find(parent[p]);
	}
	return parent[p];
}
```
经过路径压缩后，并查集的操作，时间复杂度近乎是O(1)的