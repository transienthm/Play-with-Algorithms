package com.meituan.unionfind;

public class QuickFind {
	private int[] id;
	private int count;

	public QuickFind(int n) {
		count = n;
		id = new int[n];
		for (int i = 0; i < n; i++) {
			//每个元素自己一个组别
			id[i] = i;
		}
	}

	// quick find
	public int find(int p) {
		assert(p >= 0 && p < count);
		return id[p];
	}

	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int pId = find(p);
		int qId = find(q);

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