import java.util.TreeMap;

public class Solution {
	public int[] solution(String[] operations) {
		// key: 큐에 들어 있는 숫자
        // value: 해당 숫자가 들어 있는 개수
        TreeMap<Integer, Integer> numberCount = new TreeMap<>();

        for (String operation : operations) {

            // 명령어의 첫 글자
            // "I 16"이라면 'I'
            // "D -1"이라면 'D'
            char command = operation.charAt(0);

            // 인덱스 2부터 마지막까지가 숫자 부분
            // "I 16"  -> "16"
            // "D -1"  -> "-1"
            int number = Integer.parseInt(operation.substring(2));

            // 삽입 명령
            if (command == 'I') {

                // number가 없으면 0을 가져오고, 있으면 기존 개수를 가져온다.
                int count = numberCount.getOrDefault(number, 0);

                // 숫자의 개수를 1 증가시킨다.
                numberCount.put(number, count + 1);
            }

            // 삭제 명령
            else if (command == 'D') {

                // 비어 있는 큐에 삭제 명령이 들어오면 무시한다.
                if (numberCount.isEmpty()) {
                    continue;
                }

                int target;

                // D 1이면 최댓값 삭제
                if (number == 1) {
                    target = numberCount.lastKey();
                }

                // D -1이면 최솟값 삭제
                else {
                    target = numberCount.firstKey();
                }

                // 삭제할 숫자의 현재 개수
                int count = numberCount.get(target);

                // 해당 숫자가 하나만 남았다면
                // TreeMap에서 숫자 자체를 제거한다.
                if (count == 1) {
                    numberCount.remove(target);
                }

                // 같은 숫자가 여러 개라면
                // 개수만 하나 감소시킨다.
                else {
                    numberCount.put(target, count - 1);
                }
            }
        }

        // 모든 연산이 끝난 후 큐가 비어 있다면
        if (numberCount.isEmpty()) {
            return new int[]{0, 0};
        }

        // [최댓값, 최솟값] 순서로 반환
        return new int[]{
            numberCount.lastKey(),
            numberCount.firstKey()
        };
    }
}