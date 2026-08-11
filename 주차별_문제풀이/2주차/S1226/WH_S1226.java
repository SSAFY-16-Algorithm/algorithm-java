package D3;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class WH_S1226 {
	static int N = 16;		
	static int M=0;
	// 시작점 인덱스 반환 메서드
	static int[] findStart(char[][] maze) {
		for (int r=0;r<N;r++) {
			for (int c=0;c<N;c++) {
				if (maze[r][c]=='2') {
					return new int[] {r,c};
				}
			}
		}
		return null;
	}
		
	
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		
		for (int t=1;t<=10;t++) {
			int T=scan.nextInt();			
			int answer=0;
			
			// maze 입력 받음
			char[][] maze = new char[N][N];
			for (int i = 0; i < N; i++) {				
				String line = scan.next();
			    maze[i] = line.toCharArray(); //toCharArray밖에 몰라서 이렇게 했는데 int[][]maze로 하는 방법도 알아보기 
			}
			
			// 시작점 찾기 
			int[] start= findStart(maze);
			int sr = start[0];
			int sc = start[1];
			maze[sr][sc] = '1';  // 시작점 방문 처리
			
			// 갈 수 있는 위치를 넣어두는 큐
			Queue<int[]> queue = new LinkedList<>();
			queue.offer(new int[] {sr, sc}); // 시작점 넣음
			
			// 방향 리스트
			int[] dr= {0,0,1,-1};
			int[] dc= {1,-1,0,0};
			
			// BFS 진행			
			while (!queue.isEmpty()) {
				int[] current=queue.poll();
				int cr=current[0];
				int cc=current[1];
				
				for (int idx=0;idx<4;idx++) {
					int nr=cr+dr[idx];
					int nc=cc+dc[idx];
					
					// 갈 수 있는지 체크 (1. 범위 내인지, 2. 길이 맞는지) 
					if (0<=nr && nr<N && 0<=nc && nc<N && maze[nr][nc]!='1') {
						if(maze[nr][nc]=='3') {
							answer=1;
							break;
						}
						maze[nr][nc] = '1';  // 방문 처리
					    queue.offer(new int[] {nr, nc});
					}
				}	
			}
				
			System.out.println("#"+T+" "+answer);
		}
		
	}
}

