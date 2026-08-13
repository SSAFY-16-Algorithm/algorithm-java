package solution;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class WH_S7733 {

    public static int N;
    public static int[][] cheese;
    public static int[] dr = {-1, 1, 0, 0};
    public static int[] dc = {0, 0, 1, -1};
    public static int answer;

    public static void eatCheese(int day) {
        // day일차에는 당도 day인 치즈를 먹음
        // 먹어서 없어진 곳은 0으로 만들어줌
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (cheese[r][c] == day) {
                    cheese[r][c] = 0;
                }
            }
        }
    }

    public static void countCheese() {
        boolean[][] visited = new boolean[N][N];
        int cnt = 0; // 덩어리 개수

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {

                if (cheese[r][c] != 0 && !visited[r][c]) {
                    cnt++;

                    Queue<int[]> cheeseRoute = new ArrayDeque<>();
                    cheeseRoute.offer(new int[]{r, c});
                    visited[r][c] = true;

                    while (!cheeseRoute.isEmpty()) {
                        int[] cc = cheeseRoute.poll();

                        for (int idx = 0; idx < 4; idx++) {
                            int nr = cc[0] + dr[idx];
                            int nc = cc[1] + dc[idx];

                            if (0 <= nr && nr < N && 0 <= nc && nc < N) {

                                if (cheese[nr][nc] != 0 && !visited[nr][nc]) {
                                    visited[nr][nc] = true;
                                    cheeseRoute.offer(new int[]{nr, nc});
                                }
                            }
                        }
                    }
                }
            }
        }

        // 현재 날짜의 덩어리 수와 최대 덩어리 수 비교
        if (cnt > answer) {
            answer = cnt;
        }
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        int T = scan.nextInt();

        for (int t = 1; t <= T; t++) {

            N = scan.nextInt();

            // 치즈 입력 받기
            cheese = new int[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    cheese[i][j] = scan.nextInt();
                }
            }

            // 0일차: 치즈가 한 덩어리라고 가정
            answer = 1;

            // 1일 ~ 100일까지 반복
            for (int i = 1; i <= 100; i++) {
                eatCheese(i);
                countCheese();
            }

            System.out.println("#" + t + " " + answer);
        }
    }
}