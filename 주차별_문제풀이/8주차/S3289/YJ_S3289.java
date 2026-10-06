import java.util.*;
import java.io.*;

public class YJ_S3289 {
	static StringBuilder sb;
	static int[] parent;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			parent = new int[n+1];
			for(int i=1; i<=n; i++) {
				parent[i] = i;
			}
			int m = Integer.parseInt(st.nextToken());
			sb.append("#").append(t).append(" ");
			for(int i=0; i<m; i++) {
				// 연산 m번
				// 합집합: 0 a b -> a포함 집합과 b포함 집합을 합침
				// find: 1 a b -> find() 수행 (find 수행 시 같은 집합이면 1 출력하기)
				st = new StringTokenizer(br.readLine());
				int oper = Integer.parseInt(st.nextToken());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				if(oper == 0) {
					union(a, b);
				} else if(oper ==1) {
					if(find(a) == find(b)) sb.append(1);
					else sb.append(0);
				}
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	// 두 개의 집합 합치기
	public static void union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		
		if(rootA == rootB) return; 
		else {
			if(rootA > rootB) parent[rootA] = rootB;
			else parent[rootB] = rootA;
		}
	}
	
	// 같은 집합이면 1 출력하기
	public static int find(int x) {
		if(parent[x] == x) {
			return x;
		}
		
		return parent[x] = find(parent[x]);
	}
}
