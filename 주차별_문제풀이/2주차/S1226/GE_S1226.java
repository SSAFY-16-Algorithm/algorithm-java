import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    // 미로 배열
    static int[][] maze;
    // 방문 배열
    static boolean[][] visited;
    // 테스트 케이스
    final static int TC = 10;
    // 미로 사이즈
    final static int N = 16;
    // 방향 배열
    final static int[] dx = {1, 0, -1, 0};
    final static int[] dy = {0, 1, 0, -1};

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for(int test_case = 1; test_case <= TC; test_case++) {
            StringBuilder sb = new StringBuilder("#").append(test_case).append(' ');
            br.readLine();

            maze = new int[N][N];
            visited = new boolean[N][N];

            int[] start = new int[2];
            int[] end = new int[2];

            for(int r = 0; r < N; r++) {
                String rowString = br.readLine();
                maze[r] = new int[N];
                for(int c = 0; c < N; c++) {
                    int rc = rowString.charAt(c) - '0';
                    maze[r][c] = rc;
                    if (rc == 2) {
                        start[0] = r;
                        start[1] = c;
                    } else if (rc == 3) {
                        end[0] = r;
                        end[1] = c;
                    }
                }
            }

            // 출발점부터 BFS 시작
            if (bfs(start, end))
                sb.append(1);
            else
                sb.append(0);

            sb.append('\n');
            bw.append(sb).flush();
        }
        br.close();
        bw.close();
    }

    // BFS
    static boolean bfs(int[] start, int[] end) {
        Queue<int[]> q = new LinkedList<>();

        q.offer(start);
        visited[start[0]][start[1]] = true;

        while(!q.isEmpty()) {
            int[] current = q.poll();

            if (current[0] == end[0] && current[1] == end[1])
                return true;

            // visited & maze 체크 -> offer & true
            for (int dir = 0; dir < 4; dir++) {
                int nextR = current[0] + dx[dir];
                int nextC = current[1] + dy[dir];

                // 범위 계산
                if (nextR < 0 || nextC < 0 || nextR >= N || nextC >= N)
                    continue;

                // 방문하지 않은 길(0, 3)이면 탐색
                if ((maze[nextR][nextC] == 0 || maze[nextR][nextC] == 3) && !visited[nextR][nextC]) {
                    q.offer(new int[]{nextR, nextC});
                    visited[nextR][nextC] = true;
                }
            }
        }

        return false;
    }
}