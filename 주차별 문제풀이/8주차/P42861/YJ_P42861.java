import java.util.*;

public class YJ_P42861 {
	public static void main(String[] args) {
		int n = 4; 
		int[][] costs = {{0,1,1},{0,2,2},{1,2,5},{1,3,1},{2,3,8}};
		System.out.println(solution(n, costs));
	}
	
	static int[] parent;
	
	// 비용 작은 순으로 정렬
	// 같은 집합이면 통행 가능
	// 서로 다른 집합의 개수 구해서 -1 을 return 한다. 
	public static int solution(int n, int[][] costs) {
		
		Arrays.sort(costs, (o1, o2)-> {
			return o1[2] - o2[2];
		});
		
		parent = new int[n];
		
		for(int i=0; i<n; i++) {
			parent[i] = i;
		}
		
		int totalCost = 0;
		for(int i=0; i<costs.length; i++) {
			int a = costs[i][0];
			int b = costs[i][1];
			int cost = costs[i][2];
			
			if(find(a) != find(b)) {
				union(a, b);
				totalCost += cost;
			}
		}
		return totalCost; 
	}
	
	public static void union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		
		if(rootA == rootB) return;
		
		if(rootA > rootB) parent[rootA] = rootB;
		else parent[rootB] = rootA;
	}
	
	public static int find(int x) {
		if(parent[x] == x) return x;
		
		return parent[x] = find(parent[x]); 
	}
}
