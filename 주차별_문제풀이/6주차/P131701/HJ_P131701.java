import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// 연속 부분 수열 합의 개수 => 슬라이딩 윈도우로 접근
class Solution {
    
	public static int solution(int[] elements) {
    	
		// len 변수를 따로 설정한 이유 : elements 배열 자체를 2배로 늘리기 전에 기존의 길이를 기록해두기 위함
		int len = elements.length; // 슬라이딩 윈도우 제한 인덱스
		
		// elements 배열을 한번 더 이어 붙여서 원형 수열 형태로 만들기
    	elements = Arrays.copyOf(elements, len * 2);
    	System.arraycopy(elements, 0, elements, len, len);
    	
    	// elements 배열이 2배가 잘 되었는지 확인하기
    	System.out.println(Arrays.toString(elements));
    	
    	// 전체 연속 부분 수열 합들을 저장하고, 중복되는 것을 걸러내기 위해 set을 사용 
    	Set<Integer> set = new HashSet<>();
    	
    	// 확인할 부분 수열의 길이를 1부터 elements 전체 길이만큼 확인해야함.
    	// 길이가 1부터 기존 elements 배열의 길이만큼 연속 부분 수열을 설정할 수 있기 때문
    	for (int i = 1; i <= len; i++) {
    		
    		// i 길이만큼 이제 elements 수열을 돌면서 확인해야 한다.
    		// 먼저 i 길이만큼의 구간합을 만든다.
    		int windowSum = 0;
    		for (int j = 0; j < i; j++) {
    			windowSum += elements[j];
    		}
    		    		
    		// 처음 구간합을 set에 넣어주기
    		set.add(windowSum);
    		
    		// 길이 i만큼 이제 elements 수열을 돌면서 확인해야 한다.
    		for (int right = i; right < len+i-1; right++) {
    			// 슬라이딩 윈도우가 1칸씩 오른쪽으로 이동할 때 새로 들어오는 값을 windowSum에 더해줌
    			windowSum += elements[right];
    			// 슬라이딩 윈도우가 1칸씩 오른쪽으로 이동하면 기존의 가장 왼쪽에 있던 값이 슬라이딩 윈도우에서 빠짐
    			windowSum -= elements[right - i];
    			set.add(windowSum);
    		}
    		
    	}
    	
    	// 문제에서 원하는 정답은 연속 부분 수열로부터 나올 수 있는 합 숫자의 개수
        int answer = set.size();
        
        return answer;
    }
}