class Solution {
	
    public int solution(int n, int[][] results) {
    	
        // match[i][j] = true
        // → i번 선수가 j번 선수보다 강하다는 사실을 알고 있음
        boolean[][] match = new boolean[n + 1][n + 1];
    	
        // 1. 주어진 직접적인 승패 관계 저장
        // [A, B]는 A가 B를 이겼다는 의미
        for (int[] result : results) {
            match[result[0]][result[1]] = true;
        }
    	
        // 2. 플로이드-워셜을 이용해 간접적인 승패 관계까지 계산
        // i가 k를 이기고,
        // k가 j를 이긴다면,
        // i는 j도 이긴다고 판단할 수 있다.
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (match[i][k] && match[k][j]) {
                        match[i][j] = true;
                    }
                }
            }
        }
        
        int answer = 0;
        
        // 3. 각 선수의 순위를 정확하게 알 수 있는지 확인
        for (int i = 1; i <= n; i++) {
        	
            // i번 선수와 승패 관계를 알고 있는 선수 수
            int known = 0;
        	
            for (int j = 1; j <= n; j++) {
        		
                // 자기 자신과의 관계는 확인하지 않는다.
                if (i == j) {
                    continue;
                }
        		
                // i가 j를 이겼거나,
                // j가 i를 이겼다면
                // 두 선수 중 누가 더 높은 순위인지 알 수 있다.
                if (match[i][j] || match[j][i]) {
                    known++;
                }        		
            }
        	
            // 자신을 제외한 모든 선수와의 승패 관계를 안다면
            // i번 선수의 정확한 순위를 결정할 수 있다.
            if (known == n - 1) {
                answer++;
            }
        }

        return answer;
    }
}