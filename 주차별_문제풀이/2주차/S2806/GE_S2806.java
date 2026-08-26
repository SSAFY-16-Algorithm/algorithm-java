import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class Solution {
	static int N;
	static int answer;
	
	// 공격 경로에 퀸이 있는지 확인하는 방문 체크 배열
	static boolean[] checkCol, checkRDiag, checkLDiag;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			StringBuilder sb = new StringBuilder("#").append(test_case).append(' ');
			N = Integer.parseInt(br.readLine());
			
			checkCol = new boolean[N];
			checkRDiag = new boolean[2*N]; // 같은 우상향 대각선에 존재하는 칸들의 인덱스는 row + col로 모두 같음 -> row + i
			checkLDiag = new boolean[2*N]; // 같은 좌상향 대각선에 존재하는 칸들의 인덱스는 row - col로 모두 같음 -> row - i + N: 음수 나오지 않게 N을 더해서 양수로 보정
			
			answer = 0;
			
			// 0행부터 백트래킹 탐색 시작
			backtracking(0);
			
			sb.append(answer).append('\n');
			bw.append(sb).flush();
		}
		br.close();
		bw.close();
	}
	
	// DFS 기반 백트래킹
	static void backtracking(int row) {
		// 기저 조건(목적지 도달): 퀸을 N 개 놓았을 경우 정답 카운트 증가 및 이전 상태로 복귀 (탐색 종료)
		if (row == N) {
			answer++;
			return;
		}
		
		for (int i = 0; i < N; i++) {
			// Promising 검사 및 Pruning
			if (checkCol[i]) continue;
			if (checkRDiag[row + i]) continue;
			if (checkLDiag[row - i + N]) continue;
			
			// 상태 변경: Promising하므로 퀸 배치 및 공격 경로 모두 잠금
			checkCol[i] = true;
			checkRDiag[row + i] = true;
			checkLDiag[row - i + N] = true;
			
			// DFS 재귀 호출: 다음 행에 퀸 배치
			backtracking(row + 1);
			
			// 원상 복구(Backtracking): 다른 조합을 찾기 위해 퀸 수거 및 잠금 해제
			checkCol[i] = false;
			checkRDiag[row + i] = false;
			checkLDiag[row - i + N] = false;
			}
	}
}