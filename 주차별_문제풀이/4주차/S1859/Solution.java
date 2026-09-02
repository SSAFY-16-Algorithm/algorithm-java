import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();

	public static void main(String[] args) throws Exception {
		
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			
			int N = Integer.parseInt(br.readLine());
			
			StringTokenizer st = new StringTokenizer(br.readLine());

			int[] prices = new int[N];
			for (int i = 0; i < N; i++) {
				prices[i] = Integer.parseInt(st.nextToken());
			}
			
			// 배열의 뒤쪽부터 돌면서 현재 가격과 최고 가격을 비교해서 
			// 현재 가격이 최고 가격보다 크면 업데이트하고, 최고 가격이 더 크면
			// 해당 가격과 최고 가격의 차를 계산해서 이익 총합 값을 저장하는 변수에 저장
			long result = 0;
			int maxPrice = prices[N-1];
			for (int i = N - 2; i >= 0; i--) {
				if (prices[i] >= maxPrice) {
					maxPrice = prices[i];
					continue;
				} else {
					result += maxPrice - prices[i];
				}
			}
			
			output.append("#").append(testCase).append(" ").append(result).append('\n');
		}
		System.out.println(output);
	}
}