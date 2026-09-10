import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    
	public static int solution(int[] elements) {
    	
		int len = elements.length; // 슬라이딩 윈도우 제한 인덱스
		// elements 배열을 한번 더 이어 붙여서 원형 수열 형태로 만들기
    	elements = Arrays.copyOf(elements, len * 2);
    	System.arraycopy(elements, 0, elements, len, len);
    	
    	System.out.println(Arrays.toString(elements));
    	
    	Set<Integer> set = new HashSet<>();
    	
    	// 확인할 부분 수열의 길이를 1부터 elements 전체 길이만큼 확인해야함.
    	for (int i = 1; i <= len; i++) {
    		
    		// i 길이만큼 이제 elements 수열을 돌면서 확인해야 한다.
    		// 먼저 i 길이만큼의 구간합을 만든다.
    		int windowSum = 0;
    		for (int j = 0; j < i; j++) {
    			windowSum += elements[j];
    		}
    		
    		//System.out.println(windowSum);
    		
    		// 처음 구간합을 set에 넣어주기
    		set.add(windowSum);
    		
    		// i 길이만큼 이제 elements 수열을 돌면서 확인해야 한다.
    		for (int right = i; right < len+i-1; right++) {
    			windowSum += elements[right];
    			windowSum -= elements[right - i];
    			//System.out.println(windowSum);
    			set.add(windowSum);
    		}
    		
    		//System.out.println(set.toArray());
    	}
    	
        int answer = set.size();
        
        return answer;
    }
}