import java.util.Arrays;

class MY_P49191 {
    public int solution(int n, int[][] results) {
        // win[a][b] = 1은 a가 b를 이긴다는 뜻
        // 0은 진 것, -1은 결과를 알 수 없는 것
        int[][] win = new int[n + 1][n + 1];
        
        for (int i = 0; i <= n; i++) {
            Arrays.fill(win[i], -1);
        }
        
        for (int i = 0; i < results.length; i++) {
            int[] result = results[i];
            int a = result[0];
            int b = result[1];
            win[a][b] = 1;
            win[b][a] = 0;
        }
        
        // i가 k를 이기고 k가 j를 이기면
        // i가 j를 이길 수 있음
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) { 
                if (i == k) continue;
                for (int j = 1; j <= n; j++) {
                    if (i == j) continue;
                    if (win[i][k] == 1 && win[k][j] == 1) {
                        win[i][j] = 1;
                        win[j][i] = 0;
                    }
                }
            }
        }
        
        System.out.println(Arrays.deepToString(win));
        
        int answer = 0;
        for (int i = 1; i <= n; i++) {
            int cnt = 0;
            for (int j = 1; j <= n; j++) {
                if (i == j) continue;
                if (win[i][j] == -1) break;
                cnt++;
            }
            // 나 자신 제외, 0번 인덱스 제외
            if (cnt == n - 1) answer++;
        }
        
        return answer;
    }
}
