
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class SM_S26793 {

	/*
	 * homeworkInfos : [Di, Ti]의 배열
	 * duringSum: D의 누적합
	 * 
	 * Ti - duringSum이 0 이상이어야 함
	 * -> 따라서 Ti - duringSum이 가장 작은 부분이 정답
	 */
	
	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	public static void main(String[] args) throws Exception{
		int T = Integer.parseInt(bf.readLine());
		for(int test_case = 1; test_case <= T; test_case ++) {
			int N = Integer.parseInt(bf.readLine());
			int[][] homeworkInfos = new int[N][2];
			int[] duringSum = new int[N];
			
			for(int n = 0; n < N; n ++) {
				st = new StringTokenizer(bf.readLine());
				homeworkInfos[n][0] = Integer.parseInt(st.nextToken());
				homeworkInfos[n][1] = Integer.parseInt(st.nextToken());
			
			}
			
			Arrays.sort(homeworkInfos, (a, b) -> Integer.compare(a[1], b[1]));
			
			for(int n = 0; n < N; n ++) {	          
				if(n == 0) {
					duringSum[n] = homeworkInfos[n][0];
				} else {
					duringSum[n] = duringSum[n-1] + homeworkInfos[n][0];
				}
			
			}
			
			
			int min = Integer.MAX_VALUE;
			for(int i = 0; i < N; i ++) {
				min = Math.min(min, homeworkInfos[i][1] - duringSum[i]);
			}
			
			
			
		
			
			
			System.out.println(min);
			
			
		} 
	}
}