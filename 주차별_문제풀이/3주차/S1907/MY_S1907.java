import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class MY_S1907 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static char[][] arr;
	static int[][] remain;
	static Deque<Pos> cq;
	static int N;
	static int M;
	static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
	static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
	static int answer;
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/S1907/sample_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			arr = new char[N][M];
			remain = new int[N][M];
			cq = new ArrayDeque<>();
			answer = 0;
			for (int r = 0; r < N; r++) {
				char [] row = br.readLine().toCharArray();
				arr[r] = row;
				for (int c = 0; c < M; c++) {
					remain[r][c] = -1;
					if (arr[r][c] == '.') continue;
					remain[r][c] = arr[r][c] - '0';
				}
			}
			// 주변 . 갯수 세고 무너질 에정인 경우에만 큐에 넣기
			investNearby();
			// remain == 0이 나오지 않을 때 까지 큐 돌기
			collapseSandCastle();
			
			sb.append('#').append(t).append(' ').append(answer).append('\n');
		}
		System.out.println(sb);
	}
	
	static void collapseSandCastle() {
		while(!cq.isEmpty()) {
			Pos p = cq.poll();
			int x = p.x;
			int y = p.y;
			answer = p.time;
			
			for (int d = 0; d < 8; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				if (!isValid(nx, ny) || remain[nx][ny] == -1) continue;
				remain[nx][ny]--;
				if (remain[nx][ny] == 0) cq.offer(new Pos(p.time + 1, nx, ny));
			}
		}
	}
	
	static void investNearby() {
		for (int i = 1; i < N - 1; i++) {
			for (int j = 1; j < M - 1; j++) {
				int count = 0;
				if (arr[i][j] == '.') continue;
				for (int d = 0; d < 8; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					if (!isValid(nx,ny)) continue;
					if (arr[nx][ny] == '.') count++;
				}
				remain[i][j] = Math.max(0, arr[i][j] -'0' - count);
				if (remain[i][j] == 0) cq.offer(new Pos(1, i, j));
			}
		}
	}
	
	static boolean isValid(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
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
