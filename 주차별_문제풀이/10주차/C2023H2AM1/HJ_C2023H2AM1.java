import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static class Knight {

		int r;
		int c;
		int h;
		int w;
		int hp; // 현재 체력
		int initialHp; // 초기 체력 (문제 최종 정답에서 생존한 기사들이 누적된 데미지를 구해야 하기 때문)

		public Knight(int r, int c, int h, int w, int k) {
			super();
			this.r = r;
			this.c = c;
			this.h = h;
			this.w = w;
			this.hp = k;
			this.initialHp = k;
		}

	}

	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int L = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());

		// 체스판 입력 (0 : 빈칸 / 1 : 함정 / 2 : 벽)
		int[][] map = new int[L][L];
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < L; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 초기 기사 정보 입력
		Knight[] knights = new Knight[N + 1];

		// ((r, c) : 좌측 상단 꼭지점 / h : 기사의 세로 길이 / w : 기사의 가로 길이 / k : 기사 초기 체력)
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());

			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());

			knights[i] = new Knight(r, c, h, w, k);
		}

		// 왕의 명령 정보 입력 후 바로바로 시뮬레이션 (이전 명령이 다음 명령에 영향을 끼치기 때문)
		// (i : 기사 번호 / d : 기사가 한 칸 이동하는 방향)
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());

			moveKnight(i, d, knights, map);
		}
		
		// 살아 있는 기사들이 받은 총 대미지 계산
		int answer = 0;
		
		for (int i = 1; i <= N; i++) {

		    if (knights[i].hp > 0) {
		        answer += knights[i].initialHp - knights[i].hp;
		    }
		}

		System.out.println(answer);

	}

	static void moveKnight(int i, int d, Knight[] knights, int[][] map) {
		
		// 0. 명령받은 기사가 이미 사망했다면 아무것도 하지 않음
	    if (knights[i].hp <= 0) {
	        return;
	    }
	    
		Queue<Integer> queue = new ArrayDeque<>();
		boolean[] willMove = new boolean[knights.length];

		// 명령받은 기사부터 검사
		queue.offer(i);
		willMove[i] = true;
		
		while (!queue.isEmpty()) {

		    int current = queue.poll();

		    // 1. current번 기사가 벽에 막히는지 확인
		    if (isBlocked(knights[current], d, map)) {
		    	return;
		    }
		    
		    // 2. 다른 살아 있는 기사들과 충돌하는지 확인
		    for (int j = 1; j < knights.length; j++) {
		    	
		    	// 자기 자신이면 건너뛰기
		        if (j == current) {
		            continue;
		        }

		        // 사망한 기사라면 건너뛰기
		        if (knights[j].hp <= 0) {
		            continue;
		        }

		        // 이미 이동 대상에 포함된 기사라면 건너뛰기
		        if (willMove[j]) {
		            continue;
		        }
		        
		        // 현재 기사가 이동하면 j번 기사와 겹치는지 확인
		        if (isOverlapping(knights[current], knights[j], d)) {

		        	// j번 기사도 이동 대상으로 등록
	                willMove[j] = true;
	                queue.offer(j);
		        }
		    }
		}
		
		 // 2. 모든 기사가 이동 가능하므로 실제 위치 갱신
	    for (int j = 1; j < knights.length; j++) {

	        if (!willMove[j]) {
	            continue;
	        }

	        knights[j].r += dr[d];
	        knights[j].c += dc[d];
	    }

	    // 3. 이동 후 함정 대미지 처리
	    for (int j = 1; j < knights.length; j++) {

	        // 명령받은 기사는 대미지를 받지 않음
	        if (j == i) {
	            continue;
	        }

	        // 이번 명령에서 이동하지 않은 기사는 제외
	        if (!willMove[j]) {
	            continue;
	        }

	        Knight knight = knights[j];
	        int damage = 0;

	        // 이동한 기사가 차지하는 직사각형 내부의 함정 개수 계산
	        for (int r = knight.r; r < knight.r + knight.h; r++) {
	            for (int c = knight.c; c < knight.c + knight.w; c++) {

	                if (map[r][c] == 1) {
	                    damage++;
	                }
	            }
	        }

	        // 함정 개수만큼 체력 감소
	        knight.hp -= damage;
	    }
		
	}

	static boolean isBlocked(Knight knight, int d, int[][] map) {

		int nr = knight.r + dr[d];
		int nc = knight.c + dc[d];

		int L = map.length;

		for (int i = nr; i < nr + knight.h; i++) {
			for (int j = nc; j < nc + knight.w; j++) {
				if (i < 0 || i >= L || j < 0 || j >= L) {
					return true;
				}

				if (map[i][j] == 2) {
					return true;
				}
			}
		}

		return false;

	}

	static boolean isOverlapping(Knight a, Knight b, int d) {

		int nr = a.r + dr[d];
		int nc = a.c + dc[d];

		if (nr + a.h <= b.r || nc + a.w <= b.c) {
			return false;
		}
		if (nr >= b.r + b.h || nc >= b.c + b.w) {
			return false;
		}

		return true;

	}

}