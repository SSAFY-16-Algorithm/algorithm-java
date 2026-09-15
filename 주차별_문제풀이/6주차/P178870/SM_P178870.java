import java.util.Arrays;

public class SM_P178870 {
	public static void main(String[] args) {
		int[] sequence = {1,2,3,4,5};
		int[] sequence1 = {1,1,1,2,3,4,5};

		System.out.println(Arrays.toString(solution(sequence, 7)));
		System.out.println(Arrays.toString(solution(sequence1, 5)));
	}
	

    public static int[] solution(int[] sequence, int k) {
        int left = 0; // 현재 부분 수열의 시작 위치
        int sum = 0; // left - right 구간의 합

        int bestLeft = 0; // 지금까지 찾은 정답 구간
        int bestRight = sequence.length - 1;
        int bestLen = sequence.length + 1; //현재 찾은 가장 짧은 수열의 길이

        // right를 오른쪽으로 한칸씩 이동시키면서 부분 수열의 범위를 늘린다
        for (int right = 0; right < sequence.length; right++) {

            sum += sequence[right]; // 새로운 숫자를 현재 구간에 포함

            while (sum > k) { // 합이 k보다 크다면 왼쪽 숫자를 하나씩 제거하며 범위를 줄인다
                sum -= sequence[left];
                left++;
            }

            if (sum == k) { // left - right 구간의 합이 k인 경우
                int len = right - left + 1; // 현재 부분 수열의 길이

                if (len < bestLen) { // 더 짧은 수열을 찾으면 정답 갱신
                    bestLen = len;
                    bestLeft = left;
                    bestRight = right;
                }
            }
        }

        return new int[]{bestLeft, bestRight};
    }
}

