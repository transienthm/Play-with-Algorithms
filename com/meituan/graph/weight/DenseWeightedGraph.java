package com.meituan.graph.weight;

import java.util.List;
import java.util.ArrayList;

public class DenseWeightedGraph<Weight extends Number & Comparable> implements WeightedGraph {

	private int n;
	private int m;
	private boolean directed;
	private Edge<Weight>[][] g;

	public DenseWeightedGraph(int n, boolean directed) {
		this.n = n;
		this.m = 0;
		this.directed = directed;

		g = new Edge[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				g[i][j] = null;
			}
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
	public void addEdge(Edge e) {
		if (e == null) {
			return;
		}

		int v = e.v();
		int w = e.w();

		if (v < 0 || v >= n || w < 0 || w >= n || hasEdge(v, w)) {
			return;
		}

		g[v][w] = new Edge(e);

		if (v != w && !directed) {
			g[w][v] = new Edge(e.w(), e.v(), e.wt());
		}

		m++;
	}

	@Override
	public boolean hasEdge(int v, int w) {
		if (v < 0 || v >= n || w < 0 || w >= n) {
			return false;
		}

		return g[v][w] != null;
	}

	@Override
	public void show() {
	    for( int i = 0 ; i < n ; i ++ ){
            for( int j = 0 ; j < n ; j ++ ) {
                if( g[i][j] != null ) {
                    System.out.print(g[i][j].wt()+"\t");
                }
                else {
                    System.out.print("NULL\t");
                }
            }
            System.out.println();
        }
	}

	@Override
	public Iterable<Edge<Weight>> adj(int v) {
		List<Edge<Weight>> res = new ArrayList<>();
		if (v < 0 || v >= n) {
			return res;
		}

		for (int i = 0; i < n; i++) {
			if (g[v][i] != null) {
				res.add(g[v][i]);
			}
		}

		return res;
	}
}