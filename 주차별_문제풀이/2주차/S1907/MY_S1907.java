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
	static Deque<Pos> collpase;
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
			collpase = new ArrayDeque<>();
			answer = 0;
			boolean isChanged = true;
			for (int r = 0; r < N; r++) {
				char [] row = br.readLine().toCharArray();
				arr[r] = row;
			}
			
			while(isChanged) {
				isChanged = false;
				for (int r = 1; r < N - 1; r++) {
					for (int c = 1; c < M - 1; c++ ) {
						if (arr[r][c] == '.') continue;
						int count = 0;
						for (int d = 0; d < 8; d++) {
							int nx = r + dx[d];
							int ny = c + dy[d];
							if (!isValid(nx, ny)) {
								continue;
							} else if (arr[nx][ny] == '.') {
								count++;
							}
						}
						
						if (arr[r][c] -'0' <= count) {
							isChanged = true;
							collpase.offer(new Pos(r, c));
						}
					}
				}
				if (isChanged) answer++;
				
				// 무너진 곳은 후 반영
				while (!collpase.isEmpty()) {
					Pos col = collpase.poll();
					arr[col.x][col.y] = '.'; 
				}
			}
			sb.append('#').append(t).append(' ').append(answer).append('\n');
		}
		System.out.println(sb);
	}
	
	static boolean isValid(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
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
