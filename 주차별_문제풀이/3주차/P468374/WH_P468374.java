import java.util.*;

class Solution {

    static int N;
    static int M;
    static int[][] board;

    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};

    public int[][] solution(int[][] board, int[][] commands) {

        N = board.length;
        M = board[0].length;

        for (int[] command : commands) {
            int app = command[0];
            int dir = command[1] - 1;

            // 현재 보드
            Solution.board = board;

            // 이번 이동에 영향을 받는 앱
            boolean[] check = new boolean[101];

            Queue<Integer> queue = new ArrayDeque<>();

            check[app] = true;
            queue.offer(app);

            /*
             * 하나의 명령을 처리하는 동안
             * "이번에 한 칸 이동할 앱"들을 계속 처리한다.
             *
             * 처음에는 명령받은 앱 하나만 들어있고,
             * 이동하면서 잘린 앱이 생기면 queue에 추가한다.
             */
            while (!queue.isEmpty()) {
                
                while (!queue.isEmpty()) {
                    int current = queue.poll(); //현재 queue에 있는 앱들을 기준으로 같이 움직여야 하는 앱들을 찾는다.
                    
                    for (int r = 0; r < N; r++) {
                        for (int c = 0; c < M; c++) {
                            if (board[r][c] != current) {
                                continue;
                            }
                            
                            // 이동할 다음 칸 -> 초과할 수 있으므로 N, M으로 나눠주는 연산 함 
                            int nr = (r + dr[dir] + N) % N;
                            int nc = (c + dc[dir] + M) % M;

                            int next = board[nr][nc];

                            // 이동할 방향에 다른 앱이 있다면 그 앱도 같이 이동
                            if (next != 0 && !check[next]) {
                                check[next] = true;
                                queue.offer(next);
                            }
                        }
                    }
                }

                //check에 있는 앱들을 모두 한 칸 이동해서 newBoard에 반영
                int[][] newBoard = new int[N][M];

                for (int r = 0; r < N; r++) {
                    for (int c = 0; c < M; c++) {

                        int appId = board[r][c];

                        // 빈칸이면 넘어감 
                        if (appId == 0) {
                            continue;
                        }

                        // 이번에 이동하지 않는 앱이면 넘어감 
                        if (!check[appId]) {
                            newBoard[r][c] = appId;
                            continue;
                        }

                        // 이동하는 앱
                        int nr = (r + dr[dir] + N) % N;
                        int nc = (c + dc[dir] + M) % M;

                        newBoard[nr][nc] = appId;
                    }
                }

                board = newBoard; //board 수정해줌(미리 수정하면 꼬여서 이중 for문 끝나고 나서 수정)

                //이동 이후 일부가 격자 밖으로 나갔다가 반대편에 나타난 "잘린 앱"을 찾아야 함
                check = new boolean[101];
                
                if (dir == 0 || dir == 2) {
                    // 좌우 이동
                    for (int r = 0; r < N; r++) {
                        int left = board[r][0];
                        int right = board[r][M - 1];

                        // 양 끝이 같은 앱이 아니면 잘린 게 아니므로 넘어감 
                        if (left == 0 || left != right) {
                            continue;
                        }

                        //양 끝이 같은 앱인 경우 : A 0 0 0 A일수도 있고, A A A A A일 수도 있어서 구분함
                        boolean cut = false;
                        for (int c = 1; c < M - 1; c++) {
                            if (board[r][c] != left) { //A A A A A가 아님 -> 잘린 앱임 
                                cut = true;
                                break;
                            }
                        }
                        
                        //잘린 앱이고 check되지 않았다면 check시키고 큐에 넣음 
                        if (cut && !check[left]) {
                            check[left] = true;
                            queue.offer(left);
                        }
                    }

                } else {
                    //위아래 이동
                    for (int c = 0; c < M; c++) {
                        int top = board[0][c];
                        int bottom = board[N - 1][c];
                        if (top == 0 || top != bottom) {
                            continue;
                        }

                        boolean cut = false;
                        for (int r = 1; r < N - 1; r++) {
                            if (board[r][c] != top) {
                                cut = true;
                                break;
                            }
                        }

                        if (cut && !check[top]) {
                            check[top] = true;
                            queue.offer(top);
                        }
                    }
                }
            }
        }
        //큐가 비었으면 더이상 이동할 애가 없다는 것이므로 반환
        return board;
    }
}
