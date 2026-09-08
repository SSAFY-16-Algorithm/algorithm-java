import java.util.*;

class Solution {
    static int N;
    static int[][] map;
    public boolean isValid(int r, int c){
        return (0<=r && r<N && 0<=c && c<N && map[r][c]!=1);
    }
    
    public int solution(int[][] board) {
        map = board;
        N=board.length;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{N-1, N-1, -1, 0});
        
        int[] dr={-1, 0, 1, 0};
        int[] dc={0, -1, 0, 1};
        
        int[][][] positional_cost=new int[N][N][4];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                Arrays.fill(positional_cost[i][j],Integer.MAX_VALUE);
            }
        }
        
        //역방향
        while(!queue.isEmpty()){
            int[] cur=queue.poll();
            int curR=cur[0];
            int curC=cur[1];
            for(int i=0;i<4;i++){
                int newR=curR+dr[i];
                int newC=curC+dc[i];
                if(isValid(newR, newC)){
                    int cost=0;
                    if (cur[2]==-1 || cur[2]==i){ //방향 같으면 커브 x
                        cost=100;
                    }else{
                        cost=600;
                    }
                    if (cur[3]+cost<=positional_cost[newR][newC][i]){      
                        positional_cost[newR][newC][i] = cur[3]+cost;
                        queue.add(new int[]{newR,newC, i, cur[3]+cost});
                    }
                }
            }
        }
        
        int min_cost=Integer.MAX_VALUE;
        for(int i=0;i<4;i++){
            if (positional_cost[0][0][i]<min_cost){
                min_cost=positional_cost[0][0][i];
            }
        }
        return min_cost;
    }
}
