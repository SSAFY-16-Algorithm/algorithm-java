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
	static int N, M, idx;
	static Set<Integer> idSet;
	static Deque<Integer> moveList;
	static Set<Integer> moveSet;
	static int[][] answer;

	public static int[][] solution(int[][] boardinput, int[][] commands) {
		idSet = new HashSet<>();

		app = new HashMap<>();
		moveList = new ArrayDeque<>();
		moveSet = new HashSet<>();
		board = boardinput;
		answer = new int[board.length][board[0].length];

		N = board.length;
		M = board[0].length;

		for (int i = 0; i < N; i++) {
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
//        for (int key: app.keySet()) {
//            System.out.println(Arrays.toString(app.get(key)));
//        }

		for (int[] command : commands) {
			move(command);
		}

		return board;
	}

	static void move(int[] command) {
		int id = command[0];
		idx = command[1] - 1;

		System.out.println("-----");
		System.out.println(id + "가 " + idx + "방향으로 이동");

		moveList.add(id);
		moveSet.add(id);
		if (idx % 2 == 0)
			moveHorizontal();
		else
			moveVertical();

		for (int[] row : board) {
			System.out.println(Arrays.toString(row));
		}

		// 잘린 애들 보정 해주기
		while (true) {
			boolean isAllNotCut = true;
			for (int cutId : idSet) {
				if (!isCut(cutId)) {
					continue;
				}
				isAllNotCut = false;
				System.out.println(cutId + "이(가) 잘려있음");
				moveList.add(cutId);
				moveSet.add(cutId);

				if (idx % 2 == 0)
					moveHorizontal();
				else
					moveVertical();

			}
			if (isAllNotCut)
				break;
		}

		System.out.println("-----");
		System.out.println("보정 후 결과");
		for (int[] row : board) {
			System.out.println(Arrays.toString(row));
		}
	}

	static void moveHorizontal() {
		while (!moveList.isEmpty()) {
			int id = moveList.poll();
			int x = app.get(id)[0];
			int y = app.get(id)[1];
			int size = app.get(id)[2];

			// 삭제
			for (int i = x; i < x + size; i++) {
				for (int j = y; j < y + size; j++) {
					int nx = i % N;
					int ny = j % M;
					board[nx][ny] = 0;
				}
			}

			// 오른쪽 한 칸 이동
			// 다음열 확인
			int col = -1;
			if (idx == 0)
				col = (y + size) % M;
			// 왼쪽 한 칸 이동
			// 이전열 확인
			else 
				col = (y - 1 + M) % M;
			for (int i = x; i < x + size; i++) {
				if (board[i][col] != 0 && moveSet.add(board[i][col])) {
					// 이동 리스트에 넣기
					moveList.add(board[i][col]);
				}
			}

			if (idx == 0)
				app.get(id)[1] = (y + 1) % M;
			else 
				app.get(id)[1] = (y - 1 + M) % M;
		}

		// 이동이 다 끝나면 다 채우기
		for (int id : moveSet) {
			int x = app.get(id)[0];
			int y = app.get(id)[1];
			int size = app.get(id)[2];

			for (int i = x; i < x + size; i++) {
				for (int j = y; j < y + size; j++) {
					int nx = i % N;
					int ny = j % M;
					board[nx][ny] = id;
				}
			}
		}

		moveSet.clear();
	}

	static void moveVertical() {
		while (!moveList.isEmpty()) {
			int id = moveList.poll();
			int x = app.get(id)[0];
			int y = app.get(id)[1];
			int size = app.get(id)[2];

			// 삭제
			for (int i = x; i < x + size; i++) {
				for (int j = y; j < y + size; j++) {
					int nx = i % N;
					int ny = j % M;
					board[nx][ny] = 0;
				}
			}

			// 아래 한 칸 이동
			// 다음열 확인
			int row = -1;
			if (idx == 1)
				row = (x + size) % N;
			else
				row = (x - 1 + N) % N;
			for (int j = y; j < y + size; j++) {
				if (board[row][j] != 0 && moveSet.add(board[row][j])) {
					// 이동 리스트에 넣기
					moveList.add(board[row][j]);
				}
			}

			if (idx == 1)
				app.get(id)[0] = (x + 1) % N;
			else
				app.get(id)[0] = (x - 1 + N) % N;
		}

		// 이동이 다 끝나면 다 채우기
		for (int id : moveSet) {
			int x = app.get(id)[0];
			int y = app.get(id)[1];
			int size = app.get(id)[2];

			for (int i = x; i < x + size; i++) {
				for (int j = y; j < y + size; j++) {
					int nx = i % N;
					int ny = j % M;
					board[nx][ny] = id;
				}
			}

		}
		moveSet.clear();
	}

	static boolean isCut(int id) {
		int x = app.get(id)[0];
		int y = app.get(id)[1];
		int size = app.get(id)[2];
		// 이것만 검사해도 되려나?
		return x + size > N || y + size > M;
	}

	static boolean isValid(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M);
	}

	public static void main(String[] args) {

//		int[][] answer = solution(
//				new int[][] { { 0, 9, 1, 1, 6, 0, 0, 0 }, { 2, 2, 1, 1, 0, 0, 0, 0 }, { 2, 2, 3, 4, 4, 4, 0, 0 },
//						{ 5, 0, 0, 4, 4, 4, 7, 0 }, { 0, 0, 0, 4, 4, 4, 8, 8 }, { 0, 0, 0, 0, 0, 0, 8, 8 } },
//				new int[][] { { 2, 1 }, { 3, 1 }, { 9, 2 }, { 4, 1 } });

        int[][] answer = solution(
                new int[][] {
                        { 0, 2, 2, 0, 0, 0, 0, 0 },
                        { 0, 2, 2, 0, 0, 4, 4, 0 },
                        { 0, 3, 3, 3, 1, 4, 4, 0 },
                        { 0, 3, 3, 3, 0, 0, 0, 0 },
                        { 0, 3, 3, 3, 5, 5, 6, 0 },
                        { 0, 0, 0, 0, 5, 5, 0, 0 }
                },
                new int[][] {
                        { 3, 4 },
                        { 3, 4 }
                }
        );

	}
}