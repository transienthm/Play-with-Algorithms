package com.meituan.graph;

public class SparseGraph implements Graph{
	private int n, m;//n = node count; m = edge count;
	private boolean directed;
	private Vector<Integer>[] g;

	public SparseGraph(int n, boolean directed) {
		assert n >= 0;
		this.n = n;
		this.m = 0;
		this.directed = directed;
		g = (Vector<Integer>[]) new Vector[n];
		for (int i = 0; i < n; i++) {
			g[i] = new Vector<>();
		}
	}

	@Override
	public int V() {
		return n;
	}

	@Override
	public int E() {
		return m;
	}

	@Override
	public void addEdge(int v, int w) {
		assert v >= 0 && v < n;
		assert w >= 0 && w < n;

		g[v].add(w);
		if (v != w && !directed) {
			g[w].add(v);
		}
		m++;
	}

	@Override
	public boolean hasEdge(int v, int w) {
		assert v >= 0 && v < n;
		assert w >= 0 && w < n;

		for (int i = 0; i < g[v].size(); i++) {
			if (g[v].elementAt(i) == w) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void show() {

	}

	@Override
	public Iterable<Integer> adj(int v) {
		
	}
}