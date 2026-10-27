class Solution {
    
	public int solution(int n) {
    	
		int[] tiles = new int[n+1];
		
		if (n <= 2) {
			return n;
		}
        
        tiles[1] = 1;
        tiles[2] = 2;
        
        for (int i = 3; i <= n; i++){
            tiles[i] = tiles[i-1] + tiles[i-2];
		    tiles[i] = tiles[i] % 1_000_000_007;
        }
		
        int answer = tiles[n];
        return answer;
    
	}

}