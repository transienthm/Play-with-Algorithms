package com.meituan.graph;

import java.util.ArrayList;
import java.util.List;

public class MySparseGraph {
	private int n, m;
	private boolean directed;
	private List<Integer>[] g;

	public MySparseGraph(int n, boolean directed) {
		this.n = n;
		this.directed = directed;
		this.m = 0;
		g = (ArrayList<Integer>[])new ArrayList[n];
	}

	
}