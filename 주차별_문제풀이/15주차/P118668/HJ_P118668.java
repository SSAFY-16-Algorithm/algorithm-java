import java.util.*;

class Solution {

    public int solution(int alp, int cop, int[][] problems) {

        // 1. 모든 문제를 풀기 위해 필요한 목표 능력치 구하기
        int maxAlp = 0;
        int maxCop = 0;

        for (int[] problem : problems) {
            maxAlp = Math.max(maxAlp, problem[0]);
            maxCop = Math.max(maxCop, problem[1]);
        }

        // 이미 목표 능력치를 초과한 경우 목표값으로 조정
        alp = Math.min(alp, maxAlp);
        cop = Math.min(cop, maxCop);

        // 2. DP 배열 생성 및 초기화
        // dp[a][c] = 알고력 a, 코딩력 c까지 도달하는 최소 시간
        int INF = 1_000_000_000;
        int[][] dp = new int[maxAlp + 1][maxCop + 1];

        for (int i = 0; i <= maxAlp; i++) {
            Arrays.fill(dp[i], INF);
        }

        // 현재 능력치까지 필요한 시간은 0
        dp[alp][cop] = 0;

        // 3. 모든 능력치 상태를 순회하면서 최소 시간 갱신
        for (int a = alp; a <= maxAlp; a++) {
            for (int c = cop; c <= maxCop; c++) {

                // 방법 1. 알고리즘 공부 (+1)
                if (a < maxAlp) {
                    dp[a + 1][c] = Math.min(
                        dp[a + 1][c],
                        dp[a][c] + 1
                    );
                }

                // 방법 2. 코딩 공부 (+1)
                if (c < maxCop) {
                    dp[a][c + 1] = Math.min(
                        dp[a][c + 1],
                        dp[a][c] + 1
                    );
                }

                // 방법 3. 현재 풀 수 있는 문제 해결
                for (int[] problem : problems) {

                    int alpReq = problem[0];
                    int copReq = problem[1];
                    int alpRwd = problem[2];
                    int copRwd = problem[3];
                    int cost = problem[4];

                    // 요구 능력치를 만족하지 못하면 건너뛰기
                    if (a < alpReq || c < copReq) {
                        continue;
                    }

                    // 문제를 해결한 후의 능력치 계산
                    // 목표 능력치를 초과하지 않도록 제한
                    int nextAlp = Math.min(maxAlp, a + alpRwd);
                    int nextCop = Math.min(maxCop, c + copRwd);

                    // 기존 최소 시간과 현재 경로의 시간 비교
                    dp[nextAlp][nextCop] = Math.min(
                        dp[nextAlp][nextCop],
                        dp[a][c] + cost
                    );
                }
            }
        }

        // 4. 목표 능력치에 도달하는 최소 시간 반환
        return dp[maxAlp][maxCop];
    }
}