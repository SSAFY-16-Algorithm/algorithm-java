import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SM_S19645 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		
		for(int t = 1; t <= T; t ++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int P = Integer.parseInt(st.nextToken());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			
			int aSearch = binarySearch(P, A);
			int bSearch = binarySearch(P, B);
			
			
			char answer = aSearch == bSearch? '0': aSearch > bSearch? 'B': 'A';
			System.out.println( "#" + t + " " + answer);

		}
		
	}
	
	public static int binarySearch(int P, int search) {
		int searchNum = 0;
		int left = 1;
		int right = P;
		int center = (left + right) / 2;
		while(center != search) {
			if(center > search) {
				right = center;
			} else {
				left = center;
			}
			center = (left + right) / 2;
			searchNum ++;
		}
		
		return searchNum;
	}
}
