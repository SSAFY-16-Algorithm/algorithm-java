import java.io.*;
import java.util.*;

public class Main {
	
	static int n, m;

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		int[][] edges = new int[m][2];

		// 모든 단서 저장
		for (int i = 0; i < m; i++) {
		    st = new StringTokenizer(br.readLine());

		    edges[i][0] = Integer.parseInt(st.nextToken());
		    edges[i][1] = Integer.parseInt(st.nextToken());
		}
		
		int left = 1;
		int right = m + 1;

		while (left < right) {

		    int mid = left + (right - left) / 2;

		    if (hasCycle(mid, n, edges)) {
		        // mid개의 단서에서 이미 모순이 발생함
		        // 최초 모순은 mid 이하에 존재
		        right = mid;

		    } else {
		        // mid개까지는 모순이 없음
		        // 최초 모순은 mid보다 뒤에 존재
		        left = mid + 1;
		    }
		}

		if (left == m + 1) {
		    System.out.println("Consistent");
		} else {
		    System.out.println(left);
		}
		
	}
	
	static boolean hasCycle(int k, int n, int[][] edges) {
		List<List<Integer>> graph = new ArrayList<>(); // 정점과 정점을 연결하는 간선을 저장하는 인접 리스트
		int[] indegree = new int[n+1]; // 각 정점의 진입 차수를 저장하는 배열
		
		for (int i = 0; i <= n; i++) {
			graph.add(new ArrayList<>());
		}
		
		// 1. 모든 정점 진입차수를 계산
		for (int i = 0; i < k; i++) {
			int from = edges[i][0];
			int to = edges[i][1];
			graph.get(from).add(to);
			indegree[to]++; // 도착 정점의 진입차수도 미리 한번에 계산
		}
		
		Queue<Integer> queue = new ArrayDeque<>(); // 위상정렬을 하기 위해 처리할 수 있는 정점들을 관리하는 큐
		
		// 2. 진입차수가 0인 정점 큐에 삽입
		for (int i = 1; i <= n; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}
		
		// 3. 큐에서 정점 하나 꺼내서 위상정렬 결과에 추가
		int processed = 0; // 처리 완료된 정점 개수
		
		while (!queue.isEmpty()) {
			
			int cur = queue.poll(); // 현재 queue에 있는 정점은 진입차수가 0이어서 선행 관계가 없는 정점으로서 시작을 해도 된다.
			processed++; // queue에서 빠질 때마다 위상 정렬을 위해 이미 처리된 정점이 되기 때문에 처리 완료된 개수를 기억하기 위해
			
			for (int next : graph.get(cur)) {
				indegree[next]--;
				if (indegree[next] == 0) {
					queue.offer(next);
				}
			}
			
		}
		return processed != n;
	}

}