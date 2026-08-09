import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class MY_S7733 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	static int result;
	static int N;
	static int[][] arr;
	static boolean[][] visited;
	static int[] dx = {-1, 0, 0, 1};
	static int[] dy = {0, -1, 1, 0};
	static Deque<Pos> q;
	
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/S7733/input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			arr = new int[N][N];
			result = 0;
			
			for (int r = 0; r < N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c = 0; c < N; c++) {
					arr[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			// queue랑 재귀함수 중에 뭐가 더 나을까?
			// 그냥 큐 구현이 더 쉬울 거 같음
			for (int day = 0; day <= 100; day++) { // 1~100일까지 검사
				int dayResult = 0;
				visited = new boolean[N][N]; // 먹혔거나 탐색했으면 True
				for (int r = 0; r < N; r++) {
					for (int c = 0; c < N; c++) {
						if (visited[r][c]) continue;
						// day일에 r, c 위치의 덩어리 계산 및 visited 처리
						if (arr[r][c] <= day) {
							visited[r][c] = true;
							continue;
						}
						dayResult += countCheese(day, r, c);
					}
				}
				result = Math.max(result, dayResult);
			}
			
			sb.append('#').append(t).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	
	static int countCheese(int day, int r, int c) {
		q = new ArrayDeque<>();
		q.offer(new Pos(r, c));
		visited[r][c] = true;
		while(!q.isEmpty()) {
			Pos p = q.poll();
			int x = p.x;
			int y = p.y;
			
			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				if (!isValid(nx, ny) || visited[nx][ny]) {
					continue;
				} else if (arr[nx][ny] <= day) {
					visited[nx][ny] = true;
					continue;
				} else {
					q.offer(new Pos(nx, ny));
					visited[nx][ny] = true;
				}
			}
		}
		return 1;
	}
	
	static boolean isValid(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}
	
	static class Pos {
		int x;
		int y;
		Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
