import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class MY_C2025H2AM1 {

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
		System.setIn(new FileInputStream("res/C2025H2AM1/input.txt"));
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
//            for (int r = 0; r < N; r++) {
//                System.out.println(Arrays.toString(arr[r]));
//            }

		}
//        for (int r = 0; r < N; r++) {
//            System.out.println(Arrays.toString(arr[r]));
//        }
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
//                System.out.println("좌측에서 빼기");
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
//                System.out.println("우측에서 빼기");
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
//                    System.out.printf("하차할 번호: %d\n", num);
					System.out.println(num);
					int rowIndex = packageList.get(num)[2];
					int colIndex = packageList.get(num)[3];
//                    System.out.printf("하차할 인덱스: %d %d\n", rowIndex, colIndex);
//                    System.out.printf("짐 높이: %d\n", packageList.get(num)[0]);

					for (int c = colIndex; c < colIndex + packageList.get(num)[1]; c++) {
						// 하차하기 때문에 list 숫자를 0 으로 변경
						for (int r = rowIndex; r > rowIndex - packageList.get(num)[0]; r--) {
//                            System.out.printf("%d %d %d %d\n", rowIndex, colIndex, r, c);
							arr[r][c] = 0;
						}
						// 위에서부터 다운할 수 있는 짐 번호 추가
						for (int r = rowIndex - packageList.get(num)[0]; r >= 0
								&& r > rowIndex - packageList.get(num)[0] - 1; r--) {
							// 바로 위에 있는 짐이 있다면 추가
							// set에 없으면 true기 때문에 바로 큐에 넣어줌
							// 큐와 set은 동시에 관리해야함
							if (arr[r][c] != 0 && downSet.add(arr[r][c])) {
								downQueue.add(arr[r][c]);
//                                System.out.printf("내려올 수 있는 후보: %d\n",arr[r][c]);
							}
						}
					}

					// 자기자신을 추가했을 경우 제외
					downSet.remove(num);
					downQueue.remove(num);
					packageList.remove(num);
					break;
				}
			}

			// 다운할 후보들 검사
			while (!downQueue.isEmpty()) {
				int num = downQueue.poll();
				// set에서 바로 제외해도 되려나?
				downSet.remove(num);
				// 각 열별로 몇만큼 내려갈 수 있는지 체크
				int[] isAbleToDown = new int[packageList.get(num)[1]];
				int rowIndex = packageList.get(num)[2];
				int colIndex = packageList.get(num)[3];
//                System.out.printf("이번에 내려올 친구: %d, 행: %d\n",num, rowIndex);

				// 아래로 얼마나 내려갈 수 있는지 검사
				// row를 기준으로 검사하니까 장애물을 만나면 stop할 기준이 어려움
				for (int c = colIndex; c < colIndex + packageList.get(num)[1]; c++) {
					for (int r = rowIndex + 1; r < N; r++) {
						if (arr[r][c] == 0)
							isAbleToDown[c - colIndex]++;
						else
							break;
					}
				}

//                System.out.printf("각 열별로 내려올 수 있는 수: " + Arrays.toString(isAbleToDown)+'\n');

				int downNum = Integer.MAX_VALUE;
				for (int i = 0; i < packageList.get(num)[1]; i++) {
					downNum = Math.min(downNum, isAbleToDown[i]);
				}
				if (downNum == 0) {
//                    System.out.println("내려갈 수 없어서 패스함");
					continue;
				}

				// 원래 있던 자리에서 0으로 변경 하고 그 위에 있는 짐을 다시 추가
				for (int c = colIndex; c < colIndex + packageList.get(num)[1]; c++) {
					// 하차하기 때문에 list 숫자를 0 으로 변경
					for (int r = rowIndex; r > rowIndex - packageList.get(num)[0]; r--) {
//                        System.out.printf("%d %d %d %d\n", rowIndex, colIndex, r, c);
						arr[r][c] = 0;
					}
					// 위에서부터 다운할 수 있는 짐 번호 추가
					int upperRow = rowIndex - packageList.get(num)[0];
					if (upperRow >= 0 && arr[upperRow][c] != 0 && downSet.add(arr[upperRow][c])) {
						downQueue.add(arr[upperRow][c]);
//                                System.out.printf("다음으로 내려올 수 있는 후보 추가: %d\n",arr[r][c]);
					}
				}
//                for (int r = 0; r < N; r++) {
//                    System.out.println(Arrays.toString(arr[r]));
//                }

				// 내려갈 수 있는 칸만큼 다시 내려가기
				// rowIndex를 내려갈 수 있는만큼 갱신시키고 다시 숫자로 채우기
				packageList.get(num)[2] = packageList.get(num)[2] + downNum;
				rowIndex = packageList.get(num)[2];
//                System.out.printf("최종적으로 내려갈 위치 : %d, %d\n", rowIndex, colIndex);
				for (int r = rowIndex; r > rowIndex - packageList.get(num)[0]; r--) {
					for (int c = colIndex; c < colIndex + packageList.get(num)[1]; c++) {
						arr[r][c] = num;
					}
				}

//                for (int r = 0; r < N; r++) {
//                    System.out.println(Arrays.toString(arr[r]));
//                }

				// 자기 자신은 제외하기
				downSet.remove(num);
				downQueue.remove(num);

				// 실제로 내려간 뒤에 바로 위에 영향을 받은 짐에 대해서 또 큐에 넣기
			}

			// 한 번 꺼내고 후보군은 리셋
			unloadList.clear();
			isLeft = !isLeft;
//            for (int r = 0; r < N; r++) {
//                System.out.println(Arrays.toString(arr[r]));
//            }
		}
	}

	static void down(int k, int h, int w, int p) {
		// 가로로 수용가능한 행을 저장하는 배열
		int num = k;
		int rowIndex = 0;
		int colIndex = p - 1;
		int[] isAbleToDown = new int[w];
		for (int c = colIndex; c < colIndex + packageList.get(num)[1]; c++) {
			for (int r = rowIndex + 1; r < N; r++) {
				if (arr[r][c] == 0)
					isAbleToDown[c - colIndex]++;
				else
					break;
			}
		}

//        System.out.printf("각 열별로 내려올 수 있는 수: " + Arrays.toString(isAbleToDown)+'\n');

		int downNum = Integer.MAX_VALUE;
		for (int i = 0; i < packageList.get(num)[1]; i++) {
			downNum = Math.min(downNum, isAbleToDown[i]);
		}
		packageList.get(num)[2] = rowIndex + downNum;
		rowIndex = packageList.get(num)[2];
		packageList.get(num)[3] = colIndex;

		// 4,2 , h 2, p 5
		for (int r = rowIndex; r > rowIndex - h; r--) {
			for (int c = colIndex; c < colIndex + w; c++) {
				arr[r][c] = k;
			}
		}
	}
}