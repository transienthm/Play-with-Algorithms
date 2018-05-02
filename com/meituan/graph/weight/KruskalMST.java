package com.meituan.graph.weight;

import java.util.List;
import java.util.ArrayList;

public class KruskalMST<Weight extends Number & Comparable> {
	
	private List<Edge<Weight>> mst;
	private Number mstWeight;

	public KruskalMST(WeightedGraph g) {
		int n = g.getNodesCount();
		int m = g.getEdgesCount();
		MinHeap<Edge> minHeap = new MinHeap<>(m, Edge.class);
		for (int i = 0; i < n; i++) {
			for (Object o : g.adj(i)) {
				Edge<Weight> e = (Edge<Weight>) o;
				//防止重复放入边
				if (e.v() <= e.w()) {
					minHeap.insert(e);
				}
			}
		}

		RankUF uf = new RankUF(n);
		while (!minHeap.isEmpty() && mst.size() < n - 1) {
			Edge<Weight> e = minHeap.extractMin();

			if (uf.isConnected(e.v(), e.w())) {
				continue;
			}

			mst.add(e);
			uf.union(e.v(), e.w());
		}

		mstWeight = mst.get(0).wt();
		for (int i = 1; i < mst.size(); i++) {
 			mstWeight = mstWeight.doubleValue() + mst.get(i).wt().doubleValue();
		}
	}

	public List<Edge<Weight>> getMSTEdges() {
		return mst;
	}

	public Number getWeight() {
		return mstWeight;
	}
}