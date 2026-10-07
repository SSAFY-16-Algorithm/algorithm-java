import java.util.*;

class Solution {
	
	// 1. Node 클래스
	static class Node {
		int to, weight;
		public Node(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}
	
	static List<Node>[] graph;
	
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        
    	// 2. 인접 리스트 graph 생성
    	graph = new ArrayList[n+1];
    	
    	for (int i = 1; i <= n; i++) {
    		graph[i] = new ArrayList<>();
    	}
    	
    	for (int[] path : paths) {
    		int from = path[0];
    		int to = path[1];
    		int weight = path[2];
    		
    		// 3. 양방향 간선 저장
    		graph[from].add(new Node(to, weight));
    		graph[to].add(new Node(from, weight));
    	}
    	
    	// 4. intensity[] 생성 + INF 초기화
    	int[] intensity = new int[n+1];
    	Arrays.fill(intensity, Integer.MAX_VALUE);
    	
    	// 5. isSummit[] 생성
    	boolean[] isSummit = new boolean[n+1];
    	for (int summit : summits) {
    		isSummit[summit] = true;
    	}
    	
    	// 6. PriorityQueue
    	PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
    	
    	// 7. gates를 시작점으로 등록
    	for(int gate: gates) {
        	intensity[gate] = 0;
        	pq.offer(new int[] {gate, 0});
        }
    	
    	// 8. 다익스트라 탐색
    	while (!pq.isEmpty()) {
    		// 8-1. 가장 intensity가 작은 상태를 꺼낸다.
    		int[] cur = pq.poll();
    		// 8-2. 현재 정점과 현재 intensity를 꺼낸다.
    		int u = cur[0], d = cur[1];
    		// 8-3. 이미 더 좋은 값으로 방문된 상태라면 무시한다.
    		if (d != intensity[u]) continue;
    		// 8-4. 현재 정점이 산봉우리라면 더 이상 확장하지 않는다.
    		if (isSummit[u]) continue;
    		// 8-5. 아니면 graph[current]의 모든 Node를 확인한다.
    		for (Node node : graph[u]) {
    			int nextIntensity = Math.max(d, node.weight);
    			if (nextIntensity < intensity[node.to]) {
    				intensity[node.to] = nextIntensity;
    				pq.offer(new int[] {node.to, nextIntensity});
    			}
    		}
    	}
    	
    	// 9. 산봉우리 중 정답 선택
    	int bestSummit = 0;
    	int bestIntensity = Integer.MAX_VALUE;
    	
    	for (int summit : summits) {
    		if (intensity[summit] < bestIntensity) {
    			bestIntensity = intensity[summit];
    			bestSummit = summit;
    		}
    		if (intensity[summit] == bestIntensity && summit < bestSummit) {
    			bestSummit = summit;
    		}
    	}
    	
    	int[] answer = {bestSummit, bestIntensity};
        
    	return answer;
    
    }

}