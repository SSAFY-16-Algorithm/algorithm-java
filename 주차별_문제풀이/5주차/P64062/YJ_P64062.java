class Solution {
    public int solution(int[] stones, int k) {
                
        // 이분탐색으로 몇 명인지 맞춤
        int low = 1; 
        int high = 0; 
        // 초기 low: 1, 초기 high: stones[]에서 제일 큰 값 
        for(int stone: stones) {
            high = Math.max(high, stone);
        }
        
        int answer = 0; 
        
        while(low <= high) {
        	int mid = (low+high)/2;
        	
        	// 건널 수 있는 경우 -> 인원수를 늘려본다.  
        	if(canCross(stones, k, mid)) {
        		answer = mid;
        		low = mid+1; 
        	}
        	// 못 건너는 경우 -> 인원수를 줄인다.
        	else {
        		high = mid -1; 
        	}
        }

        return answer;
    }
    
    // people명 만큼 건널 수 있는지 
    public boolean canCross(int[] stones, int k, int people) {
        int cnt = 0; // 연속으로 밟을 수 없는 돌의 개수 
        
        for(int stone: stones) {
        	if(stone <people) {
        		cnt++; // 현재 설정한 인원보다 작은만큼을 계속 누적해서 센다. 
        	} else {
        		cnt = 0; // 큰 부분이 나오는 순간 연속은 깨짐.
        	}
        	
        	if(cnt >=k) {
        		return false;
        	}
        }
        
        return true;
        
    }
}