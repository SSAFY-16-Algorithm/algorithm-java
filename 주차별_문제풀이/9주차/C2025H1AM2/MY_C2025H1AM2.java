import java.io.*;
import java.util.*;

public class MY_C2025H1AM2 {
	
    static int[] dr = {-1, 0, 0, 1};
    static int[] dc = {0, -1, 1, 0};
    static int power = 1, N, Q, result;
    static final int maxPower = 5;

    static char[][] arr;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        arr = new char[N][N];

        for (int i = 0; i < N; i++) {
            arr[i] = br.readLine().toCharArray();
        }

        Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken()) - 1;
            int c1 = Integer.parseInt(st.nextToken()) - 1;
            int r2 = Integer.parseInt(st.nextToken()) - 1;
            int c2 = Integer.parseInt(st.nextToken()) - 1;

            result = -1;
            move(r1, c1, r2, c2);
            sb.append(result).append('\n');
        }

        System.out.println(sb.toString());
    }

    public static void move(int r1, int c1, int r2, int c2) {
        // System.out.println("새로운 방법 시도");
        Queue<int[]> dq = new PriorityQueue<>(
            Comparator.comparingInt(a -> a[3])
            );
        // GPT: 한 좌표에 여러 번 도착할 수 있으니
        // 같은 파워로 한 좌표에 도착했을 때
        // 이미 더 빨리 도착한 경우가 있다면 굳이 큐에 넣지 않기 위해
        // dist 배열 선언
        int[][][] dist = new int[50][50][6]; // power 0은 사용 x
        for (int r = 0; r < 50; r++) {
            for (int c = 0; c < 50; c++) {
                for (int power = 1; power <= 5; power++) {
                    dist[r][c][power] = Integer.MAX_VALUE;
                }
            }
        }
        dq.offer(new int[] {r1, c1, 1, 0});
        // FIX: 시작점도 dist에 추가해야함
        dist[r1][c1][1] = 0;
        
        while(!dq.isEmpty()) {
            int[] frog = dq.poll();
            int r = frog[0];
            int c = frog[1];
            int curPower = frog[2];
            int curTime = frog[3];

            // 이 때 꺼낸게 도착지점이라면 최적임을 보장할 수 있음?
            if (r == r2 && c == c2) {
                result = curTime;
                // System.out.println(result + "에 도착");
                break;
            }

            int[][] jumpTimes = new int[5][3];
            int jtIdx = 0;
            // 지금 파워
            jumpTimes[jtIdx++] = new int[] {curPower, curTime + 1};

            int increasedTime = curTime;
            // 증가
            for (int i = curPower; i < 5; i++) {
                int increasedPower = i + 1;
                increasedTime += increasedPower*increasedPower;
                jumpTimes[jtIdx++] = 
                    new int[] {increasedPower, increasedTime + 1};
            }
            // 감소
            for (int i = curPower; i > 1; i--) {
                int decreasedPower = i - 1;
                jumpTimes[jtIdx++] =
                    new int[] {decreasedPower, curTime + 2};
            }

            for (int d = 0; d < 4; d++) {
                for (int[] powers: jumpTimes) {
                    int nextPower = powers[0];
                    int nextTime = powers[1];

                    int nr = r + dr[d] * nextPower;
                    int nc = c + dc[d] * nextPower;

                    if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                    // 미끄러운 돌이면 패스
                    if (arr[nr][nc] == 'S') continue;

                    // 가로, 세로 천적이 있는지 체크
                    boolean isAvailable = true;
                    for (int i = r + 1; i <= nr; i++) {
                        if (arr[i][nc] == '#') {
                            isAvailable = false;
                            break;
                        }
                    }
                    for (int i = r - 1; i >= nr; i--) {
                        if (arr[i][nc] == '#') {
                            isAvailable = false;
                            break;
                        }
                    }
                    for (int i = c + 1; i <= nc; i++) {
                        if (arr[nr][i] == '#') {
                            isAvailable = false;
                            break;
                        }
                    }
                    for (int i = c - 1; i >= nc; i--) {
                        if (arr[nr][i] == '#') {
                            isAvailable = false;
                            break;
                        }
                    }

                    if (!isAvailable) continue;

                    // 이미 더 빠르게 방문했다면 제외
                    // FIX: 같은 시간도 또 방문할 필요 없음
                    if (dist[nr][nc][nextPower] <= nextTime) continue;
                    // System.out.println(nr + 1 + " " + (nc + 1) + ": 갈 수 있음");
                    dist[nr][nc][nextPower] = nextTime;
                    dq.offer(new int[] {nr, nc, nextPower, nextTime});
                }
                if (result > 0) break;
            }
            if (result > 0) break;
        }
    }
}
