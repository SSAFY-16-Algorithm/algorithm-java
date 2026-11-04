import java.util.*;

class Solution {

	public String[] solution(String[] s) {

		String[] answer = new String[s.length];

		for (int i = 0; i < s.length; i++) {
			
			String str = s[i];
				
			Deque<Character> deque = new ArrayDeque<>();
			int count = 0;
			int moveCount = 0;

			for (int j = 0; j < str.length(); j++) {
				char ch = str.charAt(j);
				if (ch == '1') {
					deque.addLast(ch);
					count++;
				} else {
					if (count >= 2) {
						deque.removeLast();
						deque.removeLast();
						moveCount++;
						count -= 2;
					} else {
						deque.addLast(ch);
						count = 0;
					}
				}
			}
			
			StringBuilder output = new StringBuilder();
			
			while (!deque.isEmpty()) {
				output.append(deque.removeFirst());
			}
			
			int lastZeroIndex = output.lastIndexOf("0");
			
			// 삽입할 110 문자열 만들기
			StringBuilder moveString = new StringBuilder();

			for (int j = 0; j < moveCount; j++) {
			    moveString.append("110");
			}

			// 마지막 0 바로 뒤에 삽입
			output.insert(lastZeroIndex + 1, moveString);
			
			answer[i] = output.toString();
			
		}

		return answer;

	}

}