import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MY_P468374 {
	static final int[] dx = { 0, 1, 0, -1 };
	static final int[] dy = { 1, 0, -1, 0 };

	static Map<Integer, int[]> app;
	static int[][] board;
	static int N, M;
	static Deque<int[]> moveList;
	static Set<Integer> moveSet;

	public static int[][] solution(int[][] boardinput, int[][] commands) {
		Set<Integer> idSet = new HashSet<>();
		app = new HashMap<>();
		board = boardinput;
		N = board.length;
		M = board[0].length;

		for (int i = 0; i < N; i++) {
			int[] row = board[i];
			for (int j = 0; j < M; j++) {
				// 아이디를 처음 발견하면 위치 좌표 기억
				if (board[i][j] == 0)
					continue;
				if (idSet.add(board[i][j])) {
					app.put(board[i][j], new int[] { i, j, 1 });
				} else if (app.get(board[i][j])[0] == i) {
					// 길이 저장
					app.get(board[i][j])[2]++;
				}
			}
		}

		// 보드판 확인
		for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
		System.out.println('\n');

		// app 저장된 꼴 확인
//    	for (int key: app.keySet()) {
//    		System.out.println(Arrays.toString(app.get(key)));
//    	}

		for (int[] command : commands) {
			move(command);
		}

		int[][] answer = {};
		return answer;
	}

	static void move(int[] command) {
		System.out.println("커맨드 실행");
		moveList = new ArrayDeque<>();
		moveSet = new HashSet<>();
		
		moveList.add(new int [] {command[0], 1});
		moveSet.add(command[0]);
		
		int idx = command[1] - 1;

		// 이동할 앱이 없을때까지 반복
		while (!moveList.isEmpty()) {
			int[] move = moveList.poll();
			int id = move[0];
			moveSet.remove(id);

			int x = app.get(id)[0];
			int y = app.get(id)[1];
			int size = app.get(id)[2];
			
			int nx = x;
			int ny = y;
			for (int i = 0; i < move[1]; i++) {
				nx = nx + dx[idx];
				ny = ny + dy[idx];
			}

			// 이동할 위치에 있는 앱은 없애고 킵
			// 만약 반대편으로 넘어간다면 반대편도 검사
			if (isMovable(id, nx, ny, move[1])) {
				// 삭제
				for (int i = x; i < x + size; i++) {
					for (int j = y; j < y + size; j++) {
						if (board[i][j] == id)
							board[i][j] = 0;
					}
				}
				
				// step만큼 이동하지만, 혹시 그 전에 이동가능하면..
				for (int i = x; i <= nx; i++) {
					for (int j = y; y <= ny; j++) {
						
					}
				}

				// 이동
				app.get(id)[0] = nx;
				app.get(id)[1] = ny;
				for (int i = nx; i < nx + size; i++) {
					for (int j = ny; j < ny + size; j++) {
						board[i][j] = id;
					}
				}
			} else {
				// 오른쪽일 때
				if (idx == 0) {
					isMovable(id, x, 0, size);

					// 삭제
					for (int i = x; i < x + size; i++) {
						for (int j = y; j < y + size; j++) {
							if (board[i][j] == id)
								board[i][j] = 0;
						}
					}

					// 이동
					app.get(id)[0] = x;
					app.get(id)[1] = 0;
					for (int i = x; i < x + size; i++) {
						for (int j = 0; j < size; j++) {
							board[i][j] = id;
						}
					}
				}
				// 아래쪽일 때
				if (idx == 1) {
					isMovable(id, 0, y, size);

					// 삭제
					for (int i = x; i < x + size; i++) {
						for (int j = y; j < y + size; j++) {
							if (board[i][j] == id)
								board[i][j] = 0;
						}
					}

					// 이동
					app.get(id)[0] = 0;
					app.get(id)[1] = y;
					for (int i = 0; i < size; i++) {
						for (int j = y; j < y + size; j++) {
							board[i][j] = id;
						}
					}
				}
				// 왼쪽일 때
				// 위쪽일 때
			}

			for (int i = 0; i < N; i++) {
				System.out.println(Arrays.toString(board[i]));
			}
			System.out.println('\n');
		}

	}

	// 반대로 이동하지 않고도 움직일 수 있는지
	// 이동할 위치에 앱이 있는건 true (이동 가능하다고 생각)
	static boolean isMovable(int id, int x, int y, int step) {
		int size = app.get(id)[2];
		for (int i = x; i < x + size; i++) {
			for (int j = y; j < y + size; j++) {
				// 좌표를 도달할 수 없다면
				if (!isValid(i, j))
					return false;
				// 이동 위치에 다른 앱이 있다면 이동 후보로 넣기
				if (board[i][j] != 0 && !moveSet.contains(board[i][j]) && board[i][j] != id) {
					System.out.println("q에 " + board[i][j] + "추가");
					moveList.add(new int[] {board[i][j], step});
					moveSet.add(board[i][j]);
				}
			}
		}

		return true;
	}

	static boolean isValid(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
	}

	public static void main(String[] args) {
		int[][] answer = solution(
				new int[][] { { 0, 2, 2, 0, 0, 0, 0, 0 }, { 0, 2, 2, 0, 0, 4, 4, 0 }, { 0, 3, 3, 3, 1, 4, 4, 0 },
						{ 0, 3, 3, 3, 0, 0, 0, 0 }, { 0, 3, 3, 3, 5, 5, 6, 0 }, { 0, 0, 0, 0, 5, 5, 0, 0 } },
				new int[][] { { 3, 1 }, { 3, 1 } });
		for (int[] row : answer) {
			System.out.println(Arrays.toString(row));
		}
	}

}
