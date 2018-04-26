package com.meituan.graph;

public class Path {
	private Graph g; 
	private int[] from;
	private boolean[] visited;
	private int s;// 起始点

	public Path(Graph g, int s) {
		if (s < 0 || s > g.V()) {
			return;
		}
		this.g = g;
		this.s = s;
		
		int n = g.V();

		visited = new boolean[n];
		from = new int[n];

		for (int i = 0; i < n; i++) {
			visited[i] = false;
			from[i] = -1;
		}

		dfs(s);
	} 

	
}