package S1907;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class WH_S1907 {
	private static Queue<int[]> currentqueue = null;
	private static Queue<int[]> nextqueue = null;
	private static char[][] sand = null;
	private static boolean[][] inQueue = null; // firstqueue에 들어가 있는지를 나타냄
	static int H;
	static int W;
	static int answer=0;
	static int[] dr= {-1,-1,-1,0,1,1,1,0};
	static int[] dc= {-1,0,1,1,1,0,-1,-1};
	
	// 해당 모래 주변의 '.' 개수 반
	public static int search(int r, int c) {
		int cnt=0;
		for (int i=0;i<8;i++) {
			int nr=r+dr[i];
			int nc=c+dc[i];
			if (0<=nr && nr<H && 0<=nc && nc<W) {
				if(sand[nr][nc]=='.') {
					cnt++;
				}
			}
		}
		return cnt;
	}

	// 이번 파도에 무너질 애들을 nextqueue에 모음 
	public static void destroy() {
	    nextqueue = new ArrayDeque<>();
	    int size = currentqueue.size(); // 현재 파도에 검사할 모래 개수를 고정
	    
	    for (int i = 0; i < size; i++) {
	        int[] cur = currentqueue.poll();
	        int r = cur[0];
	        int c = cur[1];
	        int cnt = search(r, c);
	        inQueue[r][c] = false;
	        if (sand[r][c] - '0' <= cnt) {
	            // 이번 파도에 무너짐
	            nextqueue.offer(new int[]{r, c});
	            // 다른 모래의 주변 검사에 중복으로 들어가지 않게 해줌
	            inQueue[r][c]=true;
	        }
	    }
	}
	
	// nextqueue에 들어있는 이번 파도에 무너질 모래의 좌표에 접근해 값을 실제로 '.'으로 만들고
	// 그 주변 모래들을 다음 파도의 검사 대상으로 추가 
	public static void collapse() {
		while(!nextqueue.isEmpty()) {
			int[] cur=nextqueue.poll();
			int r=cur[0];
			int c=cur[1];
			
			//실제로 무너뜨림
			sand[r][c]='.';
			
			//무너진 모래주변 8칸 확인
			for (int i=0;i<8;i++) {
				int nr=r+dr[i];
				int nc=c+dc[i];
				if (0<=nr && nr<H && 0<=nc && nc<W) {
					//중복 삽입 방지
					if (sand[nr][nc]!='.' && !inQueue[nr][nc]) {
						currentqueue.offer(new int[] {nr, nc});
						
						inQueue[nr][nc]=true;
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		int T=Integer.parseInt(br.readLine());
		for (int t=1;t<=T;t++) {
			answer=0;
			StringTokenizer st=new StringTokenizer(br.readLine());
			H=Integer.parseInt(st.nextToken());
			W=Integer.parseInt(st.nextToken());
			sand=new char[H][W];
			
			for(int h=0;h<H;h++) {
				String input = br.readLine();
				sand[h] = input.toCharArray();
			}
			
			currentqueue=new ArrayDeque<>();
			nextqueue=new ArrayDeque<>();
			inQueue = new boolean[H][W];
			
			//처음에만 모든 모래 검사해서 모래인 부분만 큐에 넣어줌 
			for (int i=0;i<H;i++) {
				for (int j=0;j<W;j++) {
					if (sand[i][j]!='.') {
						currentqueue.offer(new int[] {i,j});
						inQueue[i][j]=true;
					}
				}
			}
			
			
			while (true) {
				// 1. 이번 파도에 무너질 모래 좌표들을 queue에 수집
				destroy();

				// 2. 만약 큐가 비어있다면? (더 이상 무너질 모래가 없다는 뜻) -> 반복 종료
				if (nextqueue.isEmpty()) {
					break;
				}

				// 3. 큐에 들어있는 좌표들을 가져와서 실제로 '.'(물)로 변경
				collapse();

				// 4. 모래들이 한꺼번에 무너졌으므로 파도 횟수 1 증가
				answer++;
			}
		
		System.out.println("#"+t+" "+answer);
		}
		
	}

}
