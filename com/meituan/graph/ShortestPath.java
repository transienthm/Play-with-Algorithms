package com.meituan.graph;

import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

//最短路径算法
public class ShortestPath {
	private Graph g;
	private int s;//start point
	private boolean[] visited;
	private int[] from;
	private int[] order;

	public ShortestPath(Graph g, int s) {
		this.g = g;
		if (s < 0 || s >= g.V()) {
			return;
		}
		
		int n = g.V();
		visited = new boolean[n];
		from = new int[n];
		order = new int[n];
		for (int i = 0; i < n; i++) {
			visited[i] = false;
			from[i] = -1;
			order[i] = -1;
		}
		this.s = s;
		Queue<Integer> queue = new LinkedList<>();

		queue.push(s);
		visited[s] = true;
		order[s] = 0;

		while(!queue.isEmpty()) {
			int v = queue.pop();
			for (int i : g.adj(v) {
				if (!visited[i]) {		
					q.push(i);
					visited[i] = true;
					from[i] = v;
					order[i] = order[v] + 1;
				}
			}
		}
	}

	/**
	* 判断s到w有没有路径
	*/
	public boolean hasPath(int w) {
		if (w < 0 || w >= g.V()) {
			return false;
		}
		return visited[w];
	}

	//查询s到w的路径
	public List<Integer> path(int w) {
		if (!hasPath(w)) {
			return null;
		}

		List<Integer> res = new ArrayList<>();

		Stack<Integer> s = new Stack<>();
		int p = w;

		while (p != -1) {
			s.push(p);
			p = from[p];
		}

		while (!s.isEmpty()) {
			res.add(s.pop());
		}

		return res;
	}

}