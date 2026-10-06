import java.util.Arrays;

class Solution {
	
    public int solution(int n, int s, int a, int b, int[][] fares) {
    	
    	// 1. dist[i][j] 배열을 만든다.
    	// 2. 자기 자신까지의 거리는 0으로 둔다.
    	int INF = 1000000000;
    	int[][] dist = new int[n][n];
    	
    	for (int i = 0; i < n; i++) {
    		Arrays.fill(dist[i], INF);
    		dist[i][i] = 0;
    	}

    	// 3. fares를 이용해서 직접 연결된 요금을 넣는다.
    	for (int[] fare : fares) {
    		dist[fare[0]-1][fare[1]-1] = fare[2];
    		dist[fare[1]-1][fare[0]-1] = fare[2];    		
    	}
    	
    	// 4. 플로이드 워셜을 돌린다. → 모든 정점 쌍의 최단거리 완성
    	for (int k = 0; k < n; k++) {
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				// INF끼리 더하지 않도록 확인
                    if (dist[i][k] == INF ||
                        dist[k][j] == INF) {
                        continue;
                    }

                    dist[i][j] = Math.min(
                        dist[i][j],
                        dist[i][k] + dist[k][j]
                    );
    			}
    		}
    	}
    	
    	// 5. k = 1 ~ n을 확인한다.
    	// 비용 = dist[s][k] + dist[k][a] + dist[k][b]
    	// 6. 그중 최솟값을 반환한다.
    	int min = INF;
    	
    	for (int k = 0; k < n; k++) {
    		if (dist[s - 1][k] == INF ||
    			dist[k][a - 1] == INF ||
    			dist[k][b - 1] == INF) {
    			continue;
    		}
    		
    		int cost = dist[s-1][k] + dist[k][a-1] + dist[k][b-1];
    		if (cost < min) {
    			min = cost;
    		}
    	}
    	
        return min;
        
    }

}