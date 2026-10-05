class Solution {
	
    public int[] solution(int[] sequence, int k) {
    	
    	int left = 0;
    	int right = 0;
    	int sum = sequence[0];
    	
    	int bestLeft = 0;
    	int bestRight = 0;
    	int bestLength = Integer.MAX_VALUE;
    	
    	while (right < sequence.length) {
    		
    		if (sum < k) {
    			
    			right++;
    			if (right == sequence.length) {
    				break;
    			}
    			sum += sequence[right];
    		
    		} else if (sum > k) {
    		
    			sum -= sequence[left];
    			left++;
    			
    		} else {
    			
    			int currentLength = right - left + 1;
    			
    			if (currentLength < bestLength) {
    				bestLength = currentLength;
    				bestLeft = left;
    				bestRight = right;
    			}
    			
    			sum -= sequence[left];
    			left++;
    			
    		}
    		
    	}
    	
    	int[] answer = new int[]{bestLeft, bestRight};
        
    	return answer;
    
    }

}