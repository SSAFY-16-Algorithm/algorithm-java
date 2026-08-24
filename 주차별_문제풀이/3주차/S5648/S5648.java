import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution {

    static class Atom {
        int x, y, dir, energy;
        boolean isAlive;

        public Atom(int x, int y, int dir, int energy) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.energy = energy;
            this.isAlive = true;
        }
    }

    // 상(0), 하(1), 좌(2), 우(3)
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    // 좌표 범위를 0 ~ 4000으로 맞추기 위한 오프셋 (최대 -2000 ~ 2000 -> +2000)
    static final int OFFSET = 2000;
    static final int MAP_SIZE = 4001;
    
    // 매번 새로 할당하지 않고 재사용할 2차원 지도 배열 (충돌 감지용)
    static int[][] map = new int[MAP_SIZE][MAP_SIZE];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine().trim());
            ArrayList<Atom> atoms = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                int energy = Integer.parseInt(st.nextToken());

                // 좌표를 2배로 늘리고 오프셋을 더해 양수 인덱스로 변환 (-2000 ~ 2000 -> 0 ~ 4000)
                atoms.add(new Atom(x * 2 + OFFSET, y * 2 + OFFSET, dir, energy));
            }

            int totalEnergy = 0;

            // 최대 4000초 동안 시뮬레이션 수행
            for (int step = 0; step <= 4000; step++) {
                int aliveCount = 0;

                // 1. 살아있는 원자 이동 및 지도에 표시
                for (int i = 0; i < atoms.size(); i++) {
                    Atom atom = atoms.get(i);
                    if (!atom.isAlive) continue;

                    // 이전 위치 지우기 (원래 있던 자리가 내 ID였던 경우에만 지움)
                    // 다른 원자와 충돌 판정을 위해 현재 좌표에 원자 번호(i + 1)를 기록
                    atom.x += dx[atom.dir];
                    atom.y += dy[atom.dir];

                    // 지도 범위를 벗어나는지 확인 (0 ~ 4000)
                    if (atom.x < 0 || atom.x >= MAP_SIZE || atom.y < 0 || atom.y >= MAP_SIZE) {
                        atom.isAlive = false;
                        continue;
                    }

                    aliveCount++;
                }

                // 살아있는 원자가 없으면 종료
                if (aliveCount == 0) break;

                // 2. 충돌 체크 및 에너지 계산을 위한 맵 마킹
                // 같은 위치에 여러 원자가 올 수 있으므로 충돌 원자들을 기록
                ArrayList<Integer> collisionList = new ArrayList<>();

                for (int i = 0; i < atoms.size(); i++) {
                    Atom atom = atoms.get(i);
                    if (!atom.isAlive) continue;

                    if (map[atom.y][atom.x] == 0) {
                        map[atom.y][atom.x] = i + 1; // 1부터 시작하는 인덱스 저장
                    } else {
                        // 이미 다른 원자가 이 자리에 있다면 충돌 발생!
                        // 처음 충돌을 일으킨 원자도 충돌 리스트에 포함되어야 하므로 체크 필요
                        int firstAtomIdx = map[atom.y][atom.x] - 1;
                        if (firstAtomIdx >= 0) {
                            collisionList.add(firstAtomIdx);
                            map[atom.y][atom.x] = -1; // 충돌 발생 표시 (-1)
                        }
                        collisionList.add(i);
                    }
                }

                // 3. 충돌한 원자들 소멸시키기
                if (!collisionList.isEmpty()) {
                    for (int idx : collisionList) {
                        Atom atom = atoms.get(idx);
                        if (atom.isAlive) {
                            atom.isAlive = false;
                            totalEnergy += atom.energy;
                        }
                    }
                }

                // 4. 사용한 map 배열 초기화 (전체 크기를 다 돌지 않고, 살아있던 원자들의 좌표만 리셋하여 시간 단축)
                for (int i = 0; i < atoms.size(); i++) {
                    Atom atom = atoms.get(i);
                    // 범위를 벗어났거나 이미 죽었어도 좌표가 지도 안이었다면 초기화
                    if (atom.x >= 0 && atom.x < MAP_SIZE && atom.y >= 0 && atom.y < MAP_SIZE) {
                        if (map[atom.y][atom.x] != 0) {
                            map[atom.y][atom.x] = 0;
                        }
                    }
                }
            }

            sb.append("#").append(tc).append(" ").append(totalEnergy).append("\n");
        }

        System.out.print(sb);
    }
}