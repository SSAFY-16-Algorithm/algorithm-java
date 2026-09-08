//수정했는데 정확성 테스트 다 통과했는데 효율성 테스트 다 틀림 아ㅠㅠ

import java.util.*;
class Solution {
    public int solution(int[] temp, int k) {
        int answer = 0;
        
        // 맨 앞에 0 삽입 후 원본 배열 카피
        int[] stones = new int[temp.length + 1];
        stones[0] = 100;
        
        for (int i = 0; i < temp.length; i++) {
            stones[i + 1] = temp[i];
        }
        
        while (true) {
            //시작점에서 첫 출발
            int cur_idx = 0;
            boolean complete = false;
            ArrayList<Integer> visited_idx=new ArrayList<>();
            
            while (true) {
                boolean moved = false;
                
                // 다음으로 밟을 수 있는 디딤돌 중 가장 가까운 것 선택
                for (int i = 1; i <= k; i++) {
                    
                    // 마지막 돌을 넘어감 → 한 명 건넘
                    if (cur_idx + i >= stones.length) {
                        complete = true;
                        break;
                    }
                    
                    // 갈 수 있는 돌이 있는 경우 -> 해당 돌 -- 해주고 인덱스 이동, 그 인덱스에서 다시 for문 시작 (*)
                    if (stones[cur_idx + i] != 0) {
                        stones[cur_idx + i]--;
                        cur_idx = cur_idx + i;
                        visited_idx.add(cur_idx);
                        moved = true;
                        break;
                    }
                }
                
                // 이번 사람이 건넘
                if (complete) {
                    answer++;
                    //이 사람이 이동했던 인덱스들이 저장된 arraylist 사용 -> 참조값 중 min 찾아서 그만큼 다 빼줌 
                    int min_num=Integer.MAX_VALUE;
                    for (int i=0;i<visited_idx.size();i++){
                        min_num=stones[visited_idx.get(i)]<min_num?stones[visited_idx.get(i)]:min_num;
                    }
                    for (int i=0;i<visited_idx.size();i++){
                        stones[visited_idx.get(i)]-=min_num;
                    }
                    answer+=min_num;
                    break;
                }
                
                // 아직 건너지 못했는데 k칸 안에 밟을 수 있는 돌이 없는 경우 여기서 종료 
                if (!moved) {
                    break;
                }
                
                //(*)는 위의 조건에 해당되지 않으므로 다시 2번째 while(true)로 올라감 
            }
            
            // 아직 건너지 못했는데 k칸 안에 밟을 수 있는 돌이 없고 이번 사람이 못 건넜으면 종료
            if (!complete) {
                break;
            }
            
            //complete가 true면 처음부터 다시 시작 
        }
        
        return answer;
    }
}
