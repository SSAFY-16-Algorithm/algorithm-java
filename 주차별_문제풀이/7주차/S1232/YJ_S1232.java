import java.util.*;
import java.io.*;

public class YJ_S1232 {
	
	static class Node {
		String val; 
		int left; 
		int right;
		
		public Node(String val, int left, int right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
	
	static Node[] tree;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for(int t=1; t<=10; t++) {
			int N = Integer.parseInt(br.readLine());
			tree = new Node[N+1];
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int idx = Integer.parseInt(st.nextToken());
				String val = st.nextToken();
				
				if(st.hasMoreTokens()) {					
					int left = Integer.parseInt(st.nextToken());
					int right = Integer.parseInt(st.nextToken());
					
					tree[idx] = new Node(val, left, right);
				}else {
					tree[idx] = new Node(val, 0, 0); // 리프 노드인 경우에는 자식에 0 넣어준다. 
				}
			}
			// 소수점 버리고 출력 필요 
			// 중위 순회 연산
			double result = evaluate(1); // 루트 노드부터 후위 순회로 계산
			sb.append("#").append(t).append(" ").append((int) result).append("\n");
		}
		System.out.println(sb);
	}
	
	public static double evaluate(int nodeIdx) {
		Node node = tree[nodeIdx];
		
		// 리프 노드인 경우 값 반환
		if(node.left == 0 && node.right ==0) {
			return Double.parseDouble(node.val);
		}
		
		// 왼쪽 -> 오른쪽 -> 본인 이므로 왼쪽 자식과 오른쪽 자식을 먼저 계산한다. 
		double leftVal = evaluate(node.left);
		double rightVal = evaluate(node.right);
		
		switch(node.val) {
		case "+":
			return leftVal + rightVal; 
		case "-":
			return leftVal - rightVal; 
		case "*":
			return leftVal * rightVal; 
		case "/":
			return leftVal / rightVal; 
		}
		return 0; 
	}
}