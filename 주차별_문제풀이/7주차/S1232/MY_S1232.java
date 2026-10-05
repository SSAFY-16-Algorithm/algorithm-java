import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static int result;
	// custom class를 만드는 것보다
	// 이렇게 자료구조 여러 개로 간단히 나누는 것도 생각하기
	static String[] value;
	static int[] left;
	static int[] right;

	public static void main(String[] args) throws Exception {
		// System.setIn(new FileInputStream("res/S1232/input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		for (int tc = 1; tc <= 10; tc++) {
			int N = Integer.parseInt(br.readLine());
			value = new String[N + 1];
			left = new int[N + 1];
			right = new int[N + 1];

			result = 0;

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				int idx = Integer.parseInt(st.nextToken());
				// string to char
				value[idx] = st.nextToken();

				// if we have more tokens, we can add nodes to the left and right
				if (st.hasMoreTokens()) {
					left[idx] = Integer.parseInt(st.nextToken());
					right[idx] = Integer.parseInt(st.nextToken());
				}
			}
			result = (int) binarySearch(1);

			sb.append('#').append(tc).append(' ').append(result).append('\n');
		}

		System.out.println(sb.toString());
	}

	public static double binarySearch(int idx) {
		String s = value[idx];
		// character가 숫자인지 검사
		if (Character.isDigit(s.charAt(0)))
			// 실제 숫자값으로 변환
			return Double.parseDouble(s);

		// 후위 연산
		double lNum = binarySearch(left[idx]);
		double rNum = binarySearch(right[idx]);

		return calculate(s, lNum, rNum);
	}

	public static double calculate(String op, double a, double b) {
		double result = 0;
		
		switch (op) {
		case "+":
			result = a + b;
			break;
		case "-":
			result = a - b;
			break;
		case "*":
			result = a * b;
			break;
		case "/":
			result = a / b;
			break;
		}
		return result;
	}

}

