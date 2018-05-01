package com.meituan.graph.weight;

public class LazyPrimMST<Weight extends Number & Comparable> {
	private WeightedGraph<Weight> g;
	private MinHeap<Edge<Weight>> minHeap;
	private boolean[] marked;
	private List<Edge<Weight>> mst;
	private Number mstWeight;

	public LazyPrimMST(WeightedGraph<Weight> graph) {
		this.g = graph;
		int m = g.getEdgesCount();
		int n = g.getNodesCount();
		minHeap = new MinHeap<Edge<Weight>>(m - 1, Edge.class);
		marked = new boolean[n];
		mst = new ArrayList<>();

		visit(0);
		while (!minHeap.isEmpty()) {
			
		}
	} 
}