import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Solution {
	
	static HashMap<Integer, Integer> parentMap;
	static StringBuilder sb;
	static int find(int a) {
		if(!parentMap.containsKey(a)) {
			return a;
		}else if (parentMap.get(a)==a) {
			return a;
		}
		else {			
			int root = find(parentMap.get(a));
		    parentMap.put(a, root); //경로 압축 
		    return root;
		}
	}
	
	static void union(int method, int a, int b) {
		int repA=find(a);
		int repB=find(b);
		
		if(method==0) {
			parentMap.put(repB, repA);
		}else if(method==1) {
			if (repA==repB) {
				sb.append(1);
			}else {
				sb.append(0);
			}
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int T=Integer.parseInt(br.readLine());
		for(int t=1;t<=T;t++) {
			StringTokenizer st=new StringTokenizer(br.readLine());
			sb=new StringBuilder();
			sb.append("#"+t+" ");
			int N=Integer.parseInt(st.nextToken());
			int M=Integer.parseInt(st.nextToken());
			
			parentMap=new HashMap<>();			
			
			for(int m=0;m<M;m++) {
				String[] str = br.readLine().split(" ");
				int op=Integer.parseInt(str[0]);
				int a=Integer.parseInt(str[1]);
				int b=Integer.parseInt(str[2]);
				if (str[0].equals("0")) {
					union(0,a,b);
				}else if (str[0].equals("1")) {
					union(1,a,b);
				}
			}
			
			System.out.println(sb);
		}
	}
}
