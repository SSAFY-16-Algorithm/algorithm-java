import java.util.*;

public class Solution {
	static Map<String, PriorityQueue<String>> routes;
	static List<String> answer;

	public static List<String> solution(String[][] tickets) {
		routes = new HashMap<>();
		answer = new LinkedList<>();

		for (int i = 0; i < tickets.length; i++) {
			String[] route = tickets[i];
			routes.computeIfAbsent(route[0], k -> new PriorityQueue<String>()).offer(route[1]);
		}

		String from = "ICN";
		dfs(from);

		return answer;
	}

	public static void dfs(String from) {
		
		PriorityQueue<String> toList = routes.getOrDefault(from, null);
		
		while (toList!= null && toList.size() > 0) {
			String to = toList.poll();
			dfs(to);
		}

		answer.addFirst(from);

	}
}
