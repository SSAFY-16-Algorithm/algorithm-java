import java.util.*;

class Solution {

    public int solution(int N, int number) {

        // 1. DP 배열 초기화
        // dp[i] = N을 정확히 i번 사용해서 만들 수 있는 모든 숫자
        // 최대 8번까지 사용 가능하므로 dp[0] ~ dp[8] 생성
        List<Set<Integer>> dp = new ArrayList<>();

        for (int i = 0; i <= 8; i++) {
            dp.add(new HashSet<Integer>());
        }

        // N을 이어 붙인 숫자 저장용
        // 예: N=5 -> 5, 55, 555, ...
        int repeated = 0;

        // 2. N을 사용하는 횟수를 1부터 8까지 증가
        for (int count = 1; count <= 8; count++) {

            // N을 count번 이어 붙인 숫자 생성
            repeated = repeated * 10 + N;

            Set<Integer> current = dp.get(count);
            current.add(repeated);

            // 3. 사용 횟수를 두 부분으로 분할
            // count=4라면 (1,3), (2,2), (3,1)
            for (int leftCount = 1; leftCount < count; leftCount++) {

                int rightCount = count - leftCount;

                // 왼쪽 집합의 모든 숫자 확인
                for (int a : dp.get(leftCount)) {

                    // 오른쪽 집합의 모든 숫자 확인
                    for (int b : dp.get(rightCount)) {

                        // 4. 사칙연산 결과 저장
                        current.add(a + b);
                        current.add(a - b);
                        current.add(a * b);

                        // 0으로 나누는 경우 제외
                        if (b != 0) {
                            current.add(a / b);
                        }
                    }
                }
            }

            // 5. 목표 숫자가 존재하면 최소 사용 횟수 반환
            if (current.contains(number)) {
                return count;
            }
        }

        // 6. N을 8번 사용해도 만들지 못한 경우
        return -1;
    }
}