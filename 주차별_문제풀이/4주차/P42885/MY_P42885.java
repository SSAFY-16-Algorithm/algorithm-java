import java.util.TreeMap;

public class MY_P42885 {
	public static int solution(int[] people, int limit) {
		TreeMap<Integer, Integer> map = new TreeMap<>();
		int answer = 0;

		for (int person : people) {
			map.put(person, map.getOrDefault(person, 0) + 1);
		}
		
		while(map.size() > 0) {
			int largest = map.lastKey();
			int smallest = map.firstKey();
			if (largest + smallest <= limit && ( (largest != smallest) || (largest == smallest && map.get(largest) > 1))) {
				map.put(smallest, map.get(smallest) - 1);
				if (map.get(smallest) == 0) map.remove(smallest);
			}
			map.put(largest, map.get(largest) - 1);
			if (map.get(largest) == 0) map.remove(largest);
			
			answer++;
		}
		
		return answer;
	}

	public static void main(String[] args) {
		System.out.println(solution(new int[] { 70, 50, 80, 50 }, 100));
		System.out.println(solution(new int[] { 70, 80, 50 }, 100));
	}
}
