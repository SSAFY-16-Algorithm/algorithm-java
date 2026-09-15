import java.util.*;

public class SM_P67258 {

    public static void main(String[] args) {
        String[] gems = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};
        System.out.println(Arrays.toString(solution(gems)));
    }

    public static int[] solution(String[] gems) {
        int[] answer = new int[2];

        Set<String> set = new HashSet<>(); // 전체 보석의 종류를 알아내기 위해 사용

        for (String gem : gems) { 
            set.add(gem);
        }

        Map<String, Integer> map = new HashMap<>(); // 구간 안에 보석이 몇 개 들어있는지 저장

        int left = 0; // 구간의 시작 위치
        int min = Integer.MAX_VALUE; // 가장 짧은 구간 길이

        for (int right = 0; right < gems.length; right++) {

            // right 위치의 보석을 맵에 추가
            map.put(gems[right],
                    map.getOrDefault(gems[right], 0) + 1);

            // 모든 종류가 들어있다면 left를 오른쪽으로 움지경 구간을 최대한 줄임
            while (map.size() == set.size()) {

                int len = right - left + 1; // 현재 구간 길이

                if (len < min) { // 더 짧은 구간이 있다면 갱신
                    min = len;
                    answer[0] = left + 1;
                    answer[1] = right + 1;
                }

                //
                map.put(gems[left], map.get(gems[left]) - 1); // left 보석 하나 제거

                if (map.get(gems[left]) == 0) { // 보석 갯수가 0개가 되면 map에서 제거
                    map.remove(gems[left]);
                }

                left++; // 왼쪽 포인터를 오른쪽으로 한칸 이동
            }
        }

        return answer;
    }
}
