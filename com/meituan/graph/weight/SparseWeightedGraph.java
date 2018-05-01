package com.meituan.graph.weight;

import java.util.List;
import java.util.ArrayList;

public class SparseWeightedGraph<Weight extends Number & Comparable> implements WeightedGraph {
	private int n;
	private int m;
	private boolean directed;
	private List<Edge<Weight>>[] g;

	public SparseWeightedGraph(int n, boolean directed) {
		if (n < 0) {
			return;
		}
		this.n = n;
		this.directed = directed;
		this.g = (ArrayList<Edge<Weight>>[])new ArrayList[n];

		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<Edge<Weight>>();
		}
	}

	@Override
	public int getNodesCount() {
		return n;
	}

	@Override
	public int getEdgesCount() {
		return m;
	}

	@Override
	public boolean hasEdge(int v, int w) {
		if (!isLegal(v) || !isLegal(w)) {
			return false;
		}

		for (Edge e : adj(v)) {
			if (e.other(v) == w) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addEdge(Edge e) {
		if (e == null) {
			return;
		}

		int v = e.v();
		int w = e.w();

		if (!isLegal(v) || !isLegal(w)) {
			return;
		}

		g[v].add(new Edge(e));


		if (v != w && !directed) {
			g[w].add(new Edge(w, v, e.wt()));
		}

		m++;
	}

	@Override 
	public Iterable<Edge<Weight>> adj(int v) {
		List<Edge<Weight>> res = new ArrayList<>();
		if (!isLegal(v)) {
			return res;
		}

		return g[v];
	}

	@Override
	public void show() {
	    for( int i = 0 ; i < n ; i ++ ){
	        System.out.print("vertex " + i + ":\t");
	        for( int j = 0 ; j < g[i].size() ; j ++ ){
	            Edge e = g[i].get(j);
	            System.out.print( "( to:" + e.other(i) + ",wt:" + e.wt() + ")\t");
	        }
	        System.out.println();
    	}
	}

	private boolean isLegal(int i) {
		return i >= 0 && i < n;
	}
}