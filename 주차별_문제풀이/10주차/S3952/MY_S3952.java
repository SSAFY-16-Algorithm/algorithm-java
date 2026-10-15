import java.io.*;
import java.util.*;

class MY_S3952 {
	public static void main(String args[]) throws Exception {
		System.setIn(new FileInputStream("res/S3952/sample_input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			List<Integer>[] graph = new ArrayList[N + 1];
			for (int i = 1; i <= N; i++) {
				graph[i] = new ArrayList<>();
			}
			int[] inDegree = new int[N + 1];

			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				graph[a].add(b);
				inDegree[b]++;
			}
			// System.out.println(Arrays.toString(inDegree));

			Queue<Integer> q = new ArrayDeque<>();
			// 진입차수 0인 노드만 큐에 넣기
			// 사이클은 없다고 가정
			for (int i = 1; i <= N; i++) {
				if (inDegree[i] == 0) q.offer(i);
			}

			sb.append('#').append(tc).append(' ');
			while(!q.isEmpty()) {
				int num = q.poll();
				sb.append(num).append(' ');
				for (int next: graph[num]) {
					inDegree[next]--;
					if (inDegree[next] == 0) q.offer(next);
				}
				// System.out.println(Arrays.toString(inDegree));
				
			}
			sb.append('\n');

		}

		System.out.println(sb.toString());
	}
}
