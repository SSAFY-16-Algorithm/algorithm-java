import java.util.*;

class Solution {

    static class Cell {
        int r;
        int c;
        int id;

        Cell(int r, int c, int id) {
            this.r = r;
            this.c = c;
            this.id = id;
        }
    }

    int N;
    int M;

    int[][] board;

    // 1: 오른쪽, 2: 아래, 3: 왼쪽, 4: 위
    int[] dr = {0, 0, 1, 0, -1};
    int[] dc = {0, 1, 0, -1, 0};

    public int[][] solution(int[][] board, int[][] commands) {

        N = board.length;
        M = board[0].length;

        this.board = new int[N][M];

        for (int r = 0; r < N; r++) {
            this.board[r] = board[r].clone();
        }

        for (int[] command : commands) {
            int appId = command[0];
            int direction = command[1];

            executeCommand(appId, direction);
        }

        return this.board;
    }

    // 명령 하나 처리
    void executeCommand(int appId, int direction) {

        // 1. 처음 앱으로부터 연쇄적으로 밀릴 앱을 모두 찾는다.
        Set<Integer> moveGroup = collectMoveGroup(appId, direction);

        // 2. 그룹 전체를 동시에 한 칸 이동시킨다.
        moveApps(moveGroup, direction);

        /*
         * 3. 이동 결과 어떤 앱이 경계를 걸쳐서
         *    양쪽 끝에 찢어져 있다면,
         *
         *    그 앱을 다시 시작점으로 잡고
         *    BFS -> 한 칸 이동을 반복한다.
         */
        while (true) {

            int splitApp = findSplitApp(direction);

            if (splitApp == 0) {
                break;
            }

            moveGroup = collectMoveGroup(splitApp, direction);

            moveApps(moveGroup, direction);
        }
    }

    // startId를 밀었을 때 같이 밀려야 하는 앱들을 BFS로 찾는다.
    Set<Integer> collectMoveGroup(int startId, int direction) {

        Set<Integer> group = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();

        group.add(startId);
        queue.offer(startId);

        while (!queue.isEmpty()) {

            int currentId = queue.poll();

            /*
             * 현재 앱이 차지하고 있는 모든 칸을 찾는다.
             *
             * 그 칸을 한 칸 이동했을 때
             * 다른 앱이 있다면 그 앱도 같이 밀려야 한다.
             */
            for (int r = 0; r < N; r++) {

                for (int c = 0; c < M; c++) {

                    if (board[r][c] != currentId) {
                        continue;
                    }

                    int nr = (r + dr[direction] + N) % N;
                    int nc = (c + dc[direction] + M) % M;

                    int nextId = board[nr][nc];

                    if (nextId == 0) {
                        continue;
                    }

                    if (group.contains(nextId)) {
                        continue;
                    }

                    group.add(nextId);
                    queue.offer(nextId);
                }
            }
        }

        return group;
    }

    // group에 포함된 모든 앱을 동시에 한 칸 이동
    void moveApps(Set<Integer> group, int direction) {

        List<Cell> movingCells = new ArrayList<>();

        // 움직여야 하는 모든 칸 저장
        for (int r = 0; r < N; r++) {

            for (int c = 0; c < M; c++) {

                if (board[r][c] != 0
                        && group.contains(board[r][c])) {

                    movingCells.add(
                            new Cell(r, c, board[r][c])
                    );
                }
            }
        }

        // 먼저 기존 위치를 모두 비운다.
        for (Cell cell : movingCells) {
            board[cell.r][cell.c] = 0;
        }

        // 그 다음 한 칸 이동한 위치에 기록한다.
        for (Cell cell : movingCells) {

            int nr =
                    (cell.r + dr[direction] + N) % N;

            int nc =
                    (cell.c + dc[direction] + M) % M;

            board[nr][nc] = cell.id;
        }
    }

    /*
     * 이동한 앱 중
     * 경계를 넘어가면서 양쪽 끝으로 찢어진 앱을 찾는다.
     *
     * 없으면 0 반환.
     */
    int findSplitApp(int direction) {

        // 좌/우 이동
        if (direction == 1 || direction == 3) {

            for (int r = 0; r < N; r++) {

                int left = board[r][0];
                int right = board[r][M - 1];

                if (left == 0 || left != right) {
                    continue;
                }

                /*
                 * 같은 ID가 왼쪽 끝과 오른쪽 끝에 있다.
                 *
                 * 단, 해당 행 전체가 같은 앱이면
                 * 원래 너비가 M인 앱일 수 있으므로
                 * "찢어진 앱"이 아니다.
                 */
                boolean hasDifferentCell = false;

                for (int c = 0; c < M; c++) {

                    if (board[r][c] != left) {
                        hasDifferentCell = true;
                        break;
                    }
                }

                if (hasDifferentCell) {
                    return left;
                }
            }
        }

        // 위/아래 이동
        else {

            for (int c = 0; c < M; c++) {

                int top = board[0][c];
                int bottom = board[N - 1][c];

                if (top == 0 || top != bottom) {
                    continue;
                }

                /*
                 * 해당 열 전체가 같은 앱이라면
                 * 높이가 N인 앱일 수 있으므로 제외.
                 */
                boolean hasDifferentCell = false;

                for (int r = 0; r < N; r++) {

                    if (board[r][c] != top) {
                        hasDifferentCell = true;
                        break;
                    }
                }

                if (hasDifferentCell) {
                    return top;
                }
            }
        }

        return 0;
    }
}