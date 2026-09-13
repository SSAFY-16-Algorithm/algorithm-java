import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    
	public int[] solution(String[] gems) {

		// gems 배열에서 보석 전체 종류 개수 구하기 -> Set 이용
		// 보석 종류가 모두 포함되었는지 확인하기 위함
		Set<String> gemTypes = new HashSet<>();
		
		for (String gem : gems) {
			gemTypes.add(gem);
		}
		
		// gems 배열에 존재하는 보석의 종류
		int totalTypes = gemTypes.size();
		
		// gems 배열을 앞에서부터 순회하면서 등장한 gems를 저장하고 관리하기 -> HashMap 사용
		Map<String, Integer> window = new HashMap<>();
		
		int left = 0;
		int right = 0;
		
		int bestLeft = 0;
		int bestRight = 0;
		int bestLength = Integer.MAX_VALUE;
				
		
		while (right < gems.length) {
			
			String Gem = gems[right];
			window.put(Gem, window.getOrDefault(Gem, 0)+1);
			
			while (window.size()==totalTypes) {
				int currentLength = right - left + 1;
				if (currentLength < bestLength) {
					bestLength = currentLength;
					bestLeft = left;
					bestRight = right;
				}
				String leftGem = gems[left];
				window.put(leftGem, window.get(leftGem) - 1);
				if (window.get(leftGem) == 0) {
					window.remove(leftGem);
				}
				left++;
			}
			
			right++;
			
		}
		
		int[] answer = {bestLeft+1, bestRight+1};
        return answer;
    
	}

}
