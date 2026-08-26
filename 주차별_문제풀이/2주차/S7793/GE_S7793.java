import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
    private static final int[] DR = {1, 0, -1, 0};
    private static final int[] DC = {0, 1, 0, -1};
    
    private static final char SUYEON_SIGN = 'S';
    private static final char GODDESS_SIGN = 'D';
    private static final char DEVIL_SIGN = '*';
    private static final char ROCK_SIGN = 'X';
    private static final char ROAD_SIGN = '.';

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int T =  Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            sb.setLength(0);
            sb.append('#').append(test_case).append(' ');

            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            char[][] map = new char[N][M];
            int[] suyeon = new int[2];
            Queue<int[]> devilQueue = new LinkedList<>();

            for (int r = 0; r < N; r++) {
                String row = br.readLine();
                for (int c = 0; c < M; c++) {
                    map[r][c] = row.charAt(c);

                    if (map[r][c] == SUYEON_SIGN) {
                        suyeon[0] = r;
                        suyeon[1] = c;
                    }
                    else if (map[r][c] == DEVIL_SIGN) {
                    	devilQueue.offer(new int[] {r, c});
                    }
                }
            }
            
            int result = escape(map, suyeon, devilQueue);
            sb.append(result < 0 ? "GAME OVER" : result).append('\n');
            
            bw.write(sb.toString());
            bw.flush();
        }
        
        br.close();
        bw.close();
    }

    private static int escape(char[][] map, int[] suyeonStart, Queue<int[]> devilQueue) {
    	int rows = map.length;
    	int cols = map[0].length;
    	
    	Queue<int[]> suyeonQueue = new LinkedList<>();
    	boolean[][] suyeonVisited = new boolean[rows][cols];
        
        suyeonQueue.offer(suyeonStart);
        suyeonVisited[suyeonStart[0]][suyeonStart[1]] = true;
        map[suyeonStart[0]][suyeonStart[1]] = ROAD_SIGN;
        
        int time = 0;

        while (!suyeonQueue.isEmpty()) {
            time++;
            
            int devilLevelSize = devilQueue.size();
            for (int i = 0; i < devilLevelSize; i++) {
            	int[] current = devilQueue.poll();
            	
            	for (int dir = 0; dir < 4; dir++) {
            		int nextR = current[0] + DR[dir];
            		int nextC = current[1] + DC[dir];
            		
            		if (isOutOfBounds(nextR, nextC, rows, cols)) continue;
            		
            		if (map[nextR][nextC] == ROAD_SIGN) {
            			map[nextR][nextC] = DEVIL_SIGN;
            			devilQueue.offer(new int[] {nextR, nextC});
            		}
            	}
            }
        	
        	int suyeonLevelSize = suyeonQueue.size();
        	for (int i = 0; i < suyeonLevelSize; i++) {
        		int[] current = suyeonQueue.poll();
        		
        		for (int dir = 0; dir < 4; dir++) {
                    int nextR = current[0] + DR[dir];
                    int nextC = current[1] + DC[dir];
                    
            		if (isOutOfBounds(nextR, nextC, rows, cols)) continue;
                    if (suyeonVisited[nextR][nextC]) continue;
                    if (map[nextR][nextC] == DEVIL_SIGN || map[nextR][nextC] == ROCK_SIGN) continue;
            		if (map[nextR][nextC] == GODDESS_SIGN) return time;
                    
                    suyeonQueue.offer(new int[] {nextR, nextC});
                    suyeonVisited[nextR][nextC] = true;
                }
        	}
        }
        
        return -1;
    }
    
    private static boolean isOutOfBounds(int r, int c, int rows, int cols) {
    	return r < 0 || c < 0 || r >= rows || c >= cols;
    }
}