import java.util.*;

public class P60061 {
	static int n; 
	static boolean[][] pillar; // 기둥 여부 
	static boolean[][] beam; // 보 여부
	
	public static void main(String[] args) {
		int[][] build_frame = {{1,0,0,1},{1,1,1,1},{2,1,0,1},{2,2,1,1},{5,0,0,1},{5,1,0,1},{4,2,1,1},{3,2,1,1}};
		int n = 5;
		int[][] answer = solution(n, build_frame);
		
		for(int i=0; i<answer.length; i++) {
			for(int j=0; j<answer[0].length; j++) {
				System.out.print(answer[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static int[][] solution(int n, int[][] build_frame) {
        
        pillar = new boolean[n + 1][n + 1];
        beam = new boolean[n + 1][n + 1];

        
        // 설치, 삭제 작업 수행
        for (int[] frame : build_frame) {
            int x = frame[0];
            int y = frame[1];
            int type = frame[2];   // 0: 기둥, 1: 보
            int action = frame[3]; // 0: 삭제, 1: 설치

            if (type == 0) { // 기둥
                if (action == 1) { // 설치
                    pillar[x][y] = true;
                    if (!isValid()) {
                        pillar[x][y] = false; // 원상복구
                    }
                } else { // 삭제
                    pillar[x][y] = false;
                    if (!isValid()) {
                        pillar[x][y] = true; // 원상복구
                    }
                }
            } else { // 보
                if (action == 1) { // 설치
                    beam[x][y] = true;
                    if (!isValid()) {
                        beam[x][y] = false; // 원상복구
                    }
                } else { // 삭제
                    beam[x][y] = false;
                    if (!isValid()) {
                        beam[x][y] = true; // 원상복구
                    }
                }
            }
        }

        List<int[]> resultList = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
            	// 기둥이라면
                if (pillar[i][j]) {
                    resultList.add(new int[]{i, j, 0});
                }
                // 보라면 
                if (beam[i][j]) {
                    resultList.add(new int[]{i, j, 1});
                }
            }
        }

        return resultList.toArray(new int[resultList.size()][]);
    }

    // 전체 구조물이 유효한지 검사
	public static boolean isValid() {
        for (int x = 0; x <= n; x++) {
            for (int y = 0; y <= n; y++) {
                // 기둥 검사
                if (pillar[x][y] && !checkPillar(x, y)) {
                    return false;
                }
                // 보 검사
                if (beam[x][y] && !checkBeam(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    // (x, y)에서 기둥 조건 검사
    public static boolean checkPillar(int x, int y) {
        // 바닥 위인 경우
        if (y == 0) return true;
        
        // 아래에 기둥이 있는 경우
        if (y - 1 >= 0 && pillar[x][y - 1]) return true;
        
        // 보의 한쪽 끝부분 위에 있는 경우
        if ((x - 1 >= 0 && beam[x - 1][y]) || beam[x][y]) return true;

        return false;
    }

    // (x, y)의 보 조건 검사
    public static boolean checkBeam(int x, int y) {
        // 한쪽 끝부분이 기둥 위에 있는 경우
        if (y - 1 >= 0) {
            if (pillar[x][y - 1]) return true;
            if (x + 1 <= n && pillar[x + 1][y - 1]) return true;
        }
        
        // 양쪽 끝부분이 다른 보와 동시에 연결되어 있는 경우
        if (x - 1 >= 0 && x + 1 <= n && beam[x - 1][y] && beam[x + 1][y]) return true;

        return false;
    }
}
