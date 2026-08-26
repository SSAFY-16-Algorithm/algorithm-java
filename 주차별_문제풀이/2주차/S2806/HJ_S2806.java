package swea.d3.p2806;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
	
	static int N;
	static int answer;
	static int[] queen;
	
	static void dfs(int row) {
		if (row == N) {
			answer++;
			return;
		}
		
		for (int col = 0; col < N; col++) {
			if (isPossible(row, col)) {
				queen[row] = col;
				dfs(row+1);
			}
		}
	}
	
	static boolean isPossible(int row, int col) {
		for (int i = 0; i < row; i++) {
			if (queen[i] == col || Math.abs(queen[i] - col) == Math.abs(row - i)) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder result = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine().trim());
		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine().trim());
			answer = 0;
			queen = new int[N];
			dfs(0);
			result.append("#").append(test_case).append(" ").append(answer).append("\n");
		}
		
		System.out.println(result);
	}
}