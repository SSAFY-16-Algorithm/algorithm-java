//정확성 테스트 다 통과했는데 효율성 테스트 다 틀림 아ㅠㅠ

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
            int cur_idx = 0;
            boolean complete = false;
            
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
                        moved = true;
                        break;
                    }
                }
                
                // 이번 사람이 건넘
                if (complete) {
                    answer++;
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
