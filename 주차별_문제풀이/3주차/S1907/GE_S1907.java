import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
 
class Solution {
    private static final int[] DR = {-1, 0, 1, 1, 1, 0, -1, -1};
    private static final int[] DC = {-1, -1, -1, 0, 1, 1, 1, 0};
     
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
         
        int T = Integer.parseInt(br.readLine());
         
        for(int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());
             
            int[][] sand = new int[H][W];
            Queue<int[]> collapsedQueue = new LinkedList<>();
             
            for (int r = 0; r < H; r++) {
                String line = br.readLine();
                for (int c = 0; c < W; c++) {
                    sand[r][c] = line.charAt(c) - '0';
                     
                    if (sand[r][c] <= 0)
                        collapsedQueue.offer(new int[] {r, c});
                }
            }
             
            int waves = 0;
 
            while (!collapsedQueue.isEmpty()) {
                boolean isCollapsed = false;
                int size = collapsedQueue.size();
                 
                for (int i = 0; i < size; i++) {
                    int[] current = collapsedQueue.poll();
                     
                    for (int dir = 0; dir < DR.length; dir++) {
                        int nr = current[0] + DR[dir];
                        int nc = current[1] + DC[dir];
                         
                        if (nr < 0 || nc < 0 || nr >= H || nc >= W) continue;
                         
                        if (sand[nr][nc] <= 0) continue;
                         
                        sand[nr][nc]--;
                         
                        if (sand[nr][nc] <= 0) {
                            collapsedQueue.offer(new int[] {nr, nc});
                            isCollapsed = true;
                        }
                    }
                }
                 
                if (isCollapsed) waves++;
            }
             
            bw.append("#" + test_case + " " + waves + "\n").flush();
        }
         
        bw.close();
        br.close();
    }
}