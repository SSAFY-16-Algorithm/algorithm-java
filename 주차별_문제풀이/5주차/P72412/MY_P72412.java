import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class MY_P72412 {
	static StringTokenizer st;
	static Map<String, List<Integer>> scores;
	static List<Integer> arr;

	public static int[] solution(String[] info, String[] query) {
		int[] answer = new int[query.length];
		scores = new HashMap<>();

		// 각 info 별로 가능한 모든 키에 대해 점수 추가하기
		for (int i = 0; i < info.length; i++) {
			addScore(info[i]);
		}
		
		// 모든 리스트 다시 정렬
		for (List<Integer> list: scores.values()) {
			Collections.sort(list);
		}

		// 키 조합을 잘 만들었는지 확인하기
//		for (Entry<String, List<Integer>> entry : scores.entrySet()) {
//			System.out.println(entry.getKey() + ": " + entry.getValue().toString());
//		}

		for (int i = 0; i < query.length; i++) {
			st = new StringTokenizer(query[i]);
			String key = "";
			for (int j = 0; j < 7; j++) {
				String temp = st.nextToken();
				if (temp.equals("and"))
					continue;
				key += temp + " ";
			}

			int num = Integer.parseInt(st.nextToken());

//			System.out.println("찾고자 하는 키 : " + key + ", 찾고자 하는 숫자: " + num);

			if (!scores.containsKey(key))
				answer[i] = 0;
			else {
//				System.out.println(scores.get(key).toString());
				arr = scores.get(key);
				answer[i] = arr.size() - binarySearch(0, arr.size(), num);
				
			}
		}

		return answer;
	}

	public static int binarySearch(int start, int end, int num) {
		int mid = (start + end) / 2;
		// mid가 num보다 크면서 큰 것 중에 제일 작을 때 멈춰야함.
		// 그 때 답은 전체 size - mid(index)
		// 못 찾았을 경우에는 mid == size
		if (start >= end) {
			return mid;
		}

		if (arr.get(mid) >= num) {
			return binarySearch(start, mid, num);
		} else {
			return binarySearch(mid + 1, end, num);
		}
		// 길이가 0, 1일 때와 mid가 끝쪽에 있을 때까지 고려해서 종료 조건 따져야 함

	}

	public static void addScore(String info) {
		st = new StringTokenizer(info);
		String[] candidate = new String[5];

		for (int i = 0; i < 5; i++) {
			candidate[i] = st.nextToken();
		}

		for (int mask = 0; mask < 16; mask++) {
			String query = "";

			// 비트 연산자 결과는 0, 1이 아니라 실제로 8이 나올 수 있기 때문에 0인지 아닌지만 확인해야함
			if ((mask & 8) != 0) {
				query += candidate[0] + " ";
			} else {
				query += "- ";
			}

			if ((mask & 4) != 0) {
				query += candidate[1] + " ";
			} else {
				query += "- ";
			}

			if ((mask & 2) != 0) {
				query += candidate[2] + " ";
			} else {
				query += "- ";
			}

			// key를 만들 때 그냥 일관적으로 띄어쓰기 편하게 넣기 위해 유지하기
			if ((mask & 1) != 0) {
				query += candidate[3] + " ";
			} else {
				query += "- ";
			}

			scores.computeIfAbsent(query, k -> new ArrayList<>()).add(Integer.parseInt(candidate[4]));
		}

	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(
				new String[] { "java backend junior pizza 150", "python frontend senior chicken 210",
						"python frontend senior chicken 150", "cpp backend senior pizza 260",
						"java backend junior chicken 80", "python backend senior chicken 50" },
				new String[] { "java and backend and junior and pizza 100",
						"python and frontend and senior and chicken 200", "cpp and - and senior and pizza 250",
						"- and backend and senior and - 150", "- and - and - and chicken 100",
						"- and - and - and - 150" })));
	}
}
