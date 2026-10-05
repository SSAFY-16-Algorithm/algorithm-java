import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MY_S19645 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/S19645/sample_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			int end = Integer.parseInt(st.nextToken());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			
			A = binarySearch(1, end, A);
			B = binarySearch(1, end, B);
			String result = A == B ? "0" : A < B ? "A" : "B";
			sb.append("#")
			  .append(tc)
			  .append(" ")
			  .append(result)
			  .append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// binarySearch가 1을 반환함으로써 총 재귀가 몇번돌았는지 검사
	public static int binarySearch(int start, int end, int num) {
		int mid = (start + end) / 2;
		if (mid == num) {
			return 1;
		}
		else if (num < mid) {
			return 1 + binarySearch(start, mid, num);
		}
		else {
			return 1 + binarySearch(mid, end, num);
		}
	}

}
