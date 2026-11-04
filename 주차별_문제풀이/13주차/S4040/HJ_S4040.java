import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder output = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			
			String str = new String(br.readLine());
			
			int answer = 1;

			for (int i = 1; i <= str.length(); i++) {

			    // 문자열 길이가 i로 나누어떨어지는지 확인
			    if (str.length() % i != 0) {
			        continue;
			    }

			    boolean isValid = true;

			    // i칸 떨어진 문자끼리 비교
			    for (int j = 0; j < str.length() - i; j++) {

			        if (str.charAt(j) != str.charAt(j + i)) {
			            isValid = false;
			            break;
			        }
			    }

			    // 가장 짧은 반복 단위를 찾았다면 정답 계산
			    if (isValid) {
			        answer = str.length() / i;
			        break;
			    }
			}
			
			output.append('#').append(testCase).append(' ').append(answer).append('\n');
			
		}
		
		System.out.print(output);
		
	}

}