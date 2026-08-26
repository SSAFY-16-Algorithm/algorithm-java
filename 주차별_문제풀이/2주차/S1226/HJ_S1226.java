package swea.d4.p1226;

import java.io.*;
import java.util.*;

public class Solution {

    static final int N = 16;

    static int[][] maze;
    static boolean[][] visited;

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, 1, -1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int t = 0; t < 10; t++) {

            int tc = Integer.parseInt(br.readLine());

            maze = new int[N][N];

            int startY = 0;
            int startX = 0;

            // 미로 입력
            for (int y = 0; y < N; y++) {

                String line = br.readLine();

                for (int x = 0; x < N; x++) {

                    maze[y][x] = line.charAt(x) - '0';

                    // 시작점 찾기
                    if (maze[y][x] == 2) {
                        startY = y;
                        startX = x;
                    }
                }
            }

            int result = bfs(startY, startX);

            System.out.println("#" + tc + " " + result);
        }
    }

    static int bfs(int startY, int startX) {

        visited = new boolean[N][N];

        Queue<int[]> queue = new ArrayDeque<>();

        // 시작점 Queue에 저장 + 방문 처리
        queue.offer(new int[]{startY, startX});
        visited[startY][startX] = true;

        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int y = current[0];
            int x = current[1];

            // 상하좌우 확인
            for (int d = 0; d < 4; d++) {

                int ny = y + dy[d];
                int nx = x + dx[d];

                // 배열 범위를 벗어나면 이동 불가
                if (ny < 0 || ny >= N || nx < 0 || nx >= N) {
                    continue;
                }

                // 벽이면 이동 불가
                if (maze[ny][nx] == 1) {
                    continue;
                }

                // 이미 방문한 곳이면 이동하지 않음
                if (visited[ny][nx]) {
                    continue;
                }

                // 도착점 발견
                if (maze[ny][nx] == 3) {
                    return 1;
                }

                // 이동 가능한 길
                visited[ny][nx] = true;
                queue.offer(new int[]{ny, nx});
            }
        }

        // 갈 수 있는 모든 곳을 확인했지만 도착점에 못 감
        return 0;
    }
}