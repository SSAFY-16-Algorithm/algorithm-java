import java.io.*;

public class Solution {
	
	static final int N = 16;
	static int[][] board;
	static boolean[][] visited;
	static int sr, sc;
	
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };
	
	static boolean isValid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
	
	static boolean dfs(int r, int c) {
		if (board[r][c] == 3) return true;
		
		for (int i = 0; i < 4; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];
			
			if (!isValid(nr, nc) ||  visited[nr][nc]) continue;
			
			visited[nr][nc] = true;
			if (dfs(nr, nc)) return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int t = 1; t <= 10; t++) {
			board = new int[N][N];
			visited = new boolean[N][N];
			int n = Integer.parseInt(br.readLine());
			
			for (int i = 0; i < N; i++) {
				String str = br.readLine();
				for (int j = 0; j < N; j++) {
					board[i][j] = str.charAt(j) - '0';
					if (board[i][j] == 1) {
                        visited[i][j] = true;
                    } else if (board[i][j] == 2) {
						sr = i;
						sc = j;
                        visited[i][j] = true;
                    }
				}
			}
			
			boolean answer = dfs(sr, sc);
			sb.append('#').append(t).append(' ').append(answer ? 1 : 0).append('\n');
		}

		System.out.println(sb);
	}
}
