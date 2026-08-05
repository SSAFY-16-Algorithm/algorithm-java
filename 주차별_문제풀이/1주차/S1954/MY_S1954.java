import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MY_S1954 {
	static BufferedReader br;
	static StringTokenizer tk;
	static StringBuilder sb;
	static int result;
	static int N;
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };
	static int count;
	static int[][] arr;
	public static void main(String[] args) throws Exception {
		// System.setIn(new FileInputStream("res/1954/input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			arr = new int[N][N];
			count = 0;
			int idx = 0;
			int x = 0, y = 0;
		
			while (count < N*N) {
				arr[x][y] = ++count;
				int nx = x + dx[idx];
				int ny = y + dy[idx];
				if (!isValid(nx, ny) || arr[nx][ny] != 0) {
					idx++;
					idx %= 4;
					nx = x + dx[idx];
					ny = y + dy[idx];
				}
				x = nx;
				y = ny;
			}
			
			sb.append("#")
				.append(test_case)
				.append(" \n");
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					sb.append(arr[i][j])
						.append(" ");
				}
				sb.append("\n");
			}
		}
		System.out.println(sb);
	}
	
	static boolean isValid(int x, int y) {
		return (x >= 0 && x < N && y >=0 && y < N);
	}
}
