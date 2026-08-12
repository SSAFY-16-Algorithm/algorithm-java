import java.io.*;

public class Solution {

	static int N, answer;
	static int[][] board;
	static boolean[][] visited;
	
	static int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static int[] dc = { 0, 0, -1, 1, -1, 1, 1, -1 };
	
	static boolean isValid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
	
	static boolean isPossible(int r, int c) {		
		for (int i = 1; i < N; i++) {
			for (int j = 0; j < 8; j++) {
				int nr = r + dr[j] * i;
				int nc = c + dc[j] * i;
				
				if (!isValid(nr, nc)) continue;
				if (visited[nr][nc]) return false;
			}
		}
		
		return true;
	}
	
	static void backtracking(int r) {
		if (r == N) {
			answer++;
			return;
		}
		
		for (int i = 0; i < N; i++) {
			if (visited[r][i] || !isPossible(r, i)) continue;
			
			visited[r][i] = true;
			backtracking(r + 1);
			visited[r][i] = false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			board = new int[N][N];
			visited = new boolean[N][N];
			answer = 0;
			
			backtracking(0);
			
			sb.append('#').append(t).append(' ').append(answer).append('\n');
		}

		System.out.println(sb);
	}
}
