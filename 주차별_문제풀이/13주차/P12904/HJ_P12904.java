class Solution {
	
    public int solution(String s) {
    	
    	int answer = 1;
    	
    	for (int i = 0; i < s.length(); i++) {
    		
    		// 홀수 길이 팰린드롬 검사
    		int left = i - 1;
    		int right = i + 1;
    		int curLength = 1;
    		
    		while (left >= 0 && right < s.length()) {
    			if (s.charAt(left) == s.charAt(right)) {
    				curLength += 2;
    				left--;
    				right++;
    			} else {
    				break;
    			}
    		}
    		
    		answer = Math.max(answer, curLength);
    		
    		// 짝수 길이 팰린드롬 검사
    		left = i;
    		right = i + 1;
    		curLength = 0;
    		
    		while (left >= 0 && right < s.length()) {
    			if (s.charAt(left) == s.charAt(right)) {
    				curLength += 2;
    				left--;
    				right++;
    			} else {
    				break;
    			}
    		}
    		
    		answer = Math.max(answer, curLength);
    		
    	}

        return answer;
    
    }

}