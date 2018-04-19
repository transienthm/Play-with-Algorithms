package com.meituan.unionfind;

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