import java.util.ArrayList;
import java.util.ArrayDeque;

class Solution {

    public int solution(String name) {

        int answer = 0;
        int N = name.length();
        int[] dial = new int[N];

        // 위 / 아래 이동
        for (int i = 0; i < N; i++) {

            dial[i] = name.charAt(i) - 'A';

            if (dial[i] >= 14) {
                dial[i] = 26 - dial[i];
            }

            answer += dial[i];
        }

        // =====================

        // 좌 / 우 이동
        int move = N - 1;

        for (int i = 0; i < N; i++) {

            // i 다음에 나오는 A가 아닌 위치 찾기
            int j;

            for (j = i + 1; j < N; j++) {
                if (dial[j] != 0) {
                    break;
                }
            }

            // 오른쪽으로 i까지 갔다가
            // 다시 돌아서 반대편으로 가는 경우
            int moveR = i * 2 + (N - j);

            // 왼쪽으로 먼저 갔다가
            // 오른쪽으로 돌아오는 경우
            int moveL = i + 2 * (N - j);

            int movemin = Math.min(moveR, moveL);

            move = Math.min(move, movemin);
        }

        answer += move;

        return answer;
    }
}
