import java.util.LinkedList;
import java.util.StringTokenizer;

public class MY_P81303 {
	static int cur;
	static int head;
	static int N;
	static StringBuilder sb;
	static int[] prev;
	static int[] next;
	static LinkedList<DeletedRow> drList;
	public static String solution(int n, int k, String[] cmd) {
		StringTokenizer st;
		sb = new StringBuilder();
		// 0이 제일 윗 행, 마지막 원소가 제일 아랫 행으로 간주함
		cur = k;
		head = 0;
		N = n;
		prev = new int[N];
		next = new int[N];
		drList = new LinkedList<>();
		
		for (int i = 0; i < N; i++) {
			prev[i] = i - 1;
			next[i] = i + 1;
		}
		
		// 명령어 개수만큼 동작 시작
		for (int i = 0; i < cmd.length; i++) {
			st = new StringTokenizer(cmd[i]);
			String oper = st.nextToken();
			int move = 0;
			// null일 때 오류 안날까?
			if (st.hasMoreTokens())
				move = Integer.parseInt(st.nextToken());

			tableOperation(oper, move);
		}
		
		inspectTable();

		return sb.toString();
	}
	
	public static void inspectTable() {
		int idx = head;
		for (int i = 0; i < N; i++) {
			if (i != idx) sb.append("X");
			else {
				sb.append("O");
				idx = next[idx];
			}
		}
	}
	
	public static void tableOperation(String oper, int move) {
		if (oper.equals("U")) {
			move(move, true);
		} else if (oper.equals("D")) {
			move(move, false);
		} else if (oper.equals("C")) {
			drList.add(new DeletedRow(prev[cur], cur, next[cur]));
			if (prev[cur] != -1) next[prev[cur]] = next[cur];
			// 맨 앞의 원소를 삭제하면 다음 원소로 head 저장
			else head = next[cur];
			if (next[cur] != N) prev[next[cur]] = prev[cur];
			else {
				move(1, true);
				return;
			}
			move(1, false);
		} else if (oper.equals("Z")){
			DeletedRow dr = drList.pollLast();
			if (dr.prev != -1) next[dr.prev] = dr.cur;
			if (dr.next != N) prev[dr.next] = dr.cur;
            
            // 맨 앞의 원소를 복구할 때 head 원위치
            if(next[dr.cur] == head) {
                head = dr.cur;
            }
		}
	}
	
	public static void move(int move, boolean isUp) {
		int[] dir;
		if (isUp) dir = prev;
		else dir = next;
		
		for (int i = 0; i < move; i++) {
			if (!isValid(dir[cur])) return;
			cur = dir[cur];
		}
	}
	
	public static boolean isValid(int x) {
		return !(x < 0 || x >= N);
	}
	
	public static class DeletedRow {
		int prev;
		int cur;
		int next;
		DeletedRow(int p, int c, int n) {
			this.prev = p;
			this.cur = c;
			this.next = n;
		}
	}

	public static void main(String[] args) {
		System.out.println(solution(2, 1, new String[] { "C", "U 5", "U 5", "D 2" }));
		System.out.println(solution(8, 2, new String[] { "D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z" }));
		System.out.println(solution(8, 7, new String[] { "C", "C", "C", "Z", "Z" }));
	}

}
