import java.util.*;

class Solution {

    public String[] solution(String[][] tickets) {

        // 출발 공항 -> 도착 가능한 공항들
        // 알파벳 순으로 가장 앞선 공항부터 꺼내기 위해 PriorityQueue 사용
        Map<String, PriorityQueue<String>> graph = new HashMap<>();

        for (String[] ticket : tickets) {
            String from = ticket[0];
            String to = ticket[1];

            graph.computeIfAbsent(from, key -> new PriorityQueue<>())
                 .offer(to);
        }

        Deque<String> stack = new ArrayDeque<>();
        List<String> route = new ArrayList<>();

        // 항상 ICN에서 출발
        stack.push("ICN");

        while (!stack.isEmpty()) {

            String current = stack.peek();

            // 현재 공항에서 사용할 수 있는 항공권이 남아 있다면
            if (graph.containsKey(current) && !graph.get(current).isEmpty()) {

                // 알파벳 순으로 가장 앞선 목적지 선택
                String next = graph.get(current).poll();
                stack.push(next);

            } else {

                // 더 이상 갈 곳이 없으면 경로에 확정
                route.add(stack.pop());
            }
        }

        // 경로는 역순으로 만들어지므로 뒤집기
        Collections.reverse(route);

        return route.toArray(new String[0]);
    }
}