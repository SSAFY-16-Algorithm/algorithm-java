import java.util.*;

public class P131701 {
    public int solution(int[] elements) {
		int totalLen = elements.length;
		
		// 계산 쉽게 하기 위해 2배로 만들어준다. 
		int[] copyElements = new int[totalLen*2];
		for(int i=0; i<totalLen; i++) {
			copyElements[i] = elements[i];
			copyElements[i+totalLen] = elements[i];
		}
        // 중복 피하기 위해 hash 사용
		HashSet<Integer> set = new HashSet<>();
        
		for(int len=1; len<=totalLen; len++) {
			for(int j=0; j<totalLen; j++) {
				int sum = 0; 
				for(int s=j; s<j+len; s++) {
					sum+=copyElements[s];
				}
				set.add(sum);
			}
		}
    
        return set.size();
    }
}