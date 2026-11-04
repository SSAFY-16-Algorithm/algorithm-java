import java.util.*;

class Solution {

    public int solution(int[][] matrix_sizes) {

        int n = matrix_sizes.length;

        // dp[i][j] : i번째 ~ j번째 행렬을
        // 곱하는 데 필요한 최소 연산 횟수
        int[][] dp = new int[n][n];

        // 1. 행렬이 하나인 구간은 연산 횟수가 0
        // int 배열의 기본값이 0이므로 별도 초기화 생략

        // 2. 구간 길이를 2개부터 n개까지 증가
        // 작은 구간의 결과를 먼저 계산
        for (int len = 2; len <= n; len++) {

            // 3. 현재 구간의 시작, 끝 위치 결정
            for (int start = 0; start + len <= n; start++) {

                int end = start + len - 1;

                // 최소 연산 횟수를 구하기 위한 초기값
                dp[start][end] = Integer.MAX_VALUE;

                // 4. 모든 분할 위치 k 확인
                for (int k = start; k < end; k++) {

                    // 왼쪽 구간의 최소 곱셈 횟수
                    // + 오른쪽 구간의 최소 곱셈 횟수
                    // + 두 결과 행렬을 곱하는 횟수
                    int cost = dp[start][k]
                             + dp[k + 1][end]
                             + matrix_sizes[start][0]
                             * matrix_sizes[k][1]
                             * matrix_sizes[end][1];

                    // 5. 현재 구간의 최소 연산 횟수 갱신
                    dp[start][end] =
                            Math.min(dp[start][end], cost);
                }
            }
        }

        // 6. 전체 행렬을 곱하는 최소 연산 횟수 반환
        return dp[0][n - 1];
    }
}
