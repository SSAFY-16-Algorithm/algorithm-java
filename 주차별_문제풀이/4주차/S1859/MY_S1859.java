import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MY_S1859 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static long answer;
	static int[] prices;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/S1859/input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			answer = 0;
			prices = new int[N];
			int maxPrice = prices[N-1];

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				prices[i] = Integer.parseInt(st.nextToken());
			}

			for (int i = N - 1; i >= 0; i--) {
				if (prices[i] > maxPrice) {
					maxPrice = prices[i];
				} else{
					answer += (maxPrice - prices[i]);
				}
			}

			sb.append('#').append(t).append(' ').append(answer).append('\n');
		}

		System.out.println(sb.toString());
	}

}
