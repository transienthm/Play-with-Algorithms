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
}