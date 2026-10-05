import java.io.*;
import java.util.*;

public class Main {

	static int N, M, LOG;

	// Kruskal
	static int[] ufParent;
	static Edge[] edges;
	static ArrayList<Edge>[] mstGraph;
	static ArrayList<Edge> nonMstEdges;

	// LCA
	static int[][] parent;
	static int[] depth;
	static long[] dist;

	// subtree 구간
	static int[] tin;
	static int[] tout;
	static int timer;

	// 탈락 정점 구간 합
	static int[] diff;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// =========================
		// 1. Kruskal 준비
		// =========================

		ufParent = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			ufParent[i] = i;
		}

		edges = new Edge[M];

		mstGraph = new ArrayList[N + 1];

		for (int i = 1; i <= N; i++) {
			mstGraph[i] = new ArrayList<>();
		}

		nonMstEdges = new ArrayList<>();

		for (int i = 0; i < M; i++) {

			st = new StringTokenizer(br.readLine());

			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

			edges[i] = new Edge(u, v, cost);
		}

		Arrays.sort(edges, Comparator.comparingInt(e -> e.cost));

		// =========================
		// 2. MST 생성
		// =========================

		int cnt = 0;

		for (Edge e : edges) {

			if (union(e.from, e.to)) {

				cnt++;

				mstGraph[e.from].add(new Edge(e.from, e.to, e.cost));

				mstGraph[e.to].add(new Edge(e.to, e.from, e.cost));

			} else {

				nonMstEdges.add(e);
			}
		}

		// MST가 존재하지 않는 경우
		if (cnt != N - 1) {
			System.out.println(-1);
			return;
		}

		// =========================
		// 3. LCA 준비
		// =========================

		LOG = 1;

		while ((1 << LOG) <= N) {
			LOG++;
		}

		parent = new int[LOG][N + 1];

		depth = new int[N + 1];
		dist = new long[N + 1];

		tin = new int[N + 1];
		tout = new int[N + 1];

		timer = 0;

		dfs(1, 0);

		// 2^k번째 조상
		for (int k = 1; k < LOG; k++) {

			for (int v = 1; v <= N; v++) {

				parent[k][v] = parent[k - 1][parent[k - 1][v]];
			}
		}

		// =========================
		// 4. 탈락 정점 표시
		// =========================

		diff = new int[N + 2];

		for (Edge e : nonMstEdges) {

			int u = e.from;
			int v = e.to;
			long w = e.cost;

			long D = getDist(u, v);

			// 이 간선이 MST 경로보다 짧지 않으면
			// 어떤 X에게도 문제가 되지 않는다.
			if (D <= w) {
				continue;
			}

			long limit = D - w;

			// ---------------------
			// u 쪽 탈락 영역
			// ---------------------

			int uLast = findLastInvalid(u, v, limit);

			int uNext = nextVertex(uLast, v);

			markComponent(uLast, uNext);

			// ---------------------
			// v 쪽 탈락 영역
			// ---------------------

			int vLast = findLastInvalid(v, u, limit);

			int vNext = nextVertex(vLast, u);

			markComponent(vLast, vNext);
		}

		// =========================
		// 5. Euler Tour 구간 합
		// =========================

		int[] invalidCount = new int[N + 1];

		int cur = 0;

		// tin 기준으로 누적
		int[] byEuler = new int[N + 1];

		for (int i = 1; i <= N; i++) {

			cur += diff[i];

			byEuler[i] = cur;
		}

		for (int v = 1; v <= N; v++) {
			invalidCount[v] = byEuler[tin[v]];
		}

		// =========================
		// 6. 정답 출력
		// =========================

		boolean found = false;

		for (int x = 1; x <= N; x++) {

			if (invalidCount[x] == 0) {

				if (found) {
					sb.append(' ');
				}

				sb.append(x);
				found = true;
			}
		}

		if (!found) {
			sb.append("-1");
		}

		System.out.println(sb);
	}

	// ==================================================
	// u에서 v 방향으로 이동할 때
	// 2 * dist(u, x) < limit 을 만족하는
	// 가장 마지막 정점 x를 찾는다.
	// ==================================================

	static int findLastInvalid(int u, int v, long limit) {

		int totalEdges = depth[u] + depth[v] - 2 * depth[lca(u, v)];

		int left = 0;
		int right = totalEdges;

		int answer = u;

		while (left <= right) {

			int mid = (left + right) >>> 1;

			int x = kthVertex(u, v, mid);

			long d = getDist(u, x);

			if (2L * d < limit) {

				answer = x;

				left = mid + 1;

			} else {

				right = mid - 1;
			}
		}

		return answer;
	}

	// ==================================================
	// u -> v 경로에서
	// u로부터 k개의 간선을 이동한 정점
	// ==================================================

	static int kthVertex(int u, int v, int k) {

		int l = lca(u, v);

		int upLength = depth[u] - depth[l];

		int downLength = depth[v] - depth[l];

		// u -> LCA 구간
		if (k <= upLength) {

			return climb(u, k);
		}

		// LCA -> v 구간
		int remain = k - upLength;

		// v에서 위로 올라가는 방식으로 계산
		return climb(v, downLength - remain);
	}

	// ==================================================
	// node에서 step개의 간선만큼 위로 이동
	// ==================================================

	static int climb(int node, int step) {

		for (int k = 0; k < LOG; k++) {

			if ((step & (1 << k)) != 0) {

				node = parent[k][node];
			}
		}

		return node;
	}

	// ==================================================
	// from에서 to 방향으로 한 칸 이동한 정점
	// ==================================================

	static int nextVertex(int from, int to) {

		if (parent[0][from] != 0 && getDist(parent[0][from], to) < getDist(from, to)) {

			// to가 부모 방향
			return parent[0][from];
		}

		// to가 from의 subtree 방향

		int l = lca(from, to);

		// from이 to의 조상
		if (l == from) {

			int diffDepth = depth[to] - depth[from] - 1;

			return climb(to, diffDepth);
		}

		return parent[0][from];
	}

	// ==================================================
	// edge (a,b)를 잘랐을 때
	// a가 속하는 컴포넌트 전체를 invalid 처리
	//
	// a-b는 반드시 MST에서 인접한 정점
	// ==================================================

	static void markComponent(int a, int b) {

		// b가 a의 자식
		//
		// a쪽 컴포넌트 =
		// 전체 - subtree(b)

		if (parent[0][b] == a) {

			addRange(1, N, 1);

			addRange(tin[b], tout[b], -1);
		}

		// a가 b의 자식
		//
		// a쪽 컴포넌트 = subtree(a)
		else {

			addRange(tin[a], tout[a], 1);
		}
	}

	// Euler Tour 구간 갱신
	static void addRange(int left, int right, int value) {

		diff[left] += value;

		diff[right + 1] -= value;
	}

	// ==================================================
	// MST DFS
	// ==================================================

	static void dfs(int cur, int par) {

		tin[cur] = ++timer;

		parent[0][cur] = par;

		for (Edge e : mstGraph[cur]) {

			int next = e.to;

			if (next == par) {
				continue;
			}

			depth[next] = depth[cur] + 1;

			dist[next] = dist[cur] + e.cost;

			dfs(next, cur);
		}

		tout[cur] = timer;
	}

	// ==================================================
	// MST 거리
	// ==================================================

	static long getDist(int a, int b) {

		int l = lca(a, b);

		return dist[a] + dist[b] - 2L * dist[l];
	}

	// ==================================================
	// LCA
	// ==================================================

	static int lca(int a, int b) {

		if (depth[a] < depth[b]) {

			int temp = a;
			a = b;
			b = temp;
		}

		int diff = depth[a] - depth[b];

		// 깊이 맞추기
		for (int k = 0; k < LOG; k++) {

			if ((diff & (1 << k)) != 0) {

				a = parent[k][a];
			}
		}

		if (a == b) {
			return a;
		}

		// 같이 올라가기
		for (int k = LOG - 1; k >= 0; k--) {

			if (parent[k][a] != parent[k][b]) {

				a = parent[k][a];
				b = parent[k][b];
			}
		}

		return parent[0][a];
	}

	// ==================================================
	// Union-Find
	// ==================================================

	static boolean union(int a, int b) {

		int rootA = find(a);
		int rootB = find(b);

		if (rootA == rootB) {
			return false;
		}

		ufParent[rootB] = rootA;

		return true;
	}

	static int find(int x) {

		if (ufParent[x] == x) {
			return x;
		}

		return ufParent[x] = find(ufParent[x]);
	}

	// ==================================================
	// Edge
	// ==================================================

	static class Edge {

		int from;
		int to;
		int cost;

		Edge(int from, int to, int cost) {

			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}
}
