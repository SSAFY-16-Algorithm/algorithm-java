import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MY_S3289 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	static int[] parent;
	static String result = "";
	public static void main(String[] args) throws Exception {
		// System.setIn(new FileInputStream("res/S3289/sample_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			result = "";
			
			parent = new int[N + 1];
			for (int i = 1; i <= N; i++) {
				parent[i] = i;
			}
			sb.append('#').append(tc).append(' ');
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int command = Integer.parseInt(st.nextToken());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				if (command == 0) {
					union(a, b);
				} else {
					if (find(a) == find(b)) {
						sb.append("1");
					} else {
						sb.append("0");
					}
				}
			}
			
			sb.append('\n');
		}
		
		System.out.println(sb.toString());
	}
	
	public static int find(int a) {
		if (parent[a] == a) return a;
		else return parent[a] = find(parent[a]);
	}
	
	public static void union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		if (rootA != rootB) {
			parent[rootB] = rootA;
		}
	}
}

