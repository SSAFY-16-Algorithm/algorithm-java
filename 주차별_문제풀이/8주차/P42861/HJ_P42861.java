import java.util.*;

class Solution {

	int[] parent;

	public int solution(int n, int[][] costs) {

		parent = new int[n];

		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
		
		int answer = 0;
		int edgeCount = 0;
		
		// 크루스칼 알고리즘 1단계 : 모든 간선 가중치 기준 오름차순으로 정렬한다.
		Arrays.sort(costs, (a, b) -> Integer.compare(a[2], b[2]));

		// 크루스칼 알고리즘 2단계 : 정렬된 간선을 순서대로 확인하며 두 정점 대표 노드를 확인한다.
		for (int[] edge : costs) {
			int nodeA = edge[0];
			int nodeB = edge[1];
			int cost = edge[2];

			int rootA = find(nodeA);
			int rootB = find(nodeB);

			if (rootA != rootB) {
				union(nodeA, nodeB);
				answer += cost;
				edgeCount++;
				
				if (edgeCount == n - 1) {
					break;
				}
			}
		}

		return answer;
	}

	int find(int node) {
		if (parent[node] == node) return node;
		return parent[node] = find(parent[node]);
	}
	
	void union(int nodeA, int nodeB) {
		nodeA = find(nodeA);
		nodeB = find(nodeB);
		parent[nodeB] = nodeA;
	}
}