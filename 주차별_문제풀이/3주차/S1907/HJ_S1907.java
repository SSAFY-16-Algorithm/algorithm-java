package swea.d5.p1907;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	
	static int H;
	static int W;
	
	static char[][] map;
	static int[][] emptyCount;
	
	static boolean[][] visited;
	static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dc = {-1,  0,  1,-1, 1,-1, 0, 1};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine().trim());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			
			map = new char[H][W];
			emptyCount = new int[H][W];
			visited = new boolean[H][W];
			
			Queue<int[]> queue = new ArrayDeque<>();
			
			// 모래성 배열 입력
			for (int r = 0; r < H; r++) {
				map[r] = br.readLine().toCharArray();
			}
			
			// 무너질 모래 찾기
			for (int r= 0; r < H; r++) {
				for (int c=0; c < W; c++) {
					if (map[r][c] == '.') {
						continue;
					}
					
					int count = 0;
					
					for (int d = 0; d < 8; d++) {
						int nr = r + dr[d];
						int nc = c + dc[d];
						
						if (nr < 0 || nr >= H || nc < 0 || nc >= W) {
							continue;
						}
						if (map[nr][nc] == '.') {
							count++;
						}
					}
					
					emptyCount[r][c] = count;
					int strength = map[r][c] - '0';
					if (emptyCount[r][c] >= strength) {
						queue.offer(new int[] {r,c});
						visited[r][c] = true;
					}
				}	
			}
			
			int wave = 0;
			
			while(!queue.isEmpty()) {
				int size = queue.size();
				
				for (int i = 0; i<size; i++) {
					int[] cur = queue.poll();
					int r = cur[0];
					int c = cur[1];
					
					map[r][c] = '.';
					
					for (int d = 0; d < 8; d++) {
						int nr = r + dr[d];
						int nc = c + dc[d];
						
						if (nr < 0 || nr >= H || nc < 0 || nc >= W) {
							continue;
						}
						
						if (map[nr][nc]=='.') {
							continue;
						}
						
						emptyCount[nr][nc]++;
						int strength = map[nr][nc] - '0';
						
						if (!visited[nr][nc] && emptyCount[nr][nc] >= strength) {
							queue.offer(new int[] {nr, nc});
							visited[nr][nc] = true;
						}
					}
				}
				wave++;
			}
			
			sb.append('#').append(testCase).append(" ").append(wave).append("\n");
		}
		System.out.println(sb);
	}
}