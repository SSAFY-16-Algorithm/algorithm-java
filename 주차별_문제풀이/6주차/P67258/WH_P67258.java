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
        int right=1;
        
        //int min_length=right-left;
        
        bag.put(gems[left],1);
        int types=1;
        
        // right++ 순회
        while(right<gems.length){
            //종류 꽉 차면 
            if(types==jewels.size()){
                //left++..
                while(true){
                    int temp=bag.get(gems[left]);
                    if (temp>1){
                        bag.put(gems[left], temp-1);
                        left++;
                    }else{
                        break;
                    }
                }           
            }
            
            //종류 꽉 차기 전
            int temp=bag.get(gems[right]);
            if (temp==0) types++;
            bag.put(gems[right], temp+1);
            right++;
        }
        
        answer[0]=left;
        answer[1]=right;
        
        return answer;
    }
}

//미완성
