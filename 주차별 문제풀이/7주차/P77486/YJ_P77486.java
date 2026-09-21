import java.util.*;

class YJ_P77486 {
    static HashMap<String, String> relation;
	static HashMap<String, Integer> profits;
    
    public static int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        
    	// enroll[i]의 부모로 referral[i] 저장하기. 
    	relation = new HashMap<>();
    	for(int i=0; i<enroll.length; i++) {
    		relation.put(enroll[i], referral[i]);
    	}
    	profits = new HashMap<>(); // 각 판매원의 전체 이익금 누적해두는 맵
    	// seller, amount로 계산
    	for(int i=0; i<seller.length; i++) {
    		// 본인한테 90% 저장하고 부모 노드로 10% 올리기
    		String curSeller = seller[i];
    		int profit = amount[i];	
    		distribution(curSeller, profit*100);
    	}
    	
    	// enroll 순서대로 
    	int[] answer = new int[enroll.length];
    	for(int i=0; i<answer.length; i++) {
    		answer[i] = profits.getOrDefault(enroll[i],0); // 안들어간 노드가 있을 수도 있으므로 
    	}
        return answer;
    }
    
    public static void distribution(String curSeller, int profit) {
    	if(curSeller.equals("-")) {
    		return; 
    	}
    	
    	int parentProfit = profit /10;
    	int myProfit = profit - parentProfit;
    	
    	// 부모에게 줄 수익금이 1 미만이면 본인이 다 갖고 종료
    	if(parentProfit<1) {
    		profits.put(curSeller, profits.getOrDefault(curSeller, 0) + myProfit);
    		return;
    	}else {
    		// 부모에게 줄 수익금 1 이상이면 
    		// -> 본인이 90%, 부모 10% 준다. 
    		profits.put(curSeller, profits.getOrDefault(curSeller, 0)+myProfit);
    		distribution(relation.get(curSeller), parentProfit);
    	}
    }
}