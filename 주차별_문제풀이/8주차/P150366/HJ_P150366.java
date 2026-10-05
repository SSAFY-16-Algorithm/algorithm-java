import java.util.*;

class Solution {

	int[] parent;
	String[] table;

	public String[] solution(String[] commands) {

		// 표 크기는 50x50으로 고정, 좌표를 그대로 인덱스 사용하기 위해 앞의 index 0을 buffer로 지정
		// merge 명령어를 구현하기 위해 UnionFind를 사용해야되는데 이 때 parent 배열을 1차원으로 두기 때문에 table도 1차원
		// 배열로 변경
		table = new String[2501];

		// MERGE 명령어 구현을 위한 UnionFind 자료구조
		parent = new int[2501];

		for (int i = 1; i <= 2500; i++) {
			parent[i] = i;
		}

		// PRINT 결과 저장
		List<String> answer = new ArrayList<>();

		for (String command : commands) {

			String[] tokens = command.split(" ");

			String type = tokens[0];
			
			if (type.equals("UPDATE")) {
				if (tokens.length == 4) {
					updateCell(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]);
				} else {
					updateValue(tokens[1], tokens[2]);
				}
			} else if (type.equals("MERGE")) {
				merge(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
			} else if (type.equals("UNMERGE")) {
				unmerge(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
			} else if (type.equals("PRINT")) {
				answer.add(print(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
			}

		}

		return answer.toArray(new String[0]);

	}

	int getIndex(int r, int c) {
		return (r - 1) * 50 + c;
	}
	
	int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		return parent[x] = find(parent[x]);
	}

	// 1. UPDATE r c value : r,c 위치의 셀의 값을 value로 업데이트 (value -> 알파벳 소문자, 숫자로 구성된
	// 1~10 길이 문자열)
	void updateCell(int r, int c, String value) {
		
		// (r,c)의 table 1차원 배열 내부 실제 인덱스 구하기
		int index = getIndex(r, c);
		// 해당 셀이 속한 대표 셀을 찾아서 값을 변경 
		int root = find(index);
		// 병합된 셀일 경우, 대표 셀만 변경해서 value를 저장
		// 병합된 셀인데 대표 셀과 (r,c) 셀이 다른 값이 저장되면 안됨
		table[root] = value;
		
	}

	// 2. UPDATE value1 value2 : value1을 값으로 가지고 있는 모든 셀 선택해서 value2로 업데이트
	void updateValue(String value1, String value2) {
		
		// table 1차원 배열에는 병합된 셀인 경우에는 대표 셀에만 값이 저장되어 있으므로 
		// 전체 table 배열을 순회하면서 value1인 부분 찾으면 value2로 변경
		for (int i = 1; i <= 2500; i++) {
			if (value1.equals(table[i])) {
				table[i] = value2;
			}
		}
		
	}

	// 3. MERGE r1 c1 r2 c2 : (r1, c1) 위치 셀과 (r2, c2) 위치 셀을 선택해서 병합
	void merge(int r1, int c1, int r2, int c2) {
		
		int index1 = getIndex(r1, c1);
		int index2 = getIndex(r2, c2);
		
		int root1 = find(index1);
		int root2 = find(index2);
		
		if (root1 == root2) {
			return;
		}
		
		String mergedValue;
		
		// 어떤 값을 유지할지 결정
		if (table[root1] != null) {
			mergedValue = table[root1];
		} else {
			mergedValue = table[root2];
		}
		
		// 두 셀 병합
		parent[root2] = root1;
		
		// 대표 셀에만 값 유지
		table[root1] = mergedValue;
		table[root2] = null;
		
	}

	// 4. UNMERGE r c : (r, c) 위치의 셀을 선택해서 모든 병합 해제
	void unmerge(int r, int c) {
		
		// 1. 선택된 셀 인덱스 
		int target = getIndex(r, c);
		
		// 2. 선택된 셀의 대표 셀을 찾기 (대표 셀이 따로 있다면 선택된 셀은 NULL일테고, 대표 셀의 값을 선택된 셀로 가져와야하기 때문
		int root = find(target);
		
		// 3. 병합을 풀기 전에 값을 저장하기
		String savedValue = table[root];
		
		// 4. 같은 그룹의 병합되었던 셀을 모두 병합 해제해야 하므로 기존에 같은 그룹이었던 셀을 찾기
		List<Integer> group = new ArrayList<>();
		
		for (int i = 1; i <= 2500; i++) {
			if(find(i) == root) {
				group.add(i);
			}
		}
		
		// 5. group에 저장된 셀들 각각 자기 자신이 대표 셀이 되도록 만들고 각 셀의 값은 null로 변경하기
		for (int index : group) {
			parent[index] = index;
			table[index] = null;
		}
		
		// 6. 선택된 셀은 기존의 대표 셀의 값으로 변경
		table[target] = savedValue;
		
	}

	// 5. PRINT r c : (r, c) 위치의 셀을 선택해서 셀 값을 출력
	String print(int r, int c) {
		
		int index = getIndex(r, c);  // (r,c)를 1차원 index로 변환
		int root = find(index);  // find(index)로 대표 셀 찾기
		
		// 대표 셀의 값이 null이면 "EMPTY", 아니면 해당 값 반환
		if (table[root] == null) {
			return "EMPTY";
		}
		
		return table[root];
	}
}