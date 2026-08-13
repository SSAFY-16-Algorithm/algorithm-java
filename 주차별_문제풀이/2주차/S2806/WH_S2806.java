package solution;
import java.util.Scanner;

public class WH_S2806 {
	static int N;
	static int[][] board;	
	public static int answer;
	
	//재귀 함수 생성 
	public static void selectQ(int row) {
		if(row==N) { //N-1까지 queen을 놓을 수 있었다는 뜻이므로 가능한 경우의 수 +1
			answer++;
			return;
		}
				
		for (int c=0;c<N;c++) {
			if (isPossible(row,c)) {
				board[row][c]=1; //방문 표시
				//row+1로 selectQ 다시 호출
				selectQ(row+1);
				
				//다시 돌아왔으면 방문 표시 없애기
				board[row][c]=0;
			}
		}
	}
	
	public static boolean isPossible(int row, int col) {
		//col 검사
		for (int r=0;r<row;r++) {
			if (board[r][col]==1) {
				return false;
			}
		}
		
		//대각선 검사 - 좌상 
		int nr=row-1;
		int nc=col-1;
		while (0<=nr && nr<N && 0<=nc && nc<N) {
			if(board[nr][nc]==1) {
				return false;
			}
			nr--;
			nc--;	
		}
		
		//대각선 검사 - 우상 
		nr=row-1;
		nc=col+1;
		while (0<=nr && nr<N && 0<=nc && nc<N) {
			if(board[nr][nc]==1) {
				return false;
			}
			nr--;
			nc++;	
		}
		
		// 좌하, 우하의 경우 아직 체스말을 놓지 않았으므로 검사 안 해도 됨
		return true; // 여기까지 왔으면 검사 다 통과한 거니까 true 반환
	}
				
	public static void main(String[] args) {

		Scanner sc=new Scanner(System.in);
		int T=sc.nextInt();
		for (int t=1;t<=T;t++) {
			N=sc.nextInt();
			answer=0; //tc마다 초기화
			
			//체스판 생성 -> 전부 0으로 초기화되어있는 상태 
			board=new int[N][N];
			
			//재귀함수 호출 
			selectQ(0);
			
			System.out.println("#"+t+" "+answer);
		}
	}
}


