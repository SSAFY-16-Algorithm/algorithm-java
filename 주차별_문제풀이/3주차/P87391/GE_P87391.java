class Solution {
    public long solution(int n, int m, int x, int y, int[][] queries) {
        long minX = x, maxX = x;
        long minY = y, maxY = y;
        
        for (int i = queries.length - 1; i >= 0; i--) {
            int dir = queries[i][0];
            int dist = queries[i][1];
            
            if (dir == 0) {
                long min = (minY == 0) ? 0 : minY + dist;
                if (min > m - 1) return 0;
                
                minY = min;
                maxY = Math.min(maxY + dist, m - 1);
            } else if (dir == 1) {
                long max = (maxY == m - 1) ? m - 1 : maxY - dist;
                if (max < 0) return 0;
                
                minY = Math.max(minY - dist, 0);
                maxY = max;
            } else if (dir == 2) {
                long min = (minX == 0) ? 0 : minX + dist;
                if (min > n - 1) return 0;
                
                minX = min;
                maxX = Math.min(maxX + dist, n - 1);
            } else if (dir == 3) {
                long max = (maxX == n - 1) ? n - 1 : maxX - dist;
                if (max < 0) return 0;
                
                minX = Math.max(minX - dist, 0);
                maxX = max;
            }
        }
        return (maxX - minX + 1) * (maxY - minY + 1);
    }
}