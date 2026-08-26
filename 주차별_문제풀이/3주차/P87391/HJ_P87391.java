public class Solution {
	public long solution(int n, int m, int x, int y, int[][] queries) {
		
        long minRow = x;
        long maxRow = x;
        long minCol = y;
        long maxCol = y;
        
        for (int i = queries.length - 1; i >= 0; i--) {
        	int command = queries[i][0]; 
        	int dis = queries[i][1];
        	
        	if (command == 0) {
        		if (minCol != 0) {
        			minCol += dis;
        		}
        		maxCol = Math.min(m-1, maxCol+dis);
        	} else if (command == 1) {
        		if (maxCol != m-1) {
        			maxCol -= dis;
        		}
        		minCol = Math.max(0, minCol-dis);
        	} else if (command == 2) {
        		if (minRow != 0) {
        			minRow += dis;
        		}
        		maxRow = Math.min(n-1, maxRow+dis);
        	} else if (command == 3) {
        		if (maxRow != n-1) {
        			maxRow -= dis;
        		}
        		minRow = Math.max(0, minRow-dis);
        	}
        	
        	if (minRow > maxRow || minCol > maxCol) {
        		return 0;
        	}
        }
        
        long row = maxRow - minRow + 1;
        long col = maxCol - minCol + 1;
        long answer = row * col;
        return answer;
    }
}