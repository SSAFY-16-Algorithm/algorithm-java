import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class MY_S4193 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static int N, result, startX, startY, endX, endY;
	static int[][] arr;
	static boolean[][][] visited;
	static int[] dx = { -1, 0, 0, 1 };
	static int[] dy = { 0, -1, 1, 0 };
	static Deque<Pos> q;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/S4193/sample.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			arr = new int[N][N];
			// 시간까지 같이 방문처리하는게 중요함
			visited = new boolean[N][N][3];
			q = new ArrayDeque<>();
			result = Integer.MAX_VALUE;

			for (int r = 0; r < N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c = 0; c < N; c++) {
					arr[r][c] = Integer.parseInt(st.nextToken());
				}
			}

			st = new StringTokenizer(br.readLine());
			startX = Integer.parseInt(st.nextToken());
			startY = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(br.readLine());
			endX = Integer.parseInt(st.nextToken());
			endY = Integer.parseInt(st.nextToken());

			calculateLeastDistance();

			if (result == Integer.MAX_VALUE)
				result = -1;
			sb.append('#').append(t).append(' ').append(result).append('\n');
		}

		System.out.println(sb);
	}

	static void calculateLeastDistance() {
		q.offer(new Pos(0, startX, startY));

		while (!q.isEmpty()) {
			Pos p = q.poll();
			int time = p.time;
			int x = p.x;
			int y = p.y;

			if (x == endX && y == endY) {
				result = Math.min(time, result);
				continue;
			}

			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];

				// 없는 좌표 값, 장애물은 패스
				if (!isValid(nx, ny) || arr[nx][ny] == 1 || visited[nx][ny][(time + 1) % 3]) {
					continue;
				}
				// 소용돌이인데 시간 % 3이 나머지 2가 아니면 패스
				else if (arr[nx][ny] == 2 && time % 3 != 2) {
					q.offer(new Pos(time + 1, x, y));
					continue;
				}
				// 소용돌이를 지나갈 수 있거나 0인 부분만 큐에 넣기
				q.offer(new Pos(time + 1, nx, ny));
				visited[nx][ny][(time + 1) % 3] = true;
			}
		}
	}

	static boolean isValid(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}

	static class Pos {
		int time;
		int x;
		int y;

		Pos(int t, int x, int y) {
			this.time = t;
			this.x = x;
			this.y = y;
		}
	}
}
