package com.meituan.graph.weight;

public interface TestInterface<Weight extends Number & Comparable> {
	public Iterable<Edge> adj();
}