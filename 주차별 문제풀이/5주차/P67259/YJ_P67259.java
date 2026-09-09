import java.util.*;

class Solution {
    static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int N; 
    
    public static class road{
		int x; 
		int y; 
		int dir;
		int cost; 
		
		// dir : 위(0), 아래(1), 왼쪽(2), 오른쪽(3)
		public road(int x, int y, int dir, int cost) {
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.cost = cost;
		} 	
	}
	
	public static int solution(int[][] board) {
        
		// 1. 현재 위치에서 갈 수 있는 곳을 찾는다. (isValid, 벽 아니면) 
		// 2. 갈 수 있는 곳으로 갈 때의 길이 저장, 코너인 경우 코너 개수 저장 , 방향 저장
		// 3. 최종적으로 코너 개수와 길이를 바탕으로 min 값 업데이트 
		
		N = board.length;
		
		// 방향별로 (x,y) 위치에 도달했을 때의 최소 비용
		int[][][] cost = new int[N][N][4];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				Arrays.fill(cost[i][j], Integer.MAX_VALUE);
			}
		}
		
		// queue에 저장해야 할 것: x, y, 방향인덱스, 비용
		Queue<road> queue = new ArrayDeque<>();
		
		// 시작점 2개 : (0,0)에서 오른쪽이랑 아래 방향 가능(벽이 아니라면) 
		
		// 오른쪽 방향(3) 
		if(isValid(0,1) && board[0][1] == 0) {
			queue.offer(new road(0, 1, 3, 100));
			cost[0][1][3] = 100; 
		}
		
		if(isValid(1,0) && board[1][0] == 0) {
			queue.offer(new road(1,0,1, 100));
			cost[1][0][1] = 100; 
		}
		
		while(!queue.isEmpty()) {
			 road cur = queue.poll();
			 
			 for(int i=0; i<4; i++) {
				 int nx = cur.x+dx[i];
				 int ny = cur.y+dy[i];
				 
				 if(!isValid(nx, ny) || board[nx][ny] ==1) continue;
				 
				 int newCost = cur.cost + (cur.dir == i ? 100: 600);
				 
				// 기존 저장된 비용보다 더 적은 비용으로 도착할 수 있는 경우에만 갱신 후 큐에 추가
				 if(newCost < cost[nx][ny][i]) {
					 cost[nx][ny][i] = newCost;
					 queue.offer(new road(nx, ny, i, newCost));
				 }
			 }
		}
		
		int answer = Integer.MAX_VALUE;
		for(int i=0; i<4; i++) {
			answer = Math.min(answer, cost[N-1][N-1][i]);
		}
		
        return answer;
    }
	
	public static boolean isValid(int x, int y) {
		return x>=0 && y>=0 && x<N && y<N;
	}
}