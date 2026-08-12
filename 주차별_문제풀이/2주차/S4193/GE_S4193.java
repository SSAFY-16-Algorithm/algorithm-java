import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
	private static final int[] DR = {1, 0, -1, 0};
	private static final int[] DC = {0, 1, 0, -1};
	
	private static int N;
	private static int[][] sea;
	private static boolean[][][] visited;
	private static int[] end;
	
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		
		StringBuilder sb = new StringBuilder();
		StringTokenizer tokenizer;
		
		for (int test_case = 1; test_case <= T; test_case++) {
			sb.setLength(0);
			
			N = Integer.parseInt(br.readLine().trim());
			sea = new int[N][N];
			visited = new boolean[N][N][3];
			
			for (int r = 0; r < N; r++) {
				tokenizer = new StringTokenizer(br.readLine());
				for (int c = 0; c < N; c++) {
					sea[r][c] = Integer.parseInt(tokenizer.nextToken());
				}
			}
			
			int[] start = new int[2];
			tokenizer = new StringTokenizer(br.readLine());
			for (int i = 0; i < 2; i++) {
				start[i] = Integer.parseInt(tokenizer.nextToken());
			}
			
			end = new int[2];
			tokenizer = new StringTokenizer(br.readLine());
			for (int i = 0; i < 2; i++) {
				end[i] = Integer.parseInt(tokenizer.nextToken());
			}
			
			sb.append('#').append(test_case).append(' ').append(bfs(start[0], start[1])).append('\n');
			
			bw.write(sb.toString());
			bw.flush();
		}
		
		br.close();
		bw.close();
	}
	
	private static int bfs(int startR, int startC) {
		Queue<int[]> q = new LinkedList<>();
		visited[startR][startC][0] = true;
		q.offer(new int[] {startR, startC, 0});
		
		while (!q.isEmpty()) {
			int[] current = q.poll();
			int currentR = current[0];
			int currentC = current[1];
			int currentTime = current[2];
			
			// 도착
			if (currentR == end[0] && currentC == end[1])
				return currentTime;
			
			int nextTime = currentTime + 1;
			int nextTimeMod = nextTime % 3;
			
			boolean wait = false;
			
			for (int i = 0; i < 4; i++) {
				int nextR = currentR + DR[i];
				int nextC = currentC + DC[i];
				
				// 벽
				if (nextR < 0 || nextC < 0 || nextR >= N || nextC >= N)
					continue;
				
				// 섬
				if(sea[nextR][nextC] == 1)
					continue;
				
				// 소용돌이 존재 (wait)
				if (sea[nextR][nextC] == 2 && currentTime % 3 != 2) {
					wait = true;
					continue;
				}
				
				// 소용돌이 상태가 같을 때 방문한 적 있는지 확인
				if(!visited[nextR][nextC][nextTimeMod]) {
					visited[nextR][nextC][nextTimeMod] = true;
					q.offer(new int[] {nextR, nextC, nextTime});
				}
			}
			
			// 소용돌이 상태가 같을 때 현재 위치에서 대기한 적 있는지 확인
			if (wait && !visited[currentR][currentC][nextTimeMod]) {
				visited[currentR][currentC][nextTimeMod] = true;
				q.offer(new int[] {currentR, currentC, nextTime});
			}
			
		}
		
		return -1;
	}
}