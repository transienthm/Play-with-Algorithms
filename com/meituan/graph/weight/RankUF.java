package com.meituan.graph.weight;


public class RankUF {
	private int[] parent;//用这样一个数组来表示元素所属的集合，表示元素指向的根结点
	private int count;
	private int[] rank;//rank[i]表示以i为根的集合所表示的树的层数

	public RankUF(int count) {
		this.count = count;
		this.parent = new int[count];
		this.rank = new int[count];

		for (int i = 0; i< count; i++) {
			parent[i] = i;
			rank[i] = i;
		}
	}

	public int find(int p) {
		if (p < 0 || p >= count) {
			return -1;
		}

        // 不断去查询自己的父亲节点, 直到到达根节点
        // 根节点的特点: parent[p] == p
		while (p != parent[p]) {
			p = parent[p];
		}
		return p;
	}

	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}

	/**
	* 关键在union的过程
	* 根据两个元素所在树的元素个数不同判断合并方向
	* 将元素个数少的集合合并到元素个数多的集合上
	*/
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);

		//已经是一家子了，什么都不用做
		if (pRoot == qRoot) {
			return;
		}

		//如果其中一方小于另一方，将小的移到大的一方即可，其余什么都不用做，因为不会因此增加大的树的高度！
		if (rank[pRoot] < rank[qRoot]) {
			parent[pRoot] = qRoot;
		} else if (rank[pRoot] > rank[qRoot]) {
			parent[qRoot] = pRoot;
		} else {
			//此时，选择一个作为根结点
			parent[pRoot] = qRoot;
			//此时，需要维护rank的值
			rank[qRoot] += 1;
		}
	}
}