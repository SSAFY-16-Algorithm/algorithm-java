import java.io.*;
import java.util.*;

public class Solution {
	
	static int N, A, B, C, D;
	static int[][] map;
	
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };
	
	static boolean isValid(int r, int c) {
		 return r >= 0 && r < N && c >= 0 && c < N;
	}
	
	static int move() {
		int result = 0;
		Deque<int[]> dq = new ArrayDeque<>();
		boolean[][] visited = new boolean[N][N];
		dq.offer(new int[] { A, B, 0 });
		visited[A][B] = true;
		
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			
			if (cur[0] == C && cur[1] == D) return cur[2];
			
			for (int d = 0; d < 4; d++) {
				int nr = cur[0] + dr[d];
				int nc = cur[1] + dc[d];
				int nd = cur[2] + 1;
				
				if (!isValid(nr, nc) || visited[nr][nc] || map[nr][nc] == 1) continue;
				if (map[nr][nc] == 2 && nd % 3 != 0) {
					dq.offer(new int[] { cur[0], cur[1], nd });
					continue;
				}
				
				dq.offer(new int[] { nr, nc, nd });
				visited[nr][nc] = true;
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			st = new StringTokenizer(br.readLine());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			C = Integer.parseInt(st.nextToken());
			D = Integer.parseInt(st.nextToken());
			
			sb.append('#').append(t).append(' ').append(move()).append('\n');
		}

		System.out.println(sb);
	}
}
