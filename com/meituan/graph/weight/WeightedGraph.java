package com.meituan.graph.weight;

public interface WeightedGraph<Weight extends Number & Comparable>  {
	public int getNodesCount();
	public int getEdgesCount();
	public void addEdge(Edge<Weight> e);
	public boolean hasEdge(int v, int w);
	public void show();
	public Iterable<Edge<Weight>> adj(int v);
}