class Solution {
    public long solution(int n, int m, int x, int y, int[][] queries) {        
        // 목적지가 시작점이 됨
        int minR = x;
        int maxR = x;
        int minC = y;
        int maxC = y;
        
        // 마지막 쿼리부터 역방향으로 쿼리를 꺼내옴
        for (int i=queries.length-1;i>=0;i--){
            int command=queries[i][0];
            int dx=queries[i][1];
            
            if (command == 0) { 
                // 실제: 왼쪽, 역방향: 오른쪽

                if (minC == 0) { //벽에 부딪혀서 이동 전과 후가 같을 수 있으므로 이 경우 minC는 건드리지 않음 
                    maxC += dx;
                } else { //현위치가 0이 아니면 둘 다 늘려줌 
                    minC += dx;
                    maxC += dx;
                    if (minC >= m) return 0; 
                }
                maxC = Math.min(maxC, m - 1); //범위 초과하지 않게 

            } else if (command == 1) { 
                // 실제: 오른쪽, 역방향: 왼쪽
                if (maxC == m - 1) { //벽에 부딪혀서 이동 전후가 같을 수 있으므로 이 경우 maxC는 건드리지 않음
                    minC -= dx;
                } else { 
                    minC -= dx;
                    maxC -= dx;
                    if (maxC < 0) return 0;
                }
                minC = Math.max(minC, 0); //범위 초과하지 않게 

            } else if (command == 2) { 
                // 실제: 위, 역방향: 아래
                if (minR == 0) {
                    maxR += dx;                    
                } else {
                    minR += dx;
                    maxR += dx;
                    if (minR >= n) return 0;
                }
                maxR = Math.min(maxR, n - 1);//범위 초과하지 않게 

            } else if (command == 3) { 
                // 실제: 아래, 역방향: 위
                if (maxR == n - 1) {
                    minR -= dx;
                } else {
                    minR -= dx;
                    maxR -= dx;
                    if (maxR < 0) return 0;
                }
                minR = Math.max(minR, 0);
            }
        }
        return (long)(maxR - minR + 1) * (maxC - minC + 1);
    }
}
