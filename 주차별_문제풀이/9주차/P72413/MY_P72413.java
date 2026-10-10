import java.util.Arrays;

public class MY_P72413 {
    public static int solution(int n, int s, int a, int b, int[][] fares) {
        // 최단거리 구해놓고
        // 목적지까지 합이 가장 작은 경유지를 찾고
        // 각자 가는 비용과 비교해서
        // 선택하기
        // 경유지 때문에 너무 플로이드 워셜...
        
        int[][] dist = new int[n + 1][n + 1];
        int INF = 100_000 * n * 2 + 1;
        for (int i = 0; i <= n; i++) {
            // 요금이 100_000까지니까
            Arrays.fill(dist[i], INF);
            if (i >= 1) dist[i][i] = 0;
        }
    
        // 무향 그래프 그리기
        for (int i = 0; i < fares.length; i++) {
            int[] fare = fares[i];
            int A = fare[0];
            int B = fare[1];
            int cost = fare[2];
            
            dist[A][B] = cost;
            dist[B][A] = cost;
        }
    
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                if (i == k || dist[i][k] >= INF) continue;
                for (int j = 1; j <= n; j++) {
                    if (i == j) continue;
                    if (dist[k][j] >= INF) continue;
                    int curCost = dist[i][k] + dist[k][j];
                    if (curCost < dist[i][j]) {
                        // 하나만 해도 된다고 함
                        dist[i][j] = curCost;
                        dist[j][i] = curCost;
                    }
                }
            }
        }
    
        // System.out.println(Arrays.deepToString(dist));
        
        int minCost = dist[s][a] + dist[s][b];
        
        // System.out.println("dist[6][6]: " + dist[6][6]);
        
        for (int k = 1; k <= n; k++) {
            // 경유지에서 둘 다 내리는 방법
            int curCost = dist[s][k] + dist[k][a] + dist[k][b];
            if (curCost < minCost) {
                // System.out.println("경유지: " + k);
                minCost = curCost;
            }
        }
        
        return minCost;
    }

    public static void main(String args[]) {
    	System.out.println(solution(6, 4, 6, 2, 
		new int[][] {{4,1,10}, {3,5,24}, {5,6,2}, {3,1,41}, {5,1,24}, 
			{4,6,50}, {2,4,66}, {2,3,22}, {1,6,25}
		}
	));
    }
}
