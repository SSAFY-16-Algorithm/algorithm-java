import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class MY_S1248 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static int result, N, M, n1, n2, size;
	static Node[] arr;
	static Set<Integer> parentSet;

	public static void main(String args[]) throws Exception {
		System.setIn(new FileInputStream("res/S1248/input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			n1 = Integer.parseInt(st.nextToken());
			n2 = Integer.parseInt(st.nextToken());
			
			arr = new Node[N + 1];
			parentSet = new HashSet<>();
			
			for (int i = 0; i < N; i++) {
				arr[i + 1] = new Node(i + 1);
			}
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < M; i++) {
				int parent = Integer.parseInt(st.nextToken());
				int child = Integer.parseInt(st.nextToken());
				
				Node parentNode = arr[parent];
				Node childNode = arr[child];
				
				if (parentNode.left == null) {
					parentNode.left = childNode;
				}
				else {
					parentNode.right = childNode;
				}
				
				childNode.parent = parentNode;
			}
			
			Node findNode = arr[n1];
			while (findNode.parent != null) {
				findNode = findNode.parent;
				parentSet.add(findNode.idx);
			}
			
			findNode = arr[n2];
			
			int commonParent = -1;
			
			while (findNode.parent != null) {
				findNode = findNode.parent;
				if (parentSet.contains(findNode.idx)) {
					commonParent = findNode.idx;
					break;
				}
			}
			
			result = binarySearch(commonParent);
			
			sb.append('#').append(tc).append(' ').append(commonParent).append(' ').append(result).append('\n');
		}
		System.out.print(sb.toString());
	}
	
	static int binarySearch (int idx) {
		Node node = arr[idx];
		
		int leftSize = 0, rightSize = 0;
		
		if (node.left != null) {
			leftSize = binarySearch(node.left.idx);
		}
		if (node.right != null) {
			rightSize = binarySearch(node.right.idx);
		}
		
		return 1 + leftSize + rightSize;
	}
	
	static class Node {
		int idx;
		Node parent;
		Node left;
		Node right;
		Node (int i) {
			this.idx = i;
		}
	}

}
