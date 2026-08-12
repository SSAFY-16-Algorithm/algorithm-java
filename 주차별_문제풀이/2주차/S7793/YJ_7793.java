import java.io.*;
import java.util.*;

public class Solution {

	static int N, M, SR, SC, DR, DC;
	static char[][] board;
	static Deque<int[]> dq, temp;
	static boolean[][] visited;

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	static boolean isValid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < M;
	}

	static int solve() {
		dq = new ArrayDeque<>();
		visited = new boolean[N][M];

		dq.offer(new int[] { SR, SC, 0 });
		visited[SR][SC] = true;

		while (true) {
			devilHand();
			int result = move();
			
			if (result == -1) return -1;
			if (result >= 0) return result;
		}
	}

	/**
	 * 악마의 손아귀 확장
	 */
	static void devilHand() {
		Deque<int[]> devils = new ArrayDeque<>();
		boolean[][] visited = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (board[i][j] == '*') {
					devils.offer(new int[] { i, j });
				}
			}
		}

		while (!devils.isEmpty()) {
			int[] cur = devils.poll();

			for (int d = 0; d < 4; d++) {
				int nr = cur[0] + dr[d];
				int nc = cur[1] + dc[d];

				if (!isValid(nr, nc) || visited[nr][nc] || board[nr][nc] == 'D' || board[nr][nc] == 'X') continue;

				board[nr][nc] = '*';
				visited[nr][nc] = true;
			}
		}
	}

	static int move() {
		temp = new ArrayDeque<>();
		
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			
			if (cur[0] == DR && cur[1] == DC) return cur[2];
			
			for (int d = 0; d < 4; d++) {
				int nr = cur[0] + dr[d];
				int nc = cur[1] + dc[d];
				int nd = cur[2] + 1;
				
				if (!isValid(nr, nc) || visited[nr][nc] || board[nr][nc] == 'X' || board[nr][nc] == '*') continue;
				
				temp.offer(new int[] { nr, nc, nd });
				visited[nr][nc] = true;
			}
		}
		
		dq = new ArrayDeque<>(temp);
		
		return temp.isEmpty() ? -1 : -2;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;

		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			board = new char[N][M]; // .: 평범한 지역, S: 수연이의 위치, D: 여신의 공간, X: 돌의 위치, *: 악마

			for (int i = 0; i < N; i++) {
				String line = br.readLine();
				for (int j = 0; j < M; j++) {
					board[i][j] = line.charAt(j);

					if (board[i][j] == 'S') {
						SR = i;
						SC = j;
					} else if (board[i][j] == 'D') {
						DR = i;
						DC = j;
					}
				}
			}
			
			int answer = solve();

			sb.append('#').append(t).append(' ').append(answer == -1 ? "GAME OVER" : answer).append('\n');
		}

		System.out.println(sb);
	}
}
