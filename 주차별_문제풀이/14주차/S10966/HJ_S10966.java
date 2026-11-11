import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	static int N, M;
	static char[][] map;
	static int[][] distance;

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			map = new char[N][M];
			distance = new int[N][M];

			Queue<int[]> queue = new ArrayDeque<>();

			// 1. 모든 물(W)을 BFS 시작점으로 등록
			for (int r = 0; r < N; r++) {
				map[r] = br.readLine().toCharArray();

				Arrays.fill(distance[r], -1);

				for (int c = 0; c < M; c++) {
					if (map[r][c] == 'W') {
						queue.offer(new int[] { r, c });
						distance[r][c] = 0;
					}
				}
			}

			int answer = 0;

			// 2. 모든 W에서 동시에 BFS 진행
			while (!queue.isEmpty()) {

				int[] current = queue.poll();

				int r = current[0];
				int c = current[1];

				// 상하좌우 탐색
				for (int d = 0; d < 4; d++) {

					int nr = r + dr[d];
					int nc = c + dc[d];

					// 격자 범위를 벗어나면 무시
					if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
						continue;
					}

					// 이미 방문한 칸이면 무시
					if (distance[nr][nc] != -1) {
						continue;
					}

					// 현재 칸까지의 거리 + 1
					distance[nr][nc] = distance[r][c] + 1;

					// 새롭게 방문한 땅의 최소 거리 합산
					answer += distance[nr][nc];

					queue.offer(new int[] { nr, nc });
				}
			}

			output.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.print(output);
	}
}