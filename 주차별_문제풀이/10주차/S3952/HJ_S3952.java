import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		StringTokenizer st;
		
		int tc = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= tc; testCase++) {
			
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // 1 ~ 50000
			int M = Integer.parseInt(st.nextToken()); // 1 ~ 150000
			
			List<List<Integer>> graph = new ArrayList<>();
			
			for (int i = 0; i <= N; i++) {
				graph.add(new ArrayList<>());
			}
			
			int[] indegree = new int[N+1];
			
			for (int i = 1; i <= M; i++) {
				
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				graph.get(a).add(b);
				indegree[b]++;
				
			}
			
			Queue<Integer> queue = new ArrayDeque<>();
			
			for (int i = 1; i <= N; i++) {
				if (indegree[i] == 0) {
					queue.offer(i);
				}
			}
			
			output.append('#').append(testCase).append(' ');
			
			while (!queue.isEmpty()) {
				
				int cur = queue.poll();
				
				output.append(cur).append(' ');
				
				for (int next : graph.get(cur)) {
					indegree[next]--;
					if (indegree[next] == 0) {
						queue.offer(next);
					}
				}
				
			}
			
			output.append('\n');
		}
		
		System.out.print(output);
		
	}

}