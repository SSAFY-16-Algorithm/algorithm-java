import java.util.Arrays;

public class MY_P42885 {
	public static int solution(int[] people, int limit) {
		int answer = 0;
		
		Arrays.sort(people);
		
		int left = 0;
		int right = people.length - 1;
		while (left <= right) {
			if (left < right && people[left] + people[right] <= limit) {
				left++;
			}
			right--;
			answer++;
		}
		
		return answer;
	}

	public static void main(String[] args) {
		System.out.println(solution(new int[] { 70, 50, 80, 50 }, 100));
		System.out.println(solution(new int[] { 70, 80, 50 }, 100));
	}
}
