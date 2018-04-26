package com.meituan.graph;

//求无权图的联通分量
public class Component {
	private Graph g;
	boolean[] visited; // 记录dfs的过程中节点是否被访问
	int ccount; // 记录联通分量个数
	private int[] id; // 每个节点所对应的联通分量标记
	
	public Component(Graph g) {
		this.g = g;
		int n = g.V();
		visited = new boolean[n];
		id = new int[n];
		ccount = 0;
		for (int i = 0; i < n; i++) {
			visited[i] = false;
			id[i] = -1;
		}

		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				dfs(i);
				ccount++;
			}
		}
	}

	//非常依赖边的遍历
	private void dfs(int i) {
		visited[i] = true;
		id[i] = ccount;

		for (int j : g.adj(i)) {
			if (!visited[j]) {
				dfs(j);
			}
		}
	}

	public int count() {
		return ccount;
	}

	//判断点v和点w是否联通
	public boolean isConnected(int v, int w) {
		if (g == null || g.V() == 0) {
			return false;
		}
		
		int n = g.V();

		if (v < 0 || v >= n || w < 0 || w >= n) {
			return false;
		}
		return id[v] == id[w];
	}
}