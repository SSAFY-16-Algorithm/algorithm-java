import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Solution {
	private static final int[] DR = {1, 0, -1, 0};
	private static final int[] DC = {0, 1, 0, -1};
	
	private static int N;
	private static int[][] cheese;
	private static boolean[][] visited;
	
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		
		StringBuilder sb = new StringBuilder();
		
		for (int test_case = 1; test_case <= T; test_case++) {
			sb.setLength(0);
			
			N = Integer.parseInt(br.readLine().trim());
			cheese = new int[N][N];
			
			int maxScore = 0;
			
			for (int r = 0; r < N; r++) {
				StringTokenizer tokenizer = new StringTokenizer(br.readLine());
				for (int c = 0; c < N; c++) {
					int score = Integer.parseInt(tokenizer.nextToken());
					cheese[r][c] = score;
					maxScore = Math.max(maxScore, score);
				}
			}
			
			int maxCount = 1;
			
			for (int day = 1; day <= maxScore; day++) {
				visited = new boolean[N][N];
				int count = 0;
				
				for (int r = 0; r < N; r++) {
					for (int c = 0; c < N; c++) {
						if (cheese[r][c] <= day || visited[r][c])
							continue;
						
						count++;
						dfs(r, c, day);
					}
				}
				
				maxCount = Math.max(maxCount, count);
			}
			sb.append('#').append(test_case).append(' ').append(maxCount).append('\n');
			
			bw.write(sb.toString());
			bw.flush();
		}
		
		br.close();
		bw.close();
	}
	
	private static void dfs(int row, int col, int currentDay) {
		visited[row][col] = true;
		
		for (int i = 0; i < 4; i++) {
			int nextR = row + DR[i];
			int nextC = col + DC[i];
			
			if (nextR < 0 || nextC < 0 || nextR >= N || nextC >= N)
				continue;
			
			if (cheese[nextR][nextC] <= currentDay || visited[nextR][nextC])
				continue;
			
			dfs(nextR, nextC, currentDay);
		}
	}
}