import java.io.*;
import java.util.*;

public class Solution {
	
	static final int MAX_DAY = 100;
	static int N;
	static int[][] map;
	static boolean[][] ate;
	
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };
	
	static boolean isValid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
	
	static void eat(int day) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == day) {
					ate[i][j] = true;
				}
			}
		}
	}
	
	static int count() {
		int result = 0;
		Deque<int[]> dq = new ArrayDeque<>();
		boolean[][] visited = new boolean[N][N];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (ate[i][j] || visited[i][j]) continue;
				
				dq.offer(new int[] { i, j });
				visited[i][j] = true;
				
				while (!dq.isEmpty()) {
					int[] cur = dq.poll();
					
					for (int d = 0; d < 4; d++) {
						int nr = cur[0] + dr[d];
						int nc = cur[1] + dc[d];
						
						if (!isValid(nr, nc) || visited[nr][nc] || ate[nr][nc]) continue;
						
						dq.offer(new int[] { nr, nc });
						visited[nr][nc] = true;
					}
				}
				
				result++;
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			ate = new boolean[N][N];
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int answer = 0;
			for (int i = 0; i <= MAX_DAY; i++) {
				eat(i);
				answer = Math.max(answer, count());
			}
			
			sb.append('#').append(t).append(' ').append(answer).append('\n');
		}

		System.out.println(sb);
	}
}
