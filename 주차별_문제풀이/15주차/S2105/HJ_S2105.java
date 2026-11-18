import java.io.*;
import java.util.*;

public class Solution {

    static int N;
    static int[][] cafes;
    static boolean[] eaten;
    static int answer;

    // 대각선 이동: 우하 -> 좌하 -> 좌상 -> 우상
    static int[] dr = {1, 1, -1, -1};
    static int[] dc = {1, -1, -1, 1};

    static void dfs(int startR, int startC,
                    int r, int c, int dir, int count) {

        // 현재 방향으로 계속 가거나 다음 방향으로 꺾기
        for (int nextDir = dir;
             nextDir <= dir + 1 && nextDir < 4;
             nextDir++) {

            int nr = r + dr[nextDir];
            int nc = c + dc[nextDir];

            // 격자 범위를 벗어나면 이동 불가
            if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
                continue;
            }

            // 출발점으로 다시 돌아왔는지 확인
            if (nr == startR && nc == startC) {

                // 네 방향을 모두 사용하여 사각형을 완성한 경우
                if (nextDir == 3 && count >= 4) {
                    answer = Math.max(answer, count);
                }

                continue;
            }

            int dessert = cafes[nr][nc];

            // 이미 먹은 디저트 종류라면 이동 불가
            if (eaten[dessert]) {
                continue;
            }

            // 1. 현재 디저트 선택
            eaten[dessert] = true;

            // 2. 다음 카페 탐색
            dfs(startR, startC, nr, nc, nextDir, count + 1);

            // 3. 선택 취소 (백트래킹)
            eaten[dessert] = false;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {

            N = Integer.parseInt(br.readLine());

            cafes = new int[N][N];

            // 카페 정보 입력
            for (int r = 0; r < N; r++) {

                StringTokenizer st =
                    new StringTokenizer(br.readLine());

                for (int c = 0; c < N; c++) {
                    cafes[r][c] =
                        Integer.parseInt(st.nextToken());
                }
            }

            // 테스트 케이스별 상태 초기화
            answer = -1;
            eaten = new boolean[101];

            // 모든 카페를 출발점으로 선택
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {

                    // 출발 카페의 디저트 방문 처리
                    eaten[cafes[r][c]] = true;

                    // DFS 탐색 시작
                    dfs(r, c, r, c, 0, 1);

                    // 다음 출발점을 위해 상태 복원
                    eaten[cafes[r][c]] = false;
                }
            }

            sb.append("#")
              .append(t)
              .append(" ")
              .append(answer)
              .append("\n");
        }

        System.out.print(sb);
    }
}