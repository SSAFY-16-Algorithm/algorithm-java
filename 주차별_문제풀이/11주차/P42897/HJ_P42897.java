class Solution {

    public int solution(int[] money) {

        int n = money.length;

        // Case 1. 첫 번째 집을 포함할 수 있는 경우 (마지막 집 제외)
        int case1 = rob(money, 0, n - 2);

        // Case 2. 마지막 집을 포함할 수 있는 경우 (첫 번째 집 제외)
        int case2 = rob(money, 1, n - 1);

        // 두 경우 중 최댓값 반환
        return Math.max(case1, case2);
    }

    // start부터 end까지 인접한 집을 동시에 털지 않고 얻는 최대 금액
    private int rob(int[] money, int start, int end) {

        int prev2 = 0; // 두 집 전까지의 최대 금액
        int prev1 = 0; // 바로 이전 집까지의 최대 금액

        for (int i = start; i <= end; i++) {

            // 현재 집을 털거나, 털지 않는 경우 중 최댓값
            int current = Math.max(prev1, prev2 + money[i]);

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}