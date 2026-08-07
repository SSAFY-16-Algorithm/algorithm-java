import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MY_S1226 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	static final int[] dx = {-1, 0, 0, 1};
	static final int[] dy = {0, -1, 1, 0};
	static final int N = 16;
	
	static int result;
	static int[][] maze; // visited 대신 -1 사용
	static int startX, startY, endX, endY;
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/S1226/input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		final int T = 10;
		for (int t = 1; t <= T; t++) {
			int test_case = Integer.parseInt(br.readLine());
			maze = new int[N][N];
			// 미로 입력
			for (int i = 0; i < N; i++) {
				char[] row = br.readLine().toCharArray();
				for (int j = 0; j < N; j++) {
					maze[i][j] = row[j] - '0';
					if (maze[i][j] == 2) {
						startX = i; startY = j;
					}
					else if (maze[i][j] == 3) {
						endX = i; endY = j;
					}
				}
			}
			// 정답값 초기화
			result = 0;
			
			// dfs 호출
			findRoute(startX, startY);
			sb.append("#").append(test_case).append(" ").append(result).append('\n');
		}
		System.out.println(sb);
	}
	
	static void findRoute(int x, int y) {
		// 가지치기
	    if (result == 1) return;
	    
		maze[x][y] = -1;
		
		// 가지치기
		if (x == endX && y == endY) {
			result = 1;
			return;
		}
		
		// 4방향 탐색
		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			// 유효하지 않은 좌표거나 - 그럴 일이 없을 것 같지만
			// 장애물(1)을 만나면 무시
			// 이미 간 곳(-1)이어도 무시
			if (!isValid(nx, ny) || maze[nx][ny] == 1 || maze[nx][ny] == -1) {
				continue;
			} else if (maze[nx][ny] == 0 || maze[nx][ny] == 3) {
				findRoute(nx, ny);
			}
		}
	}
	
	static boolean isValid(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}
}
