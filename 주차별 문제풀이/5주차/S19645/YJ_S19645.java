import java.util.*;
import java.io.*;
public class YJ_S19645 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int total = Integer.parseInt(st.nextToken());
			int Pa = Integer.parseInt(st.nextToken());
			int Pb = Integer.parseInt(st.nextToken());
			
			// A의 탐색
			int A = binarySearch(1, total, Pa, 1);
			
			// B의 탐색
			int B = binarySearch(1, total, Pb, 1);
			
			
			sb.append("#").append(t).append(" ");
			
			if(A==B) {
				sb.append(0);
			} else if(A>B) {
				sb.append("B");
			} else {
				sb.append("A");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	public static int binarySearch(int l, int r, int target, int count) {
		
		int c = (l+r)/2;
		
		if(target == c) {
			return count;
		} else if(target > c) {
			l = c; 
			return binarySearch(l, r, target, count+1);
		} else {
			r = c;
			return binarySearch(l, r, target, count+1);
		}
	}
}