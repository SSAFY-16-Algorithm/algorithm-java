import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		
		// 테스트 케이스 개수
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int P = Integer.parseInt(st.nextToken());
			int Pa = Integer.parseInt(st.nextToken());
			int Pb = Integer.parseInt(st.nextToken());
			
			int countA = binarySearchCount(P, Pa);
			int countB = binarySearchCount(P, Pb);
			
			char result;
			
			if (countA < countB) {
				result = 'A';
			} else if (countA > countB) {
				result = 'B';
			} else {
				result = '0';
			}
		
			output.append("#").append(testCase).append(" ").append(result).append('\n');
			
		}
		
		System.out.print(output);
	
	}
	
	static int binarySearchCount(int P, int target) {
		
		int low = 1;
		int high = P;
		int count = 0;
		
		while (low <= high) {
			
			int mid = low + (high - low) / 2;
			
			count++;
			
			if (mid == target) {
				break;
			}
			
			if (mid < target) {
				low = mid;
			} else {
				high = mid;
			}
		
		}
		
		return count;
	}

}