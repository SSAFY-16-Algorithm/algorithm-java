public class Solution {
	
	public int solution(String name) {
        
		// 문자열 길이
		int n = name.length();
		
		// ====================== <문제 풀이 아이디어> ======================
        // 1. 조이스틱 위, 아래 (알파벳이 A와 Z 사이에서 어디에 더 가까운가?
		// 2. 조이스틱 왼쪽, 오른쪽 (A 아닌 것들은 가서 바꿔야 됨. A의 위치만 파악하면 됨)
		int alphaCount = 0;
		int moveCount = n - 1; // 탐색하기 전 좌우 이동 방법 중 최소 이동 횟수 (그냥 앞에서부터 뒤로)
		
		for (int i = 0; i < n; i++) {
			
			// 아이디어 1 구현
			char c = name.charAt(i);
			int up = c - 'A';
			int down = 'Z' - c + 1;
			alphaCount += Math.min(up, down);
		
			// 아이디어 2 구현
			int next = i + 1; // A가 연속으로 얼마나 나오는지 확인하기 위한 변수
			
			// 문자열 끝까지 돌면서 
			while(next < n && name.charAt(next) == 'A') {
				next++;
			}
			
			int route1 = 2 * i + (n - next);
			int route2 = 2 * (n - next) + i;
			
			moveCount = Math.min(moveCount, Math.min(route1, route2));
			
		}
		
		int answer = alphaCount + moveCount;
		
		return answer;
    
	}
	
}