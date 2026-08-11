package solution;

import java.util.Scanner;

public class WH_S4193 {
	
	static int N;
	static int[][] ocean;
	static boolean[][] visited;
	static int[] dr= {-1,1,0,0};
	static int[] dc= {0,0,-1,1};
	static int time=0;
	static int startr;
	static int startc;
	static int goalr;
	static int goalc;
	static int answer;
	
	public static void move(int r, int c) {
		// 목적지 도착하면 종료 
		if (r==goalr && c==goalc) {
			if (time<answer) {
				answer=time;
				return;
			}
		}
		
		// 가지치기 -> 현재 시간이 구해놓은 최소시간보다 이미 크면 더이상의 탐색 불필요
		if (time >= answer) {
			return;
		}
		
		for (int i=0;i<4;i++) {
			int nr=r+dr[i];
			int nc=c+dc[i];
			if (0<=nr && nr<N && 0<=nc && nc<N) {
				if (ocean[nr][nc] !=1 && !visited[nr][nc]) {
					
					//해당 길로 갔을 때 걸리는 추가 시간 계산 -> 백트래킹 쉽도록 변수로 저장
					int addTime;
					
					if (ocean[nr][nc]==2) {
            addTime=3-(time%3);
					}else {
						addTime=1; //일반 길이면 1초 추가
					}
					
					//방문 처리 
					visited[nr][nc]=true;
					time+=addTime;
					move(nr, nc);
					
					//돌아오면 방문 처리 회수
					visited[nr][nc]=false;
					time-=addTime;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int T=sc.nextInt();
		for (int t=1;t<=T;t++) {
			N=sc.nextInt();
			
			// 바다 초기화
			ocean=new int[N][N];
			for (int i=0;i<N;i++) {
				for (int j=0;j<N;j++) {
					ocean[i][j]=sc.nextInt();
				}
			}
			
			//시작점, 도착점 입력받기
			startr=sc.nextInt();
			startc=sc.nextInt();
			goalr=sc.nextInt();
			goalc=sc.nextInt();
			
			visited=new boolean[N][N];
			answer=99999;
			
			visited[startr][startc]=true;
			move(startr, startc);
			
			if (answer==99999) {
				System.out.println("#"+t+" -1");
			}else {
				System.out.println("#"+t+" "+answer);
			}
			
		}
		sc.close();

	}
}
