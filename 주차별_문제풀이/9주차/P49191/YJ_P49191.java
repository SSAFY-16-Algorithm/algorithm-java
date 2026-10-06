import java.util.Arrays;

public class YJ_P49191 {
	
	public static void main(String[] args) {
		int n = 5; 
		int[][] results = {{4, 3},{4, 2},{3, 2},{1, 2},{2, 5}};
		int result = solution(5, results);
		System.out.println(result);
	}
	
	static final int INF = 1_000_000_000;
	
	public static int solution(int n, int[][] results) {
        
        // results로 dist 배열 채우기
        int[][] dist = new int[n+1][n+1];
        for(int i=0; i<dist.length; i++) {
        	Arrays.fill(dist[i], INF);
        }
        
        for(int i=0; i<results.length; i++) {
        	// results = [A, B]면 A가 B를 이겼다
        	int A = results[i][0];
        	int B = results[i][1];
        	
        	dist[A][B] = 1;
        	dist[B][A] = -1;
        }
        int answer = floydWarshall(dist, n);
        
        return answer;
    }
	
	static int floydWarshall(int[][] dist, int n) {
		// 1번부터 n번까지이므로
		for(int k=1; k<=n; k++) {
			for(int i=1; i<=n; i++) {
				for(int j=1; j<=n; j++) {
					// 이 경우 순위가 정해짐 
					if(dist[i][k] == 1 && dist[k][j] == 1) {
						dist[i][j] = 1;
						dist[j][i] = -1; 
					}
					if(dist[i][k] == -1 && dist[k][j] == -1) {
						dist[i][j] = -1; 
						dist[j][i] = 1; 
					}
				}
			}
		}
		
		int count = 0; 
		
		// 각 행에 지거나 이긴 표시가 n-1개 되어있으면 순위 명확
		for(int r=1; r<=n; r++) {
			int num = 0; 
			for(int c=1; c<=n; c++) {
				if(dist[r][c] != INF) {
					num++;
				}
			}
			if(num == n-1) count++;
		}
		
		return count;
	}
}

// 플로이드-워셜
// [A, B] -> A가 B를 이겼다. 
// 순위가 내가 더 뒤면 +1, 모르면 0, 내가 더 앞이면 1
// -> 알고리즘을 다 돈다