import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class MY_P42628 {
	static StringBuilder sb;
	static StringTokenizer tk;
	// 최소, 최댓값을 자주 Delete하기 때문에 LinkedList가 좋다고 판단
	static LinkedList<Integer> q;
	
	public static int[] solution(String[] operations) {
		q = new LinkedList<>();
		
		for (String oper: operations) {
			tk = new StringTokenizer(oper);
			String operation = tk.nextToken();
			int num = Integer.parseInt(tk.nextToken());
			
			switch(operation) {
				case "I": {
					addList(num);
					break;
				}
				case "D": {
					if (q.size() == 0) continue;
					removeList(num);
					break;
				}
				default: {
					break;
				}
			}
			
		}
		
		if (q.size() == 0) 
			return new int[] {0, 0};
        int[] answer = {q.getLast(), q.getFirst()};
        return answer;
    }
	
	public static void addList(int num) {
		// 
		int idx = 0;
		while(idx < q.size() && q.get(idx) <= num) {
			idx++;
		}
//		System.out.println("size : " + q.size() + "index : " + idx);
		q.add(idx, num);
	}
	
	public static void removeList(int num) {

		switch (num) {
		case 1: {
			q.removeLast();
			break;
		}
		case -1: {
			q.poll();
			break;
		}
		default: {
			break;
		}
		}
	}
	
	public static void main(String[] args) {
	    String[] input1 = {
	        "I 16", "I -5643", "D -1",
	        "D 1", "D 1", "I 123", "D -1"
	    };

	    String[] input2 = {
	        "I -45", "I 653", "D 1",
	        "I -642", "I 45", "I 97",
	        "D 1", "D -1", "I 333"
	    };

	    System.out.println(Arrays.toString(solution(input1)));
	    System.out.println(Arrays.toString(solution(input2)));
	}
}
