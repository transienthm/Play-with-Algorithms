package com.meituan.graph.weight;

import java.util.List;
import java.util.ArrayList;

public class LazyPrimMST<Weight extends Number & Comparable> {
	private WeightedGraph<Weight> g;
	private MinHeap<Edge> minHeap;
	private boolean[] marked;
	private List<Edge<Weight>> mst;
	private Number mstWeight;//最小生成树的权值

	public LazyPrimMST(WeightedGraph<Weight> graph) {
		this.g = graph;
		int m = g.getEdgesCount();
		int n = g.getNodesCount();
		this.minHeap = new MinHeap<Edge>(m - 1, Edge.class);
		this.marked = new boolean[n];
		this.mst = new ArrayList<>();

		visit(0);

		while (!minHeap.isEmpty()) {
			Edge<Weight> e = minHeap.extractMin();
			//如果这条边的两个端点都访问过了，或者没有访问过(事实上是不可能的，因为如果都没有访问过，那么这条边不会出现在堆中)，则扔掉这条边，它不是横切边
			if (marked[e.v()] == marked[e.w()]) {
				continue;
			}
			//否则这条边应该存在在最小生成树中
			mst.add(e);

			//访问和这条边连接的还没有被访问过的节点
			if (!marked[e.v()]) {
				visit(e.v());	
			} else {
				visit(e.w());
			}

			//计算最小生成树的权值
			mstWeight = mst.get(0).wt();
			for (int i = 1; i < mst.size(); i++) {
				mstWeight += mst.get(i).wt();
			}
		}
	} 

	private void visit(int v) {
		if (marked[v]) {
			return;
		}
		marked[v] = true;

		for (Edge<Weight> e : g.adj(v)) {
			if (!marked[e.other(v)]) {
				minHeap.insert(e);
			}
		}
	}

	public List<Edge<Weight>> getMstEdges() {
		return mst;
	}

	public Number getWeight() {
		return mstWeight;
	}
}