import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SM_S1859 {
	/*
	 * 배열의 뒤에서부터 시작
	 * 뒤에서부터 가장 큰 값을 max에 저장
	 * max보다 작은 수면 answer +=max - 작은 수
	 * max보다 크면 max 변경
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer token;
	
		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case ++) {
			int N = Integer.parseInt(br.readLine());
			long answer = 0;
			int[] salePrice = new int[N];
			
			token = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i ++) {
				salePrice[i] = Integer.parseInt(token.nextToken());
			}
			
			int max = salePrice[N - 1];
			for(int i = N - 2; i >= 0; i --) {
				if(max < salePrice[i]) {
					max = salePrice[i];
					continue;
				}
				
				answer += max - salePrice[i];
			}
			System.out.println("#" + test_case + " " + answer);
			
		}
	}
}