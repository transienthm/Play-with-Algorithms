package com.meituan.graph.weight;

public class Edge<Weight extends Number & Comparable> implements Comparable<Edge>{
	
	private int a, b;//边的两个顶点
	private Weight weight;

	public Edge(int a, int b, Weight weight) {
		this.a = a;
		this.b = b;
		this.weight = weight;
	}

	public Edge(Edge<Weight> e) {
		this.a = e.a;
		this.b = e.b;
		this.weight = e.weight;
	}

	public int v() {
		return a;
	}

	public int w() {
		return b;
	}

	public Weight wt() {
		return weight;
	} 

	public int other(int x) {
		if (x != a && x != b) {
			return -1;
		}

		return x == a ? b : a;
	}
	
	@Override
	public int compareTo(Edge that) {
		return 0;
	}

	@Override
	public String toString() {
		return "" + a + "-" + ": " + weight;
	}
}