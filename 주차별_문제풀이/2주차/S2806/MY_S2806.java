import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MY_S2806 {
	static BufferedReader br;
	static StringBuilder sb;

	static int N; // 행렬 크기, 퀸의 개수
	static int[] queenCol; // 각 row별 퀸의 col 위치를 저장
	static int answer;

	public static void main(String args[]) throws Exception {
		System.setIn(new FileInputStream("res/S2806/sample_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			queenCol = new int[N];
			answer = 0;

			// 첫 행부터 퀸 놓기
			dfs(0);
			sb.append('#').append(test_case).append(" ").append(answer).append('\n');
		}
		System.out.println(sb);
	}

	public static void dfs(int row) {
		// 행 별로 퀸은 하나만 배치할 수 있기 때문에 row만큼만 퀸을 놓을 수 있는지 검사하면 됨
		if (row == N){
			answer++;
			return;
		}

		// 이전 행의 퀸 배치에 따라 충돌 검사
		for (int i = 0; i < N; i++) {
			// 이 열에 놓아도 되는지 검사하는 변수
			boolean isValid = true;
			
			for (int j = 0; j < row; j++) {
				// 내 열 : i, 내 행 row
				// 이전 열: queenCol[j], 이전 행 j
				// 충돌 해결 식 |행-행| == |열-열|이면 충돌
				if (i == queenCol[j] || (Math.abs(j-row) == Math.abs(i-queenCol[j]))){
					isValid = false;
					// 이 열에 대해서 더 검사할 필요 없으니까 break
					break;
				}
			}
			// 위의 포문을 다 돌아서 통과했으면
			// 이번 행에서 놓을 위치를 찾으면 함수 호출
			if (isValid){
				queenCol[row] = i;
				dfs(row + 1);
			}
		}

	}
}