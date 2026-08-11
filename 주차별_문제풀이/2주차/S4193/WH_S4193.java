package solution;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class WH_S4193 {
	
	public static int N;
	public static int M;
	public static char[][] board;
	
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		int T=scan.nextInt();
		
		for (int t=1;t<=T;t++) {
			boolean goal=true;
			
			N=scan.nextInt();
			M=scan.nextInt();
			
			board = new char[N][M];
			
			for (int i = 0; i < N; i++) {				
				String line = scan.next();
			    board[i] = line.toCharArray(); 
			}
			
			Queue<int[]> devil = new ArrayDeque<int[]>();
			Queue<int[]> suyeon = new ArrayDeque<int[]>();
			int[] goddess=new int[2];
			
			for (int i=0;i<N;i++) {
				for (int j=0;j<M;j++) {
					if (board[i][j]=='*'){
						devil.offer(new int[] {i,j});
					}
					if (board[i][j]=='S') {
						suyeon.offer(new int[] {i,j});
					}
					if (board[i][j]=='D') {
						goddess=new int[] {i,j};
					}
				}
			}
			
			int[] dr= {-1,1,0,0};
			int[] dc= {0,0,-1,1};
			
			int dist=0;
			
			while (!(suyeon.isEmpty())) {		
				//가지치기로 D 주변이 비어있지 않게 되는 순간 break하게 할 수도 있으려나?
				int[] goddessRoute= new int[4];
				for (int idx=0;idx<4;idx++) {
					int nr=goddess[0]+dr[idx];
					int nc=goddess[1]+dc[idx];
					if (0<=nr && nr<N && 0<=nc && nc<N) {
						if (goddessRoute[idx]=='.') {
							break;
						}
					}
					goal=false;
					break;
				}
				if (goal==false){
					break;
				}
				
				int devilSize=devil.size();
				for(int i=0;i<devilSize;i++) {
					int[] dcur=devil.poll();
					for (int idx=0;idx<4;idx++) {
						int ndr=dcur[0]+dr[idx];
						int ndc=dcur[1]+dc[idx];
						
						if (0<=ndr && ndr<N && 0<=ndc && ndc<M && board[ndr][ndc]!='X' && board[ndr][ndc]!='D') {
							board[ndr][ndc]='*';
							devil.offer(new int[] {ndr, ndc});
						}
					}
				}
				
				//수연 이동 
				int suyeonSize=suyeon.size();
				for(int i=0;i<suyeonSize;i++) {
					int[] scur=suyeon.poll();
					if (board[scur[0]][scur[1]]=='D') {
						goal=true;
						break;
					}
					for (int idx=0;idx<4;idx++) {
						int nsr=scur[0]+dr[idx];
						int nsc=scur[1]+dc[idx];
					
						if (0<=nsr && nsr<N && 0<=nsc && nsc<M && board[nsr][nsc]!='X' && board[nsr][nsc]!='*') {
							suyeon.offer(new int[] {nsr, nsc});
							System.out.println(nsr+","+nsc);
						}
					}
				}
				
				for (int i=0;i<N;i++) {
					for (int j=0;j<M;j++) {
						System.out.print(board[i][j]);
					}System.out.println("");
				}		
				
				if (goal==true) {
					break;
				}
				dist++;
			}
			
			if(goal==true) {
				System.out.println("#"+t+" "+dist);
			}
			else {
				System.out.println("#"+t+" GAME OVER");
			}
						
		}
	}
}
