import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class MY_C2025SamsungTest1 {

	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static int N, M, k, h, w, c;
	static int[][] arr;

	static Map<Integer, int[]> packageList;
	static SortedMap<Integer, Integer> unloadList;
	static Set<Integer> downSet;
	static Deque<Integer> downQueue;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		packageList = new HashMap<Integer, int[]>();

		arr = new int[N][N];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			k = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			packageList.put(k, new int[] { h, w, -1, -1 });
			down(k, h, w, c);
//			for (int r = 0; r < N; r++) {
//                System.out.println(Arrays.toString(arr[r]));
//			}

		}
		for (int r = 0; r < N; r++) {
            System.out.println(Arrays.toString(arr[r]));
		}
		// 꺼낼 수 있는 후보들을 넣는 큐
		unloadList = new TreeMap<>();
		downSet = new HashSet<>();
		downQueue = new ArrayDeque<>();
		unload();

	}

	static void unload() {
		// 리스트에 있는 것이 다 꺼내질 때까지 반복
		boolean isLeft = true;
		while (!packageList.isEmpty()) {
			if (isLeft) {

				// 좌측
				for (int r = 0; r < N; r++) {
					for (int c = 0; c < N; c++) {
						if (arr[r][c] == 0)
							continue;
						// 관측된 행의 개수만큼 저장
						// 패키지의 번호에 없으면 0, 있으면 + 1
						unloadList.put(arr[r][c], unloadList.getOrDefault(arr[r][c], 0) + 1);
						break;
					}
				}
			} else {

				// 우측
				for (int r = N - 1; r >= 0; r--) {
					for (int c = N - 1; c >= 0; c--) {
						if (arr[r][c] == 0)
							continue;
						// 관측된 행의 개수만큼 저장
						// 패키지의 번호에 없으면 0, 있으면 + 1
						unloadList.put(arr[r][c], unloadList.getOrDefault(arr[r][c], 0) + 1);
						break;
					}
				}
			}
			
			for (Map.Entry<Integer, Integer> entry : unloadList.entrySet()) {
				int num = entry.getKey();
				int inspectH = entry.getValue();

				// 가장 작은 번호의 택배 높이가 전부 관측되는 경우
				// list에서 값을 0으로 만들고
				// packageList에서도 삭제
				if (packageList.get(num)[0] == inspectH) {
					System.out.println(num);
					int rowIndex = packageList.get(num)[2];
					int colIndex = packageList.get(num)[3];
					System.out.printf("%d %d\n", rowIndex, colIndex);
					
					for (int c = colIndex; c < colIndex + packageList.get(num)[1]; c++) {
						// 하차하기 때문에 list 숫자를 0 으로 변경
						for (int r = rowIndex; r > rowIndex - packageList.get(num)[0]; r--) {
//							System.out.printf("%d %d %d %d\n", rowIndex, colIndex, r, c);
							arr[r][c] = 0;
						}
						// 위에서부터 다운할 수 있는 짐 번호 추가
						for (int r = rowIndex - packageList.get(num)[0]; r >= 0; r--) {
							// 바로 위에 있는 짐이 있다면 추가
							if(arr[r][c] != 0) downSet.add(arr[r][c]);
						}
					}
					
					downQueue.addAll(downSet);
					downSet.clear();
					downQueue.remove(num);
					packageList.remove(num);
					break;
				}
			}
			
			
			// 다운할  후보들 검사
			while(!downQueue.isEmpty()) {
				int num = downQueue.poll();
				System.out.println(num);
				int[] isAbleToDown = new int[packageList.get(num)[1]];
				int rowIndex = packageList.get(num)[2];
				int colIndex = packageList.get(num)[3];

				// 아래로 얼마나 내려갈 수 있는지 검사
				for (int r = rowIndex + 1; r < N; r++) {
					for (int c = colIndex; c < colIndex + packageList.get(num)[1]; c++) {
						if (arr[r][c] == 0) {
							System.out.printf("colIndex: %d, c: %d\n", colIndex, c);
							isAbleToDown[c - colIndex]++;
						} else break;
					}
				}
				
				int downNum = Integer.MAX_VALUE;
				for (int i = 0; i < packageList.get(num)[1]; i++) {
					downNum = Math.min(downNum, isAbleToDown[i]);
				}
				if (downNum == Integer.MAX_VALUE) continue;
				
				downSet.addAll(downQueue);

				// 원래 있던 자리에서 0으로 변경 하고 그 위에 있는 짐을 다시 추가
				for (int c = colIndex; c < colIndex + packageList.get(num)[1]; c++) {
					// 하차하기 때문에 list 숫자를 0 으로 변경
					for (int r = rowIndex; r > rowIndex - packageList.get(num)[0]; r--) {
//						System.out.printf("%d %d %d %d\n", rowIndex, colIndex, r, c);
						arr[r][c] = 0;
					}
					// 위에서부터 다운할 수 있는 짐 번호 추가
					for (int r = rowIndex - packageList.get(num)[0]; r >= 0; r--) {
						// 바로 위에 있는 짐이 있다면 추가
						if(arr[r][c] != 0) downSet.add(arr[r][c]);
					}
				}
				
				// 내려갈 수 있는 칸만큼 다시 내려가기
				

				downQueue.remove(num);
				
				// 실제로 내려간 뒤에 바로 위에 영향을 받은 짐에 대해서 또 큐에 넣기
			}

			// 한 번 꺼내고 후보군은 리셋
			unloadList.clear();
			isLeft = !isLeft;
			for (int r = 0; r < N; r++) {
				System.out.println(Arrays.toString(arr[r]));
			}
		}
	}

	static void down(int k, int h, int w, int p) {
		// 가로로 수용가능한 행을 저장하는 배열
		boolean[] isValid = new boolean[N];
		// 아래에서부터 검사
		for (int r = N - 1; r >= 0; r--) {
			isValid[r] = true;
			for (int c = p - 1; c < p - 1 + w; c++) {
				// 비지 않으면 false로 변경
//                System.out.printf("%d %d %d\n", r, c, arr[r][c]);
				if (arr[r][c] != 0)
					isValid[r] = false;
			}
			// 이미 불가능한 행이 되면 다음행 검사
			if (!isValid[r])
				continue;
		}

//        System.out.println(Arrays.toString(isValid));

		int rowIndex = -1;
		if (h == 1) {
			for (int r = N - 1; r >= 0; r--) {
				if (isValid[r]) {
					rowIndex = r;
					break;
				}
			}
		} else {
			for (int r = N - 1; r >= 0 + h - 1; r--) {
				boolean isValid2 = true;

				for (int i = 0; i < h; i++) {
					isValid2 = isValid[r - 1] && isValid[r];
				}
				if (isValid2) {
					rowIndex = r;
					break;
				}
			}
		}
		// 4,2 , h 2, p 5

		packageList.get(k)[2] = rowIndex;
		packageList.get(k)[3] = p - 1;
		for (int r = rowIndex; r > rowIndex - h; r--) {
			for (int c = p - 1; c < p - 1 + w; c++) {
				arr[r][c] = k;
			}
		}
	}
}