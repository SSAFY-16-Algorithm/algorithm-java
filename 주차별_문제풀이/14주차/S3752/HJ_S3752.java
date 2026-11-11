import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			int N = Integer.parseInt(br.readLine());

			int[] scores = new int[N];
			int total = 0;

			StringTokenizer st = new StringTokenizer(br.readLine());

			for (int i = 0; i < N; i++) {
				scores[i] = Integer.parseInt(st.nextToken());
				total += scores[i];
			}

			// dp[s] = s점을 만들 수 있는지 여부
			boolean[] dp = new boolean[total + 1];

			// 모든 문제를 틀리면 0점 가능
			dp[0] = true;

			for (int score : scores) {

				// 같은 문제를 중복 사용하지 않도록 역순 탐색
				for (int s = total; s >= score; s--) {

					if (dp[s - score]) {
						dp[s] = true;
					}
				}
			}

			// 만들 수 있는 점수의 개수 계산
			int answer = 0;

			for (int s = 0; s <= total; s++) {
				if (dp[s]) {
					answer++;
				}
			}

			output.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.print(output);
	}
}
