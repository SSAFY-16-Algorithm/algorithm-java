import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class MY_S5648 {
	static BufferedReader br;
	static BufferedWriter bw;
	static StringBuilder sb;
	static StringTokenizer st;
	// 위로 올라갈 때 y가 증가
	// 왼쪽으로 갈때 x가 감소
	static final int[] dy = { 1, -1, 0, 0 }; // 상하좌우
	static final int[] dx = { 0, 0, -1, 1 };

	static Deque<Atom> dq;
	// 충돌 좌표 저장
	static Set<Integer> conflictCoordinates;
	// 충돌하지 않은 원소만 저장
	static Map<Integer, Atom> singleAtoms;
	static int atomSize;
	static int totalPower;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/S5648/sample_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			// 저장된 원자
			// 큐에 1000개 이상 담겨도 되려나?
			dq = new ArrayDeque<>();
			conflictCoordinates = new HashSet<>();
			singleAtoms = new HashMap<>();

			// atomSize만큼 큐에 입력 후
			// atom이 좌표를 벗어나거나 서로 부딪히는 경우 큐에서 삭제할 예정
			atomSize = Integer.parseInt(br.readLine());

			// O(4N)
			for (int i = 0; i < atomSize; i++) {
				st = new StringTokenizer(br.readLine());
				int x, y, idx, power;
				// 0.5 거리에서 부딪힐 수 있기 때문에 좌표를 두 배로 확장
				x = Integer.parseInt(st.nextToken()) * 2;
				y = Integer.parseInt(st.nextToken()) * 2;
				idx = Integer.parseInt(st.nextToken());
				power = Integer.parseInt(st.nextToken());
				dq.offer(new Atom(x, y, power, idx));
			}

			totalPower = 0;
			calculateTotalPower();

			sb.append('#').append(t).append(' ').append(totalPower).append('\n');
		}
		bw.write(sb.toString());

		br.close();
		bw.close();
	}

	static void calculateTotalPower() {
		// 0초에는 어차피 안 부딪히니 바로 이동 시작
		while (!dq.isEmpty()) {
			// 원자를 꺼내서 좌표 이동 후 맵에 추가
			Atom curAtom = dq.poll();
			curAtom.x = curAtom.x + dx[curAtom.moveIdx];
			curAtom.y = curAtom.y + dy[curAtom.moveIdx];
			int x1 = curAtom.x + 2000;
			int y1 = curAtom.y + 2000;
			// 좌표가 벗어나면 큐에서 삭제
			// 속도가 같으니까 범위를 벗어나면 부딪힐 일 없다고 판단됨
			if (!(curAtom.x < -2000 || curAtom.x > 2000 || curAtom.y < -2000 || curAtom.y > 2000)) {
				int coordinate = x1 * 4001 + y1;
				// 일단 충돌 좌표인지 검사
				if (conflictCoordinates.contains(coordinate)) {
					totalPower += curAtom.power;
				} else {
					// 충돌하지 않은 것만 넣는 리스트
					Atom conflictAtom = singleAtoms.putIfAbsent(coordinate, curAtom);

					// 넣었는데 뭔가 나오면 충돌된 것
					if (conflictAtom != null) {
						totalPower += conflictAtom.power + curAtom.power;
						conflictCoordinates.add(coordinate);
						singleAtoms.remove(coordinate);
					}
				}
			}

			// 맵에 모두 넣다보면 dq가 비는 순간이 오는데 이때 map을 순회하기
			// O(N^2) 가능성
			// is Empty로 검사하면 비지 않아서 안 들어옴?
			if (dq.size() == 0) {
				for (Atom atom : singleAtoms.values()) {
					dq.offer(atom);
				}
				conflictCoordinates.clear();
				singleAtoms.clear();
			}
		}
	}

	static class Atom {
		int x;
		int y;
		int power; // 에너지
		int moveIdx; // 이동 방향

		Atom(int x, int y, int power, int moveIdx) {
			this.x = x;
			this.y = y;
			this.power = power;
			this.moveIdx = moveIdx;
		}
	}
}