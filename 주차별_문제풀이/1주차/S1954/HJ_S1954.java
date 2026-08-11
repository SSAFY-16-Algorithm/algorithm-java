import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder result = new StringBuilder();

        // 총 테스트 케이스 개수
        int T = Integer.parseInt(br.readLine().trim());

		for(int test_case = 1; test_case <= T; test_case++) {
			
			// 달팽이 숫자
			int N = Integer.parseInt(br.readLine().trim());
			// 달팽이
			int[][] snail = new int[N][N];
			
			// 달팽이 이동 방향
			int[] dr = {0, 1, 0, -1};
			int[] dc = {1, 0, -1, 0};
						
			int row = 0;
			int col = 0;
			int dir = 0;
			
			// N*N 배열을 채워나가기
			for (int num = 1; num <= N*N; num++) {
				snail[row][col] = num;
				
				// 배열이 다 채워졌으면 종료
				if (num == N*N) {
					break;
				}
				
				//다음 방향으로 나아가기
				int nextrow = row + dr[dir];
				int nextcol = col + dc[dir];
				
				// 배열이 끝나는 점 확인 후 방향 전환
				if (nextrow < 0 || nextrow >= N || nextcol < 0 || nextcol >= N || snail[nextrow][nextcol] != 0) {
					
					// 종료 후 방향 전환
					dir = (dir + 1) % 4;
					nextrow = row + dr[dir];
					nextcol = col + dc[dir];
				}
				
				// 다음 방향 나아가기
				row = nextrow;
				col = nextcol;	
			}
			
			// 테스트 케이스 출력
			result.append("#").append(test_case).append("\n");
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					result.append(snail[i][j]);
					
					if (j < N-1) {
						result.append(" ");
					}
				}
				result.append("\n");
			}

		}
		
		System.out.println(result);
	}

}
