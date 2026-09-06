public class MY_P42860 {
    public static int solution(String name) {
        int answer = 0;
    	int N = name.length();
        // 일단 알파멧을 바꾸는 연산만 
        for (int i = 0; i < N; i++) {
        	char c = name.charAt(i);
        	 answer += Math.min(c - 'A', 1 + 'Z' - c);
        }
        
        // 최소 이동 거리 검사
        int move = N - 1;
        for (int i = 0; i < N; i++) {
        	int next = i + 1;
        	while(next < N && name.charAt(next) == 'A') {
        		next++;
        	}
        	move = Math.min(move, 
        			Math.min(i * 2 + N - next , (N - next) * 2 + i));
        }
        answer += move;
        
        return answer;
    }
    
    public static void main(String[] args) {
		System.out.println(solution("JEROEN"));
		System.out.println(solution("JAN"));
	}
}
