import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		StringTokenizer st;
		
		// 테스트 케이스 개수
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			
			st = new StringTokenizer(br.readLine());
			int P = Integer.parseInt(st.nextToken());
			int Pa = Integer.parseInt(st.nextToken());
			int Pb = Integer.parseInt(st.nextToken());
			char result;
			
			// 이진 탐색 코드를 사용하면 각각 Pa와 Pb를 찾는 것까지는 가능
			// 1. while문을 두번 돌릴 것인가? 따로?
			// 한번 탐색하면 count를 1개씩 올리자.
			// ======== 문제 풀이 코드 =========
			
			int lowPa = 1;
			int highPa = P;
			int countPa = 0;
			
			while (lowPa <= highPa) {
				int mid = lowPa + (highPa - lowPa) / 2;
				if (mid == Pa) {
					countPa += 1;
					break;
				} else if (mid < Pa) {
					countPa += 1;
					lowPa = mid;
				} else {
					countPa += 1;
					highPa = mid;
				}
			}
			
			int lowPb = 1;
			int highPb = P;
			int countPb = 0;
			
			while (lowPb <= highPb) {
				int mid = lowPb + (highPb - lowPb) / 2;
				if (mid == Pb) {
					countPb += 1;
					break;
				} else if (mid < Pb) {
					countPb += 1;
					lowPb = mid;
				} else {
					countPb += 1;
					highPb = mid;
				}
			}
			
			if (countPa < countPb) {
				result = 'A';
			} else if (countPa > countPb) {
				result = 'B';
			} else {
				result = '0';
			}
			
			// ======== 문제 풀이 코드 =========
			
			output.append("#").append(testCase).append(" ").append(result).append('\n');
			
		}
		
		System.out.print(output);
	
	}

}