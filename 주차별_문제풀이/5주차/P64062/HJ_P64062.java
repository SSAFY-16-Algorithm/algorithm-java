class Solution {
	
    public int solution(int[] stones, int k) {
    	
    	int low = 1;
    	int high = 0;
    	for (int stone : stones) {
    		high = Math.max(high, stone);
    	}
    	
    	while (low <= high) {
    		
    		int mid = (low + high) / 2;
    		
    		if (canCross(stones, k, mid)) {
    			low = mid + 1;
    		} else {
    			high = mid - 1;
    		}
    		
    	}
    	
    	int answer = high;
        
    	return answer;
    
    }
    
    private boolean canCross(int[] stones, int k, int people) {
    	
    	int count = 0;
    	
    	for (int stone : stones) {
    		
    		if (stone < people) {
    			count++;
    		} else {
    			count = 0;
    		}
    		
    		if (count == k) {
    			return false;
    		}
    	
    	}
    	
    	return true;
    
    }
    
}