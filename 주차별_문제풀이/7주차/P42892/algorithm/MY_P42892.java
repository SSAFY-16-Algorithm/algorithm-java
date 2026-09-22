package algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MY_P42892 {
	public static void main(String[] args) {
		int[][] answer = solution(new int[][] { { 5, 3 }, { 11, 5 }, { 13, 3 }, { 3, 5 }, { 6, 1 }, { 1, 3 }, { 8, 6 }, { 7, 2 },
				{ 2, 2 } });
		System.out.println(Arrays.toString(answer[0]));
		System.out.println(Arrays.toString(answer[1]));
	}

	static int[][] answer;

	public static int[][] solution(int[][] nodeinfo) {
		answer = new int[2][nodeinfo.length];
		// y 기준으로 정렬하고
		// 제일 큰걸 부모로 삼은 다음
		// 근데 예시에서 9가 6이 아니라 1에 붙는 근거가 뭐지 더 가까운 노드?
		// 거리가 같을 수는 없으니까 그래도 될듯 (x가 같을 수 없음)
		// 근데 그러면 들어오는 자식의 부모노드를 어떻게 찾지?
		// 부모 노드에서 자식을 찾아서 붙이는게 나을 것 같다는 생각
		// 아니 바로 전 랭크 노드들을 x가 작은순으로 정렬해놓고
		// 자식도 x가 작은 순으로 정렬해서
		// 자식 기준으로 부모를 하나 꺼내서 내가 그 부모보다 x가 작으면 바로 달아버리고
		// x가 크면 다음 부모까지 보고 더 가까운거에 붙이면 될듯

		// Arrays.sort(nodeinfo, comparator)로 직접 정렬하려고 했는데
		// 배열의 index + 1이 노드 번호라고 해서
		// 직접 다른 컬렉션에 넣어주고 정렬해야할듯?
		Queue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node n, Node m) {
				if (n.y != m.y) {
					return Integer.compare(m.y, n.y);
				} else {
					return Integer.compare(n.x, m.x);
				}
			}
		});

		for (int i = 0; i < nodeinfo.length; i++) {
			int[] node = nodeinfo[i];
			Node n = new Node(i + 1, node[0], node[1]);
			pq.offer(n);
		}

		// 첫 노드는 루트
		Node root = pq.poll();

		while (!pq.isEmpty()) {
			Node node = pq.poll();
			Node parent = root;
			Node child = null;

			do {
				if (parent.x < node.x) {
					child = parent.right;
				} else if (parent.x > node.x) {
					child = parent.left;
				}
				if (child == null) {
					break;
				} else {
					parent = child;
				}
			} while (child != null);

			if (node.x > parent.x)
				parent.right = node;
			else
				parent.left = node;
		}

		preorder(root, 0);
		postorder(root, 0);

		return answer;
	}

	public static void preorder(Node root, int idx) {
		answer[0][idx] = root.idx;
		if (root.left != null)
			preorder(root.left, ++idx);
		if (root.right != null)
			preorder(root.right, ++idx);
	}

	public static void postorder(Node root, int idx) {
		if (root.left != null)
			preorder(root.left, ++idx);
		if (root.right != null)
			preorder(root.right, ++idx);
		answer[1][idx] = root.idx;
	}

	static class Node {
		int x, y, idx;
		Node left, right;

		Node(int i, int x, int y) {
			this.idx = i;
			this.x = x;
			this.y = y;
		}
	}
}