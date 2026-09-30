import java.util.HashMap;
import java.util.Map;

class Solution {

    public int[] solution(String[] enroll, String[] referral,
                          String[] seller, int[] amount) {

        int n = enroll.length;

        // 이름 -> enroll 배열 인덱스
        Map<String, Integer> indexMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            indexMap.put(enroll[i], i);
        }

        // 각 판매원의 추천인 인덱스
        // -1이면 center
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {

            if (referral[i].equals("-")) {
                parent[i] = -1;
            } else {
                parent[i] = indexMap.get(referral[i]);
            }
        }

        // 각 판매원의 최종 수익
        int[] answer = new int[n];

        // 판매 기록 하나씩 처리
        for (int i = 0; i < seller.length; i++) {

            int current = indexMap.get(seller[i]);
            int profit = amount[i] * 100;

            // 추천인 방향으로 이익 분배
            while (current != -1 && profit > 0) {

                int commission = profit / 10;
                int myProfit = profit - commission;

                answer[current] += myProfit;

                // 추천인에게 전달
                profit = commission;
                current = parent[current];
            }
        }

        return answer;
    }
}