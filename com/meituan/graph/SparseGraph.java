package com.meituan.graph;

public class SparseGraph {
	private int n, m;
	private boolean directed;
	private List<List<Integer>> g;

	public SparseGraph(int n, boolean directed) {
		assert n >= 0;
		this.n = n;
		this.m = 0;
		this.directed = directed;
		g = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			g.set(i) = new ArrayList<>();
		}
	}
}