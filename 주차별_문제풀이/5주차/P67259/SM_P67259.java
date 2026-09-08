import java.util.Arrays;

public class SM_P67259 {
	static int N;
	static int[][][] minCost;
	static int[] dR = {1, 0, -1, 0};
	static int[] dC = {0, 1, 0, -1};
	
	
	public static void main(String[] args) {
		int[][] arr = {{0,0,0},{0,0,0},{0,0,0}};
		
		System.out.println(solution(arr));
	}
	
	public static int solution(int[][] board) {
		N = board.length;
        int answer = Integer.MAX_VALUE;
        minCost = new int[N][N][4]; // 4πÊ«‚
        
        for(int i = 0; i < N; i ++) {
        	for(int j = 0; j < N; j ++) {
        		Arrays.fill(minCost[i][j], Integer.MAX_VALUE);
        	}
        }
        
        
        dfs(0,0,-1,0,board);
        for(int i = 0; i < 4; i ++) {
        	answer = Math.min(answer, minCost[N-1][N-1][i]);
        }
        return answer;
    }

	static void dfs(int r, int c, int dir, int cost, int[][] board) {
		for(int nextDir = 0; nextDir < 4; nextDir++) {

            int newR = r + dR[nextDir];
            int newC = c + dC[nextDir];

            if(newR < 0 || newR >= N ||
               newC < 0 || newC >= N ||
               board[newR][newC] == 1) {
                continue;
            }

            int newCost;

            if(dir == -1 || dir == nextDir) {
                newCost = cost + 100;
            } else {
                newCost = cost + 600;
            }

            if(newCost >= minCost[newR][newC][nextDir]) {
                continue;
            }

            minCost[newR][newC][nextDir] = newCost;

            dfs(newR, newC, nextDir, newCost, board);
        }
	}
}
