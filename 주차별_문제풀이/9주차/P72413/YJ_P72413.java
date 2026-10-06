
import java.util.*;

public class YJ_P72413 {
	
    public static void main(String[] args) {
        int n = 6;
        int s = 4;
        int a = 6;
        int b = 2;
        int[][] fares = {{4, 1, 10},{3, 5, 24},{5, 6, 2},{3, 1, 41},{5, 1, 24},{4, 6, 50},{2, 4, 66},{2, 3, 22},{1, 6, 25}};
        int result = solution(n, s, a, b, fares);
        
        System.out.println(result);
    }
    
    static int INF = 1_000_000_000;
    
    public static int solution(int n, int s, int a, int b, int[][] fares) {
        
        // 1. fares 를 기준으로 각 지점간 최단거리를 dist 배열에 저장한다(플로이드-워셜 사용)
        // 시작점이 정해져 있어서 처음엔 다익스트라로 풀어야 되나 생각을 했는데 어차피 뒤에서 각 지점 간의 최소 길이가 필요하므로
        // 플로이드 - 워셜 배열 한 번만 쓰기로 결정
        
        int[][] dist = new int[n+1][n+1];
        
        for(int i=1; i<=n; i++) {
            Arrays.fill(dist[i], INF);
        }
        
        for(int i=1; i<=n; i++) {
            dist[i][i] = 0;
        }
        
        for(int[] fare: fares) {
            int c = fare[0];
            int d = fare[1];
            int f = fare[2];
            
            dist[c][d] = f;
            dist[d][c] = f;
        }
        
        // 플로이드-워셜 알고리즘 수행(각 지점간 최단 거리 찾기)
        for(int k=1; k<=n; k++) {
            for(int i=1; i<=n; i++) {
                for(int j=1; j<=n; j++) {
                    if(dist[i][k] == INF || dist[k][j] == INF) continue;
                    dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
                }
            }
        }
        
        // 2. 각 지점까지를 합승 위치로 하고 지점 이후로는 각자 간다고 했을 때의 요금을 계산해서 min변수 업데이트
        // min(min, dist[s][k] + dist[k][B] + dist[k][A]) - K까지만 같이 온다고 했을 때
        int min = Integer.MAX_VALUE;
        
        for(int k=1; k<=n; k++) {
            if(dist[s][k] == INF || dist[k][a] == INF || dist[k][b] == INF) {
                continue;
            }
            min = Math.min(min, dist[s][k]+dist[k][a]+dist[k][b]);
        }
        
        return min;
    }
}
