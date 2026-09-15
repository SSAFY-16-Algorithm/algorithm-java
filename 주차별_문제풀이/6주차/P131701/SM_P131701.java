import java.util.HashSet;
import java.util.Set;

public class SM_P131701 {
	public static void main(String[] args) {
		int[] elements = {7,9,1,1,4};
		System.out.println(solution(elements));
	}
	
	public static int solution(int[] elements) {
        int answer = 0;
        Set<Integer> set = new HashSet<>(); // 중복된 수 삽입 방지

        int n = elements.length; // 총 배열의 길이
        int[] prefix = new int[n * 2 + 1]; // 누적합 - 계산 용이

        for(int i = 0; i < n * 2; i++) {
            prefix[i + 1] = prefix[i] + elements[i % n];
        }
        
        
        for (int len = 1; len <= n; len++) { // 수열의 길이
            for (int start = 0; start < n; start++) { // 수열을 시작하는 위치
                int sum = prefix[start + len] - prefix[start]; // 찾고자 하는 합 찾기
                
                set.add(sum);
            }
        }
        
        answer = set.size(); // 최종 수열 갯수
        return answer;
    }
}
