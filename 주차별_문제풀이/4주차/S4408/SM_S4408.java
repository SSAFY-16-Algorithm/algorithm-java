import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class SM_S4408 {
	/*
	 * 1. 한 복도에 얼마나 많은 학생들이 이동해야 하는지 계산한다.
	 * 2. 가장 많이 드나들어야 하는 복도의 학생 수가 정답.
	 */
	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer token;
	public static void main(String[] args) throws NumberFormatException, IOException {
		int T = Integer.parseInt(bf.readLine());
		
		for(int test_case = 1; test_case <= T; test_case ++) {
			int N = Integer.parseInt(bf.readLine());
			
			int[] way = new int[200];
			
			for(int i = 0; i < N; i ++) {
				token = new StringTokenizer(bf.readLine());
				int startWay = Math.floorDiv(Integer.parseInt(token.nextToken()) - 1, 2);
				int endWay = Math.floorDiv(Integer.parseInt(token.nextToken()) - 1, 2);
				
				if(endWay < startWay) {
					int cnt = startWay;
					startWay = endWay;
					endWay = cnt;
				}
				for(int s = startWay; s <= endWay; s ++) {
					way[s] ++;
				}
				
			}
			
			int answer = 0;
			for(int i = 0; i < 200; i ++) {
				answer = Math.max(answer, way[i]);
			}
			
			System.out.println(answer);
			
			
		}
	}
}