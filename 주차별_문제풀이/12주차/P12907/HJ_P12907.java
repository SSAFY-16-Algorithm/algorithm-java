class Solution {

    public int solution(int n, int[] money) {

        // 1. 나머지 연산에 사용할 상수 선언
        final int MOD = 1_000_000_007;

        // 2. DP 배열 초기화
        // dp[i]: 지금까지 처리한 동전으로
        //        i원을 만드는 방법의 수
        int[] dp = new int[n + 1];

        // 0원을 만드는 방법은
        // 아무 동전도 사용하지 않는 1가지
        dp[0] = 1;

        // 3. 모든 동전 종류를 하나씩 확인
        for (int coin : money) {

            // 4. 현재 동전으로 만들 수 있는
            // 금액을 오름차순으로 순회
            for (int i = coin; i <= n; i++) {

                // 기존 방법의 수
                // + 현재 동전을 하나 추가하는 방법의 수
                dp[i] = (dp[i] + dp[i - coin]) % MOD;
    		}
  
        }

        // 5. n원을 만드는 전체 방법의 수 반환
        return dp[n];
    }
}