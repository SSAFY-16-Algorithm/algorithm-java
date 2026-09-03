import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;
        Arrays.sort(people); //오름차순 정렬
        
        //ArrayDeque로 구현
        ArrayDeque<Integer> queue=new ArrayDeque<>();
        for (int i=people.length-1; i >= 0; i--) {
            queue.add(people[i]); //내림차순 정렬 
        }
        
        while(!queue.isEmpty()){
            int weight=0;
            weight+=queue.poll();
            if (!queue.isEmpty() && weight + queue.peekLast() <= limit) {
                queue.pollLast();
            }
            answer++;     //보트 하나 보냄                    
        }
        return answer;
    }
}
