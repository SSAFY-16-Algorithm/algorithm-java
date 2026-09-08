package programmers.lv3.p67259;

import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {

    static final int INF = 1_000_000_000;

    // 0: 위, 1: 아래, 2: 왼쪽, 3: 오른쪽
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board) {

        int N = board.length;

        // cost[r][c][d]
        // = (r, c)에 d 방향으로 도착했을 때의 최소 비용
        int[][][] cost = new int[N][N][4];

        // 처음에는 아직 어떤 경로도 발견하지 않았으므로
        // 충분히 큰 값으로 초기화
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                Arrays.fill(cost[r][c], INF);
            }
        }

        // 출발점은 비용 0
        // 다시 출발점으로 돌아오는 불필요한 경로를 막기 위해
        // 네 방향 모두 0으로 초기화
        for (int d = 0; d < 4; d++) {
            cost[0][0][d] = 0;
        }

        /*
         * 상태:
         * [0] = row
         * [1] = col
         * [2] = direction
         * [3] = cost
         *
         * 비용이 작은 상태부터 꺼낸다.
         */
        PriorityQueue<int[]> pq =
                new PriorityQueue<>(
                        (a, b) -> Integer.compare(a[3], b[3])
                );

        // 출발점에서는 아직 이전 진행 방향이 없으므로 direction = -1
        pq.offer(new int[]{0, 0, -1, 0});

        while (!pq.isEmpty()) {

            int[] current = pq.poll();

            int r = current[0];
            int c = current[1];
            int direction = current[2];
            int currentCost = current[3];

            // 이미 더 싼 비용으로 같은 상태를 방문했다면
            // 현재 상태는 볼 필요 없음
            if (direction != -1
                    && currentCost > cost[r][c][direction]) {
                continue;
            }

            // 현재 위치에서 상하좌우 탐색
            for (int nextDirection = 0;
                 nextDirection < 4;
                 nextDirection++) {

                int nr = r + dr[nextDirection];
                int nc = c + dc[nextDirection];

                // 배열 범위를 벗어나면 이동 불가
                if (nr < 0 || nr >= N
                        || nc < 0 || nc >= N) {
                    continue;
                }

                // 벽이면 이동 불가
                if (board[nr][nc] == 1) {
                    continue;
                }

                int nextCost;

                /*
                 * 출발점의 첫 이동
                 * 또는 기존 방향과 같은 방향으로 이동
                 *
                 * 직선 도로 하나: +100
                 */
                if (direction == -1
                        || direction == nextDirection) {

                    nextCost = currentCost + 100;

                /*
                 * 방향이 바뀌는 경우
                 *
                 * 직선 도로: +100
                 * 코너:     +500
                 * 총:       +600
                 */
                } else {

                    nextCost = currentCost + 600;
                }

                /*
                 * 같은 위치 + 같은 방향으로 도착하는
                 * 기존 비용보다 더 싸게 갈 수 있다면 갱신
                 */
                if (nextCost
                        < cost[nr][nc][nextDirection]) {

                    cost[nr][nc][nextDirection] = nextCost;

                    pq.offer(new int[]{
                            nr,
                            nc,
                            nextDirection,
                            nextCost
                    });
                }
            }
        }

        // 도착점에 어떤 방향으로 들어오는 것이 가장 싼지 확인
        int result = INF;

        for (int d = 0; d < 4; d++) {
            result = Math.min(
                    result,
                    cost[N - 1][N - 1][d]
            );
        }

        return result;
    }
}