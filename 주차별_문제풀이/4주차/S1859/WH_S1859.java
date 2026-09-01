import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int T=Integer.parseInt(br.readLine());
		for (int t=1;t<=T;t++) {
			int N=Integer.parseInt(br.readLine());
			StringTokenizer sb=new StringTokenizer(br.readLine());
			int[] prices=new int[N];
			for (int n=0;n<N;n++) {
				prices[n]=Integer.parseInt(sb.nextToken());
			}
			
			long max_price=0;
			long profit=0;
            
			//뒤에서부터 탐색
			for (int i=N-1;i>=0;i--) {
				if (prices[i]>max_price){
					max_price=prices[i];
                } else{
					profit+=max_price-prices[i];
                }
			}
			System.out.println("#"+t+" "+profit);
		}
	}
}
