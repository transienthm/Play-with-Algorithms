package com.meituan.graph.weight;

import java.util.List;
import java.util.ArrayList;

public class Test {

	public Test(TestInterface2 ti) {
		for (Edge e : ti.adj()) {
			System.out.print(e + " ");
		}
	}
}

interface TestInterface2 {
	public Iterable<Edge> adj();
} 