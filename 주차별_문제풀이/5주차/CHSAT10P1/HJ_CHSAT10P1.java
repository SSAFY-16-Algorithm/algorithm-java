import java.util.Scanner;

public class Main {
	
    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        
        int[] positions = new int[N];
        for (int i = 0; i < N; i++) {
            positions[i] = scanner.nextInt();
        }
        
        // 구멍을 덮을 수 있는 패치의 길이를 가지고 이분탐색 진행
        
        int low = 1;
        int high = positions[N-1] - positions[0] + 1;
        int answer = high;
        
        while (low <= high) {
        	int mid = (low + high) / 2;
        	
        	if (check(positions, K, mid)) {
        		answer = mid;
        		high = mid - 1;
        	} else {
        		low = mid + 1;
        	}
        }
        
        System.out.println(answer);
        
    }

    static boolean check(int[] positions, int K, int L) {
    	
    	int patchCount = 1;
    	int coverEnd = positions[0] + L - 1;
    	
    	for (int i = 1; i < positions.length; i++) {
    		if (positions[i] > coverEnd) {
    			patchCount++;
    			coverEnd = positions[i] + L - 1;
    			
    			if (patchCount > K) {
        			return false;
        		}
    		}
    	}
    	
    	return true;
    
    }
}