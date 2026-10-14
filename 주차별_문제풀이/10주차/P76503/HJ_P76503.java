import java.util.*;

class Solution {
  
	public long solution(int[] a, int[][] edges) {
		
		// 정점들의 가중치 합이 0이 아닌 경우 -> 절대 모든 정점을 0으로 만들 수 없으므로 -1 return
		long total = Arrays.stream(a).asLongStream().sum();
		if (total != 0) {
			return -1;
		}
		
		// 모든 정점의 가중치가 0인 경우 -> return 0
		boolean allZero = true;
		for (int aElement : a) {
			if (aElement != 0) {
				allZero = false;
			}
		}	
		if (allZero) {
			return 0;
		}
		
		// 트리의 모든 정점을 0으로 만드는 연산의 최소 횟수 구하기
		// 1. 각 정점이 어떤 정점과 연결되어 있는지 저장하는 graph 초기화
		int n = a.length;
		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		
		// 2. 각 정점들을 연결하는 간선들 정보를 통해 초기화한 graph에 이웃한 정점들의 정보를 저장
		for (int[] edge : edges) {
			int u = edge[0];
			int v = edge[1];
			graph.get(u).add(v);
			graph.get(v).add(u); // 이 문제는 무방향 그래프 이므로 양쪽 방향을 모두 연결해주어야 함.
		}
		
		// 3. graph에서 연결된 개수는 degree이므로 degree 배열에 각 정점별로 연결된 정점의 개수를 저장
		int[] degree = new int[n];
		for (int i = 0; i < n; i++) {
			degree[i] = graph.get(i).size();
		}
		
		// 4. degree가 1인 정점을 찾아서 큐에 삽입
		Queue<Integer> queue = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			if (degree[i] == 1) {
				queue.offer(i);
			}
		}
		
		long answer = 0; // 연산 횟수
		long[] weight = new long[n]; // 가중치가 전달되면서 변경되는 각 정점의 가중치 -> a 배열을 그대로 복사
		for (int i = 0; i < n; i++) {
		    weight[i] = a[i];
		}
		
		while (!queue.isEmpty()) {
			
			// 5. 큐에서 리프 노드를 꺼내 연결된 이웃 정점으로 가중치를 전달합니다.
			// 6. 처리한 리프 노드를 제거하면서 이웃 정점의 degree를 감소시킵니다.
			int cur = queue.poll();
			
			// 이미 처리된 정점이라면 건너뛰기
		    if (degree[cur] == 0) {
		        continue;
		    }
			
			for (int next : graph.get(cur)) {
				
				// 이미 처리된 정점이라면 건너뛰기
				if (degree[next] == 0) {
			        continue;
			    }
				
				answer += Math.abs(weight[cur]);
				
				weight[next] += weight[cur];
				
				degree[next]--;
			
				if (degree[next] == 1) {
					queue.offer(next);
				}
				
			}
			
			degree[cur] = 0;
			
		}
        
		return answer;
    
	}

}