import java.util.*; 
    class Solution {
    public int[] solution(String[] gems) {
        int[] answer = new int[2];
        
        // 보석 종류 수집 시작 
        HashSet<String> jewels=new HashSet<>();
        for (int i=0;i<gems.length;i++){
            jewels.add(gems[i]);
        }
        
        // 보석 종류별 개수 저장 해시맵 생성
        HashMap<String, Integer> bag=new HashMap<>();
        for(int i=0;i<gems.length;i++){   
            bag.put(gems[i],0);
        }
        
       // 구간 탐색 시작 
        int left=0;
        int types=0;
        int min_length = Integer.MAX_VALUE;
        
        // right++ 순회
        for(int right=0;right<gems.length;right++){
             //추가된 right에 대해 개수 추가해줌 
            int temp=bag.get(gems[right]);
            if (temp==0) types++;
            bag.put(gems[right], temp+1);
            
            //만약 전종류 보유시 left를 늘림 
            if(types==jewels.size()){
                while(bag.get(gems[left])>1){
                    bag.put(gems[left], bag.get(gems[left])-1);
                    left++;
                }
                
                if (right-left<min_length){
                    min_length=right-left;
                    answer[0]=left+1;
                    answer[1]=right+1;
                }
            }   
        }
        return answer;
    }
}
