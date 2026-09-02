
class Programmers_Greedy_1 {
	/*
	 * 1. 위/아래로 움직여야 하는 양 계산 
	 * int[] shiftDistance: 각각의 글자가 되기 위해 이동해야 하는 값
	 * 
	 * 2. 왼/오로 움직여야 하는 양 계산
	 * next: A의 연속 중 제일 마지막
	 * 1. 왼 -> 오 -> 왼
	 * 2. 오 -> 왼 -> 오
	 * 3. 왼쪽/오른쪽으로 쭉 가기
	 * 
	 * answer = shiftDistance 합 + 왼오 움직이는 양
	 */
	
	public static void main(String[] args) {
		System.out.println(solution("AZAAAZA"));
	}
    public static int solution(String name) {
    	int answer = 0;
    	int N = name.length();
    	
    	boolean hasA = false;
    	for(int n = 0; n < N; n ++) {
    		int shiftUp = name.charAt(n) - 'A';
    		int shiftDown = 'Z' - name.charAt(n) + 1;
    		
    		answer += Math.min(shiftDown, shiftUp);
    	}
    	
    	
    	int minWay = N - 1;
    	
    	// 연속된 A 구간을 건너뛰면서 커서의 최소 이동 횟수를 구한다.
    	for (int firstA = 0; firstA < N; firstA++) {

    	    // A가 아닌 위치는 연속된 A 구간의 시작점이 아니므로 건너뛴다.
    	    if (name.charAt(firstA) != 'A') {
    	        continue;
    	    }

    	    // firstA부터 시작하는 연속된 A 구간의 끝을 찾는다.
    	    int lastA = firstA + 1;

    	    while (lastA < N && name.charAt(lastA) == 'A') {
    	        lastA++;
    	    }

    	    // 오른쪽으로 이동한 후 왼쪽으로 돌아오는 경우
    	    minWay = Math.min(minWay,
    	            firstA * 2 + N - lastA);

    	    // 왼쪽으로 이동한 후 오른쪽으로 돌아오는 경우
    	    minWay = Math.min(minWay,
    	            (N - lastA) * 2 + firstA);
    	}
    	
    	
    	answer += minWay;
    	
     	return answer;
    }
}