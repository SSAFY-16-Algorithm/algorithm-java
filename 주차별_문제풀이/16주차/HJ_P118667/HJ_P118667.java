class Solution {

    public int solution(int[] queue1, int[] queue2) {

        int n = queue1.length; // 각 큐의 초기 원소 개수
        int length = 2 * n;    // 가상 원형 배열의 전체 길이

        // 현재 queue1이 차지하는 구간을 [left, right)로 표현
        int left = 0;  // 현재 구간의 첫 번째 원소 위치
        int right = n; // 현재 구간의 마지막 원소 바로 다음 위치

        long sum = 0L;      // 현재 queue1 구간의 원소 합
        long totalSum = 0L; // 두 큐의 전체 원소 합

        int count = 0; // 지금까지 수행한 원소 이동 횟수

        // 1. 두 배열을 실제로 합치지 않고 원소의 합만 계산
        for (int i = 0; i < n; i++) {
            sum += queue1[i];

            // 산술 오버플로우 방지를 위해 long 타입으로 계산
            totalSum += (long) queue1[i] + queue2[i];
        }

        // 2. 전체 합이 홀수라면 두 큐의 합을 같게 만들 수 없음
        if (totalSum % 2 != 0) {
            return -1;
        }

        // 두 큐가 각각 가져야 하는 목표 합
        long target = totalSum / 2;

        // 3. 투포인터를 이용한 목표 합 탐색
        while (sum != target) {

            // 최대 4N회의 작업을 수행하도록 제한하여
            // 목표 합에 도달할 수 없는 경우의 무한 반복 방지
            if (count >= 2 * length) {
                return -1;
            }

            // 현재 구간의 합이 목표값보다 큰 경우
            // queue1에서 queue2로 원소를 이동시키는 것과 동일
            if (sum > target) {

                // 나머지 연산을 이용하여 가상 원형 배열의 인덱스 계산
                int index = left % length;

                // left가 가리키는 실제 원소를 현재 구간 합에서 제외
                if (index < n) {
                    // 가상 배열의 앞쪽 절반은 queue1
                    sum -= queue1[index];
                } else {
                    // 가상 배열의 뒤쪽 절반은 queue2
                    sum -= queue2[index - n];
                }

                // 구간의 시작점을 이동시켜 현재 구간 축소
                left++;

            } else {

                // 현재 구간의 합이 목표값보다 작은 경우
                // queue2에서 queue1로 원소를 이동시키는 것과 동일

                // 나머지 연산을 이용하여 가상 원형 배열의 인덱스 계산
                int index = right % length;

                // right가 가리키는 실제 원소를 현재 구간 합에 포함
                if (index < n) {
                    // 가상 배열의 앞쪽 절반은 queue1
                    sum += queue1[index];
                } else {
                    // 가상 배열의 뒤쪽 절반은 queue2
                    sum += queue2[index - n];
                }

                // 구간의 끝점을 이동시켜 현재 구간 확장
                right++;
            }

            // 포인터 이동 1회 = 원소 이동 작업 1회
            count++;
        }

        // 4. 두 큐의 합이 같아졌으므로 최소 작업 횟수 반환
        return count;
    }
}