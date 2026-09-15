import java.util.*;

public class P67258 {
    // 모든 보석을 포함하는 가장 짧은 구간
	public int[] solution(String[] gems) {
		int[] answer = new int[2];
		
		Set<String> total = new HashSet<>();
		// 1. 전체 보석의 종류 세기 
		for(String gem: gems) {
			total.add(gem);
		}
		
		int totalType = total.size();
		
		// 2. start = 0, end = 0으로 설정. 
		int start = 0; 
		int end = 0; 
		int minLength = gems.length+1;
		
		
		// 현재 구간 내의 보석 정보 
		Map<String, Integer> map = new HashMap<>();
		
		while(end<gems.length) {
			// 3. end를 오른쪽으로 이동 -> map에 세기
			map.put(gems[end], map.getOrDefault(gems[end], 0)+1);
			end ++;
			// 4. map에 있는 보석 개수가 전체 개수 와 동일하면 구간을 찾은 것. 
			if(map.size() == totalType) {				
				// 5. start 위치에 있는 보석이 >1 이면 start 오른쪽으로 이동
				while(map.get(gems[start]) >1) {					
					map.put(gems[start], map.get(gems[start])-1);
					start++;
				}
				// 5의 과정을 반복하고 더 이상 줄일 수 없을 때 구간 길이가 최소 길이보다 짧으면 update
				int len = end - start; // 이미 end 증가되어있으므로 +1 하지 않아도 됨. 
				if(len < minLength) {
					minLength = len;
					answer[0] = start;
					answer[1] = end-1;
				}
				// 6. start+1해서 시작 (map에서도 제거 필요)
				map.put(gems[start], map.get(gems[start])-1);
				// 사이즈 0개면 제거
				if(map.get(gems[start]) == 0) {
					map.remove(gems[start]);
				}
				start++; 
			}
		}
		
		answer[0]+=1;
		answer[1]+=1;
		// 인덱스 +1씩 해서 return 하기 (1부터 시작) 
		return answer;
	}
}