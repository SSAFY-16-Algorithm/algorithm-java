import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {

    static int[][] grid = new int[4][4];
    static Set<Integer> results;

    // 상하좌우 델타 탐색
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            // 1. 격자판 입력
            for (int i = 0; i < 4; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < 4; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 2. 완성된 7자리 수의 중복 제거를 위한 HashSet 초기화
            // 모든 시작점의 탐색 결과를 하나의 Set에서 관리
            results = new HashSet<>();

            // 3. 모든 격자 칸을 시작점으로 BFS 수행
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    bfs(i, j);
                }
            }

            // 4. 서로 다른 7자리 수의 개수 계산
            int answer = results.size();

            output.append('#').append(tc).append(' ')
                  .append(answer).append('\n');
        }

        System.out.print(output);
    }

    static void bfs(int r, int c) {

        // 탐색 상태: {현재 행, 현재 열, 이동 횟수, 누적 숫자}
        Queue<int[]> queue = new ArrayDeque<>();

        // 시작 칸의 숫자를 포함하고 이동 횟수는 0으로 초기화
        queue.offer(new int[] {r, c, 0, grid[r][c]});

        while (!queue.isEmpty()) {

            // 1. 현재 탐색 상태 추출
            int[] cur = queue.poll();

            int cr = cur[0];
            int cc = cur[1];
            int count = cur[2];
            int number = cur[3];

            // 2. 6번 이동하여 7자리 수가 완성된 경우
            if (count == 6) {
                results.add(number);

                // 현재 경로만 종료하고 나머지 경로는 계속 탐색
                continue;
            }

            // 3. 상하좌우 이동 가능한 모든 경로 탐색
            for (int i = 0; i < 4; i++) {

                int nr = cr + dr[i];
                int nc = cc + dc[i];

                // 격자판을 벗어나는 경우 제외
                if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4) {
                    continue;
                }

                // 4. 현재 상태를 유지하며 새로운 탐색 상태 생성
                int nextCount = count + 1;
                int nextNumber = number * 10 + grid[nr][nc];

                // 5. 다음 탐색 상태를 Queue에 삽입
                // 동일한 격자 칸의 재방문이 가능하므로 visited 불필요
                queue.offer(new int[] {nr, nc, nextCount, nextNumber});
            }
        }
    }
}