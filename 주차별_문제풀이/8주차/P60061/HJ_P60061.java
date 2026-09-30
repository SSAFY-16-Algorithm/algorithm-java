import java.util.*;

class Solution {

	int n; // 전체 벽면 길이
	boolean[][] column; // 기둥 설치 현황 배열
	boolean[][] beam; // 보 설치 현황 배열

	public int[][] solution(int n, int[][] build_frame) {

		this.n = n;
		List<int[]> result = new ArrayList<>();
		
		// 1. 기둥, 보 설치 배열 생성
		column = new boolean[n + 1][n + 1];
		beam = new boolean[n + 1][n + 1];
		
		// 2. build_frame을 처음부터 하나씩 확인하면서 설치 / 삭제 명령을 처리
		for (int[] command : build_frame) {
			
			int x = command[0];
			int y = command[1];
			int type = command[2];
			int action = command[3];
			
			// 설치
			if (action == 1) {
				
				if (type == 0) { // 기둥
					if(canInstallColumn(x, y)) {
						column[x][y] = true;
					}
				} else { // 보
					if(canInstallBeam(x, y)) {
						beam[x][y] = true;
					}
				}
			
			// 삭제
			} else {
				
				if (type == 0) { // 기둥
					// 일단 기둥 삭제
					column[x][y] = false;
					// 삭제 후 전체 구조물이 잘못되었다면 복구
					if(!isValidStructure()) {
						column[x][y] = true;
					}
				} else { // 보
					// 일단 보 삭제
					beam[x][y] = false;
					// 삭제 후 전체 구조물이 잘못되었다면 복구
					if(!isValidStructure()) {
						beam[x][y] = true;
					}
				}
				
			}
			
		}
		
		// 3. column, beam에 남아 있는 구조물을 [x, y, type]형태 int[][]로 만들어 반환
		for (int x = 0; x <= n; x++) {
			for (int y = 0; y <= n; y++) {
				if(column[x][y]) {
					result.add(new int[] {x, y, 0});
				}
				if(beam[x][y]) {
					result.add(new int[] {x, y, 1});
				}
			}
		}
		
		// List<int[]> -> int[][]로 변환
		int[][] answer = new int[result.size()][3];
		
		for(int i = 0; i < result.size(); i++) {
			answer[i] = result.get(i);
		}
		
		return answer;

	}
	
	// (x, y)에서 시작하는 기둥이 현재 구조물 상태에서 존재해도 되는지 확인
	boolean canInstallColumn(int x, int y) {
		
		// 1. 바닥 위에 세우는 경우
		if (y == 0) {
			return true;
		}
		
		// 2. 바로 아래에 기둥이 있는 경우
		if (column[x][y -1]) {
			return true;
		}
		
		// 3. 현재 위치에서 오른쪽으로 뻗는 보가 있는 경우
		if (beam[x][y]) {
			return true;
		}
		
		// 4. 왼쪽에서 현재 위치까지 이어지는 보가 있는 경우
		if (x > 0 && beam[x-1][y]) { // x == 0일 때 beam[-1][y]에 접근하지 않도록
			return true;
		}
		
		return false;
		
	}
	
	// (x, y)에서 시작하는 보가 현재 구조물 상태에서 존재해도 되는지 확인
	boolean canInstallBeam(int x, int y) {
		
		// 1. 왼쪽 끝 아래에 기둥이 있는 경우
		if (y > 0 && column[x][y-1]) {
			return true;
		}
		
		// 2. 오른쪽 끝 아래에 기둥이 있는 경우
		if (y > 0 && column[x+1][y-1]) {
			return true;
		}
		
		// 3. 양쪽에 보가 연결되어 있는 경우
		if (x > 0 && beam[x - 1][y] && beam[x+1][y]) {
			return true;
		}
		
		return false;
		
	}
	
	// 삭제 연산 시 이 구조물이 유지 가능한 구조물인지 확인
	boolean isValidStructure() {
		
		for (int x = 0; x <= n; x++) {
			for (int y = 0; y <= n; y++) {
				// 현재 위치에 기둥이 있는데 기둥 설치 조건을 만족하지 못하면
				if (column[x][y] && !canInstallColumn(x, y)) {
					return false;
				}
				// 현재 위치에 보가 있는데 보 설치 조건을 만족하지 못하면
				if (beam[x][y] && !canInstallBeam(x, y)) {
					return false;
				}
			}
		}
		
		return true;
		
	}

}