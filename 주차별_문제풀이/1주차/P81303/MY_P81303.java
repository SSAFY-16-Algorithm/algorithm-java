import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class MY_P81303 {
	static int currentIdx;
	static LinkedList<Integer> table;
	static int N;
	static ArrayList<DeletedRow> drList;
	static StringBuilder sb;
	static int orgSize;
	public static String solution(int n, int k, String[] cmd) {
		StringTokenizer st;
		sb = new StringBuilder();
		// 0이 제일 윗 행, 마지막 원소가 제일 아랫 행으로 간주함
		table = new LinkedList<>();
		drList = new ArrayList<>();
		currentIdx = k;
		orgSize = n;
		N = n;
		
		// 첫 테이블 생성
		for (int i = 0; i < N; i++) {
			table.add(i);
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
	
	public static String inspectTable() {
		int idx = 0;
		// 없으면 RuntimeError 일으킬 가능성 (IndexOutOfBoundsException)
		if (table.size() == 0) return sb.repeat('X', orgSize).toString();
		for (int i = 0; i < orgSize; i++) {
			// 없으면 RuntimeError 일으킬 가능성 (IndexOutOfBoundsException)
			if (idx < table.size() && table.get(idx) == i) {
				sb.append('O');
				idx++;
			}
			else {
				sb.append('X');
			}
		}
		return null;
	}
	
	public static void tableOperation(String oper, int move) {
		N = table.size();
		if (oper.equals("U")) {
			currentIdx = moveTable(-move);
		} else if (oper.equals("D")) {
			currentIdx = moveTable(move);
		} else if (oper.equals("C")) {
			drList.add(new DeletedRow(currentIdx,  table.remove(currentIdx)));
			currentIdx = moveTable(0);
		} else if (oper.equals("Z")){
			// 없으면 RuntimeError 일으킬 가능성 (IndexOutOfBoundsException)
			// drList == null로 검사하면 LinkedList가 이미 연결되어 있어서
			// 절대 null이 아닐 수 없다.
			if (drList.size() == 0) return;
			DeletedRow dr = drList.removeLast();
			if (dr.deletedIdx <= currentIdx) currentIdx++;
			// 현재 행이 테이블의 제일 끝이고 삭제된 행이 그것보다 뒤의 행일 때는 그냥 추가
			if (dr.deletedIdx >= N) {
				table.add(dr.deletedRow);
			} else { 
				// 아니면 그냥 idx에 맞춰 추가하고
				// 만약 currentidx 위치였다면 원래 자리로 돌아가기
				table.add(dr.deletedIdx, dr.deletedRow);
			}
		}
	}
	
	public static int moveTable(int move) {
		N = table.size();
		if (currentIdx + move < 0) return 0;
		else if (currentIdx + move >= N) return N-1;
		else return currentIdx + move;
	}
	
	public static class DeletedRow {
		int deletedIdx;
		int deletedRow;
		
		DeletedRow(int idx, int row) {
			this.deletedIdx = idx;
			this.deletedRow = row;
		}
	}

	public static void main(String[] args) {
		System.out.println(solution(8, 2, new String[] { "D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z" }));
	}

}
