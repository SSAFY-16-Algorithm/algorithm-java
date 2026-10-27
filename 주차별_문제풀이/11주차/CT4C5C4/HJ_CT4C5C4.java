import java.io.*;
import java.util.*;

public class Main {
    
    static int N;
    static int[][] grid;
    static int[][] dp;
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    public static void main(String[] args) throws NumberFormatException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        grid = new int[N][N];
        dp = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        int answer = 0;
        
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                answer = Math.max(answer, dfs(r, c));
            }
        }
        
        System.out.println(answer);

    }

    static int dfs(int r, int c) {
        
        if (dp[r][c] != 0) {
            return dp[r][c];
        }
        
        int maxLength = 1;
        
        for (int i = 0; i < 4; i++) {
            
            int nr = r + dr[i];
            int nc = c + dc[i];
            
            if (isValid(nr, nc) && grid[r][c] < grid[nr][nc]) {
                maxLength = Math.max(maxLength, dfs(nr, nc) + 1);
            }
        
        }
        
        dp[r][c] = maxLength;
        
        return maxLength;
        
    }
    
    static boolean isValid(int nr, int nc) {
        if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
            return true;
        }
        return false;
    }

}