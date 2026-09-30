import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HJ_S3289 {
	
	static int[] parent;
	
	static int find(int x) {
		
		if (parent[x] == x) {
			return x;
		}
		
		return parent[x] = find(parent[x]);
	}
	
	static void union(int a, int b) {
		
		int rootA = find(a);
		int rootB = find(b);
		
		if(rootA != rootB) {
			parent[rootB] = rootA;
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		StringTokenizer st;
		
		int tc = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= tc; testCase++) {
			
			output.append('#').append(testCase).append(' ');
			
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			
			parent = new int[n+1];
			
			for (int i = 1; i <= n; i++) {
				parent[i] = i;
			}
			
			for (int i = 0; i < m; i++) {
				
				st = new StringTokenizer(br.readLine());
				int command = Integer.parseInt(st.nextToken());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				if (command == 0) {
					union(a, b);
				} else {
					
					if (find(a)==find(b)) {
						output.append(1);
					} else {
						output.append(0);
					}
				}
				
			}
			
			output.append('\n');
			
		}
		
		System.out.print(output);
		
	}

}