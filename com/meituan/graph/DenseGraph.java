package com.meituan.graph;

public class DenseGraph {
	private int n; //节点的数量
	private int m; //边的数量
	private boolean directed; // 是否为有向图
	private boolean[][] g;// 图的具体数据

	public DenseGraph(int n, boolean directed) {
		this.n = n;
		this.m = 0;
		this.directed = directed;
		g = new boolean[n][n];
	}

	public int V() {
		return n;
	}

	public int E() {
		return m;
	}

	public void addEdge(int v, int w) {
		assert(v >= 0 && v < n);
		assert(w >= 0 && w < n);

		if (hasEdge(v, w)) {
			return;
		}

		g[v][w] = true;

		if (!directed) {
			g[w][v] = true;
		}

		m++;
	}

	public boolean hasEdge(int v, int w) {
		assert(v >= 0 && v < n);
		assert(w >= 0 && w < n);
		return g[v][w];
	}

}