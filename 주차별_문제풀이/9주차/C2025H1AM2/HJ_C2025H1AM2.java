import java.io.*;
import java.util.*;

public class Main {

    static final int MAX_JUMP = 5, INF = 1_000_000_000;
    static int N;
    static char[][] map;
    static int[][][][] nextPos;
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static class State implements Comparable<State> {
        int r, c, jump, time;

        State(int r, int c, int jump, int time) {
            this.r = r; this.c = c; this.jump = jump; this.time = time;
        }

        public int compareTo(State other) {
            return Integer.compare(this.time, other.time);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        map = new char[N][N];

        for (int r = 0; r < N; r++) {
            String row = br.readLine();
            for (int c = 0; c < N; c++) map[r][c] = row.charAt(c);
        }

        // 각 위치 / 점프력 / 방향별 점프 가능 위치를 미리 계산
        buildJumpTable();

        int Q = Integer.parseInt(br.readLine());

        while (Q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int r1 = Integer.parseInt(st.nextToken()) - 1;
            int c1 = Integer.parseInt(st.nextToken()) - 1;
            int r2 = Integer.parseInt(st.nextToken()) - 1;
            int c2 = Integer.parseInt(st.nextToken()) - 1;

            output.append(dijkstra(r1, c1, r2, c2)).append('\n');
        }

        System.out.print(output);
    }

    // 가능한 점프 위치 전처리
    static void buildJumpTable() {
        nextPos = new int[N][N][MAX_JUMP + 1][4];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                for (int jump = 1; jump <= MAX_JUMP; jump++) {
                    Arrays.fill(nextPos[r][c][jump], -1);

                    for (int d = 0; d < 4; d++) {
                        int nr = r, nc = c;
                        boolean possible = true;

                        // #을 지나거나 범위를 벗어나면 점프 불가능
                        for (int step = 1; step <= jump; step++) {
                            nr += dr[d]; nc += dc[d];

                            if (!inRange(nr, nc) || map[nr][nc] == '#') {
                                possible = false;
                                break;
                            }
                        }

                        if (!possible || map[nr][nc] == 'S') continue;

                        nextPos[r][c][jump][d] = nr * N + nc;
                    }
                }
            }
        }
    }

    // (행, 열, 점프력)을 상태로 사용하는 다익스트라
    static int dijkstra(int sr, int sc, int tr, int tc) {
        if (sr == tr && sc == tc) return 0;

        int[][][] dist = new int[N][N][MAX_JUMP + 1];

        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++)
                Arrays.fill(dist[r][c], INF);

        PriorityQueue<State> pq = new PriorityQueue<>();

        dist[sr][sc][1] = 0;
        pq.offer(new State(sr, sc, 1, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();

            int r = cur.r, c = cur.c;
            int jump = cur.jump, time = cur.time;

            if (time != dist[r][c][jump]) continue;
            if (r == tr && c == tc) return time;

            // 1. 현재 점프력으로 상하좌우 점프
            for (int d = 0; d < 4; d++) {
                int pos = nextPos[r][c][jump][d];
                if (pos == -1) continue;

                int nr = pos / N, nc = pos % N;
                int nextTime = time + 1;

                if (nextTime < dist[nr][nc][jump]) {
                    dist[nr][nc][jump] = nextTime;
                    pq.offer(new State(nr, nc, jump, nextTime));
                }
            }

            // 2. 점프력 증가
            if (jump < MAX_JUMP) {
                int nextJump = jump + 1;
                int nextTime = time + nextJump * nextJump;

                if (nextTime < dist[r][c][nextJump]) {
                    dist[r][c][nextJump] = nextTime;
                    pq.offer(new State(r, c, nextJump, nextTime));
                }
            }

            // 3. 점프력 감소
            for (int nextJump = 1; nextJump < jump; nextJump++) {
                int nextTime = time + 1;

                if (nextTime < dist[r][c][nextJump]) {
                    dist[r][c][nextJump] = nextTime;
                    pq.offer(new State(r, c, nextJump, nextTime));
                }
            }
        }

        return -1;
    }

    static boolean inRange(int r, int c) {
        return 0 <= r && r < N && 0 <= c && c < N;
    }
}