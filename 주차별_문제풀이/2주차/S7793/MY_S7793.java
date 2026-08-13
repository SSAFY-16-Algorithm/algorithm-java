import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class MY_S7793 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static char[][] arr;
	static int N;
	static int M;
	static int answer;
	static int[] dx = { -1, 0, 0, 1 };
	static int[] dy = { 0, -1, 1, 0 };
	static boolean[][] visited;
	static int[][] demonGrasp;

	static Pos devil;
	static Pos God;
	static Pos me;
	static Deque<Pos> q;
	static Deque<Pos> dq;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/S7793/input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			arr = new char[N][M];
			visited = new boolean[N][M];
			demonGrasp = new int[N][M];
			q = new ArrayDeque<>();
			dq = new ArrayDeque<>();
			answer = Integer.MAX_VALUE;

			for (int r = 0; r < N; r++) {
				arr[r] = br.readLine().toCharArray();

				for (int c = 0; c < M; c++) {
					char cur = arr[r][c];
					demonGrasp[r][c] = -1;

					if (cur == 'D') {
						God = new Pos(-1, r, c);
					} else if (cur == 'S') {
						me = new Pos(0, r, c);
					} else if (cur == '*') {
						demonGrasp[r][c] = 0;
						dq.offer(new Pos(0, r, c));
					} else if (cur == 'X') {
						// X 관련 처리
					}
				}
			}

			// 수연이는 S
			// 여신의 공간은 D
			// 돌의 위치 X
			// 악마 *
			demonicGrasp();
			calculateLeastTime();

			if (answer == Integer.MAX_VALUE) {
				sb.append('#').append(t).append(' ').append("GAME OVER").append('\n');
				continue;
			}
			sb.append('#').append(t).append(' ').append(answer).append('\n');
		}
		System.out.println(sb);
	}

	static void calculateLeastTime() {
		q.offer(me);

		while (!q.isEmpty()) {
			Pos curMe = q.poll();
			int x = curMe.x;
			int y = curMe.y;
			int time = curMe.time;
			// 신을 만나면 끝
			if (x == God.x && y == God.y) {
				answer = Math.min(answer, curMe.time);
				break;
			}

			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				if (!isValid(nx, ny))
					continue;
				if (arr[nx][ny] == '.' && !visited[nx][ny]
						&& (demonGrasp[nx][ny] == -1 || time + 1 < demonGrasp[nx][ny]) || arr[nx][ny] == 'D') {

					q.offer(new Pos(time + 1, nx, ny));
					visited[nx][ny] = true;
				}
			}
		}
	}

	static void demonicGrasp() {
		while (!dq.isEmpty()) {
			Pos curDem = dq.poll();
			int x = curDem.x;
			int y = curDem.y;

			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				if (!isValid(nx, ny) || demonGrasp[nx][ny] != -1)
					continue;

				// 전부 큐에 넣지말고 확장가능한 악마 영역만 넣기
				if (arr[nx][ny] == '.' || arr[nx][ny] == 'S') {
					dq.offer(new Pos(curDem.time + 1, nx, ny));
					demonGrasp[nx][ny] = curDem.time + 1;
				}
			}
		}

	}

	static boolean isValid(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
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
