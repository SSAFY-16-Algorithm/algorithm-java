import java.util.*;

public class P178870 {
   public int[] solution(int[] sequence, int k) {
        
		int totalLength = sequence.length;
		
		int start = 0; 
		int end = 0; 
		
		int minLength = Integer.MAX_VALUE; 	// 수열 길이
		int firstIdx = 0; 
		int lastIdx = 0; 
		
		// 조건 만족 수열 여러개인 경우 minLength를 가지는 것, minIdx를 가지는 것을 우선으로 한다. 
		
		int sum = sequence[0]; // 둘 다 0, 0일 때
		while(start < totalLength && end <totalLength) {
			if(sum == k) {
				int currentLength = end - start +1; 
				if(currentLength < minLength) {
					minLength = currentLength;
					firstIdx = start;
					lastIdx = end;
				} // 길이가 같은 경우에는 이미 idx 빠른 순서가 저장되어있을 것
				sum -= sequence[start];
				start++;
			} else if(sum <k) {
				end++;
				if(end <totalLength) {	// 범위 안넘어가도록 검사	(end를 먼저 증가시키므로)			
					sum += sequence[end];
				}
			} else { //sum > k 
				sum -= sequence[start];
				start++; 
			}
		}
		
        return new int[] {firstIdx, lastIdx};
    }
}
