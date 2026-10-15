import java.util.*;
import java.io.*;

public class MY_C2023H2AM1 {
    // 상, 우, 하, 좌
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    static class Knight {
        int idx, r, c, h, w, k, demage;
        Knight (int i, int r, int c, int h, int w, int k) {
            idx = i;
            this.r = r;
            this.c = c;
            this.h = h;
            this.w = w;
            this.k = k;
        }
    }

    static int L, N, Q;
    static int[][] arr;
    static Map<Integer, Knight> knights = new HashMap<>();
    static List<Integer> pits = new ArrayList<>();
    static List<Integer> walls = new ArrayList<>();
    static boolean canMove;

    public static void main(String[] args) throws Exception {
        // L * L 은 최대 1600, 병사는 최대 30이므로
        // 좌표 검사는 병사를 전부 순회해서 검사하는게 낫다고 판단됨
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        // 두 개의 이동 범위 혹은 이동 범위와 함정이 겹치는지 확인하는 방법
        // 어떤 범위의 시작이 끝보다 작고, 끝이 시작보다 크면 됨
        // 따라서 배열에 저장할 필요는 없음
        // 그것보다는 병사와 함정을 한번씩 다 순회하는게 낫다고 느껴짐
        for (int r = 0; r < L; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < L; c++) {
                int num = Integer.parseInt(st.nextToken());
                if (num == 1) pits.add(r * L + c);
                else if (num == 2) walls.add(r * L + c);
            }
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            knights.put(i, new Knight(i, r, c, h, w, k));
        }

        // System.out.println("기사가 총 "+ knights.size() + "명");

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            List<Integer> moveList = new ArrayList<>();
            moveList.add(n);
            canMove = true;
            searchKnight(moveList, n, d);

            if (canMove && knights.containsKey(n)) {
                moveKnight(moveList, d);
            }
        }

        int result = 0;
        for (Knight knight : knights.values()) {
            // System.out.println(knight.idx + " 기사의 " + knight.demage + "데미지 추가");
            result += knight.demage;
        }
        
        System.out.println(result);
    }

    static boolean isValid(int r) {
        return r >= 0 && r < L;
    }

    static void searchKnight(List<Integer> moveList, int idx, int d) {
        if (!knights.containsKey(idx) || !canMove) return;
        Knight knight = knights.get(idx);
        int r = knight.r;
        int c = knight.c;
        int h = knight.h;
        int w = knight.w;

        // 이동이 좌표를 벗어나면 이동하지 않음
        if (!isValid(r + dr[d]) || !isValid(r + h + dr[d] - 1)
            || !isValid(c + dc[d]) || !isValid(c + w + dc[d] - 1)) {
                moveList.clear();
                canMove = false;
                return;
            }

        // 벽을 만나면 이동하지 않음
        for (int wall: walls) {
            int wr = wall / L;
            int wc = wall % L;
            if (wr < r + h + dr[d] &&
                wr >= r + dr[d] &&
                wc < c + w + dc[d] &&
                wc >= c + dc[d]) {
                    moveList.clear();
                    canMove = false;
                    return;
                }
        }

        // 벽이 없는게 맞으면 옆에 이동할 기사 있는지 확인
        /*
         * nr < r + h + dr[d]
         * nr + nh > r + dr[d]
         * c, w, nc, nw도 마찬가지
         *
         */
        for (Knight nextKnight : knights.values()) {
            // 같은 거면 패스, 이미 있으면 패스
            if (nextKnight == knight) continue;
            int nIdx = nextKnight.idx;
            if (moveList.contains(nIdx)) continue;
            int nr = nextKnight.r;
            int nc = nextKnight.c;
            int nh = nextKnight.h;
            int nw = nextKnight.w;
            if (nr < r + h + dr[d] &&
                nr + nh > r + dr[d] &&
                nc < c + w + dc[d] &&
                nc + nw > c + dc[d]) {
                    moveList.add(nIdx);
                    searchKnight(moveList, nIdx, d);
                }
        }
    }

    static void moveKnight(List<Integer> moveList, int d) {
        // System.out.println("한 턴 이동");
        while (moveList.size() > 0) {
            int last = moveList.remove(moveList.size() - 1);
            Knight knight = knights.get(last);
            int r = knight.r;
            int c = knight.c;
            int h = knight.h;
            int w = knight.w;

            int decrease = 0;
            // 함정을 몇 개 만나는지 검사
            for (int pit: pits) {
                int pr = pit / L;
                int pc = pit % L;
                if (pr < r + h + dr[d] &&
                    pr >= r + dr[d] &&
                    pc < c + w + dc[d] &&
                    pc >= c + dc[d]) {
                        decrease++;
                    }
            }
            if (moveList.size() != 0) {
                // System.out.println(knight.idx + " 기사가 " + decrease + "만큼의 데미지를 입습니다.");
                knight.demage += decrease; 
            }
            if (knight.k - knight.demage <= 0) {
                // System.out.println(knight.idx + " 기사가 삭제됩니다.");
                knights.remove(last);
            } else {
                knight.r += dr[d];
                knight.c += dc[d];
            }
        }
    } 
}
