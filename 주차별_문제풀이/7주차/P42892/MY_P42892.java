
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MY_P42892 {
	public static void main(String[] args) {
		int[][] answer = solution(new int[][] { { 5, 3 }, { 11, 5 }, { 13, 3 }, { 3, 5 }, { 6, 1 }, { 1, 3 }, { 8, 6 },
				{ 7, 2 }, { 2, 2 } });

		System.out.println(Arrays.toString(answer[0]));
		System.out.println(Arrays.toString(answer[1]));
	}

	static int[][] answer;
	static int size;

	public static int[][] solution(int[][] nodeinfo) {
		answer = new int[2][nodeinfo.length];
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

		preorder(root);
		size = 0;
		postorder(root);

		return answer;
	}

	public static void preorder(Node root) {
		answer[0][size] = root.idx;
		if (root.left != null) {
			++size;
			preorder(root.left);
		}

		if (root.right != null) {
			++size;
			preorder(root.right);
		}
	}

	public static void postorder(Node root) {
		if (root.left != null) {
			postorder(root.left);
		}
		if (root.right != null)
			postorder(root.right);
		answer[1][size++] = root.idx;
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