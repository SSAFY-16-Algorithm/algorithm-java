import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static int N;
	static StringTokenizer st;
	static String[] tree;
	static int[] left;
	static int[] right;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		for(int t=1;t<11;t++) {
			N=Integer.parseInt(br.readLine());
			tree=new String[N+1];
			left=new int[N+1];
			right=new int[N+1];
			
			for(int n=0;n<N;n++) {
				st=new StringTokenizer(br.readLine());
				int idx=Integer.parseInt(st.nextToken());
				String op=st.nextToken();				
				tree[idx]=op;
				
				if (st.hasMoreTokens()) { //연산자가 주어진 경우 추가로 두 개 숫자 더 들어옴
					int n1=Integer.parseInt(st.nextToken()); 
					int n2=Integer.parseInt(st.nextToken()); 
					left[idx]=n1;
					right[idx]=n2;
				}				
				//tree={null,-,-,10,88,65}
			}
			int answer=search(1);
			System.out.println("#"+t+" " +answer);
		}			
	}
	
	static int search(int idx) {
		if (tree[idx].equals("+")) {
			return(search(left[idx]) + search(right[idx]));
		}else if (tree[idx].equals("-")){
			return(search(left[idx]) - search(right[idx]));
		}else if (tree[idx].equals("*")){
			return(search(left[idx]) * search(right[idx]));
		}else if (tree[idx].equals("/")){
			return(search(left[idx]) / search(right[idx]));
		}
		else {
			return Integer.parseInt(tree[idx]);
		}
	}
} 
 
