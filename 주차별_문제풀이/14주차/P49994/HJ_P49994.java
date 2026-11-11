import java.util.*;

class Solution {
	
	public int solution(String dirs) {
		
		int answer = 0;
		
		int x = 0;
		int y = 0;
		
		// 이미 지나간 경로를 저장하는 HashSet
		Set<String> visited = new HashSet<>();
		
		// 명령어를 하나씩 확인
		for (int i = 0; i < dirs.length(); i++) {
			
			char dir = dirs.charAt(i);
			
			// 다음 이동할 좌표
			int nx = x;
			int ny = y;
			
			// 명령어에 따른 이동
			if (dir == 'U') {
				ny++;
			} else if (dir == 'D') {
				ny--;
			} else if (dir == 'R') {
				nx++;
			} else if (dir == 'L') {
				nx--;
			}
			
			// 좌표평면의 경계를 벗어나면 이동 무시
            if (nx < -5 || nx > 5 || ny < -5 || ny > 5) {
                continue;
            }
            
            // 현재 좌표에서 다음 좌표로 이동하는 경로
            String path = x + "," + y + "," + nx + "," + ny;

            // 반대 방향으로 이동하는 경로
            String reversePath = nx + "," + ny + "," + x + "," + y;
            
            // 처음 지나가는 길인 경우
            if (!visited.contains(path)) {

                answer++;

                // 정방향과 역방향 모두 방문 처리
                visited.add(path);
                visited.add(reversePath);
            }

            // 기존에 지나간 길이어도 실제 이동은 수행
            x = nx;
            y = ny;
        }

        return answer;
		
	}
	
}