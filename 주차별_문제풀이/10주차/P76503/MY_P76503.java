import java.util.*;

class MY_P76503 {

	public static void main(String args[]) {
		System.out.println(solution(
			new int[] {-5, 0, 2, 1, 2},
			new int[][] {{0,1}, {3,4}, {2,3}, {0,3}}
		));
	}

	static int N;
	static List<Integer>[] graph;
	static Queue<Integer> pq;
	public static long solution(int[] a, int[][] edges) {
		N = a.length;
		graph = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int i = 0; i < edges.length; i++) {
			int A = edges[i][0];
			int B = edges[i][1];
			graph[A].add(B);
			graph[B].add(A);
		}

		List<Integer> stack = new ArrayList<>();
		stack.add(0);

		// GPT:
		// 방문 순서를 먼저 기록하고
		// 자식의 부모 노드를 기억한 뒤
		// 역순으로 처리하며 부모로 weight를 전달하면 됨
		int[] parent = new int[N];
		parent[0] = -1;
		long[] weight = new long[N];
		for (int i = 0; i < N; i++) {
			weight[i] = a[i];
		}
		List<Integer> order = new ArrayList<>();

		// 가중치의 합이 0이 아니라면 
		// 모든 노드를 0으로 만들 수 없음
		// 순서를 기록하면서 검사하기
		long cost = 0;
		while(stack.size() > 0) {
			int num = stack.remove(stack.size() - 1);
			order.add(num);
			cost += weight[num];
			
			for (int i = 0; i < graph[num].size(); i++) {
				int child = graph[num].get(i);
				// 무방향 그래프이기 때문에
				// 되돌아가는 걸 방지하기 위해 필요함
				if (parent[num] == child) continue;
				parent[child] = num;
				stack.add(child);
			}
		}

		long answer = 0;
		if (cost != 0) {
			answer = -1;
			return answer;
		}

		for (int i = order.size() - 1; i > 0; i--) {
			int num = order.get(i);
			int p = parent[num];
			
			answer += Math.abs(weight[num]);

			weight[p] += weight[num];
			weight[num] = 0;
		}	

		return answer;
	}
}
