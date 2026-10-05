import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class HJ_S1248 {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		StringTokenizer st;

		int tc = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= tc; testCase++) {

			st = new StringTokenizer(br.readLine());
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			int nodeA = Integer.parseInt(st.nextToken());
			int nodeB = Integer.parseInt(st.nextToken());

			int[] parent = new int[V + 1];
			int[] leftChild = new int[V + 1];
			int[] rightChild = new int[V + 1];
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < E; i++) {
				int p = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				parent[c] = p;
				
				if (leftChild[p] == 0) {
					leftChild[p] = c;
				} else {
					rightChild[p] = c;
				}
			}
			
            // nodeA의 모든 조상 표시
            boolean[] ancestor = new boolean[V + 1];

            int current = nodeA;

            while (current != 0) {
                ancestor[current] = true;
                current = parent[current];
            }

            // nodeB에서 부모 방향으로 올라가면서
            // nodeA의 조상과 처음 만나는 정점을 찾는다.
            current = nodeB;

            while (!ancestor[current]) {
                current = parent[current];
            }

            int commonAncestor = current;

            // 공통 조상을 루트로 하는 서브트리의 크기 계산
            Queue<Integer> queue = new ArrayDeque<>();

            queue.offer(commonAncestor);

            int subtreeSize = 0;

            while (!queue.isEmpty()) {

                int node = queue.poll();

                subtreeSize++;

                if (leftChild[node] != 0) {
                    queue.offer(leftChild[node]);
                }

                if (rightChild[node] != 0) {
                    queue.offer(rightChild[node]);
                }
            }

			output.append('#').append(testCase).append(' ').append(commonAncestor).append(' ').append(subtreeSize).append('\n');
		}

		System.out.print(output);

	}

}