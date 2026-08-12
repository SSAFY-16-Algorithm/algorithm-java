package swea.d4.p7733;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	// 멤버 변수
	static int N;
	static int[][] map;
	static boolean[][] visited;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static void dfs(int r, int c, int day) {
		visited[r][c] = true;
		for (int d = 0; d < 4; d++) {
			int nr = r + dr[d];
			int nc = c + dc[d];
			
			// 1. 배열 범위를 벗어나면 패스
            if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
                continue;
            }

            // 2. 이미 방문한 칸이면 패스
            if (visited[nr][nc]) {
                continue;
            }

            // 3. 이미 먹힌 치즈면 패스
            if (map[nr][nc] <= day) {
                continue;
            }

            // 아직 남아 있는 연결된 치즈 탐색
            dfs(nr, nc, day);
		}
	}

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        
        for (int testCase = 1; testCase <= T; testCase++) {
        	
            // 입력 처리
        	N = Integer.parseInt(br.readLine());
        	map = new int[N][N];
        	for (int r = 0; r < N; r++) {
        		StringTokenizer st = new StringTokenizer(br.readLine());
        		for (int c = 0; c < N; c++) {
        			map[r][c] = Integer.parseInt(st.nextToken());
        		}
        	}
            // 문제 풀이
        	int maxCount = 0;

            // day = 0 : 아무 치즈도 먹히기 전
            // day = 1~100 : 해당 날짜까지 먹힌 상태
            for (int day = 0; day <= 100; day++) {

                visited = new boolean[N][N];

                int count = 0;

                // 모든 칸 확인
                for (int r = 0; r < N; r++) {
                    for (int c = 0; c < N; c++) {

                        // 아직 먹히지 않았고
                        // 아직 방문하지 않은 칸이라면
                        if (map[r][c] > day && !visited[r][c]) {

                            // 새로운 치즈 덩어리 발견
                            count++;

                            // 이 덩어리에 연결된 모든 칸 방문
                            dfs(r, c, day);
                        }
                    }
                }

                // 가장 많은 덩어리 개수 저장
                maxCount = Math.max(maxCount, count);
            }
    
            // 결과 저장
            sb.append("#").append(testCase).append(" ").append(maxCount).append("\n");
        }
        System.out.print(sb);
    }
}