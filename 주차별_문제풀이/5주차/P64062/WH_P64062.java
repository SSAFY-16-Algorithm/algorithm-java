class Solution {
    public static boolean canPass(int num){
        int cannot_pass=0;
        for (int i=0;i<stones.length;i++){
            if (stones[i]<num){
                cannot_pass++;
                if (cannot_pass>=k){ // 연속으로 k개 이상의 돌을 못 밟게 되면 건널 수 없음
                    return false;
                }
            }
            else{
                cannot_pass=0; // 밟을 수 있는 돌을 만나면 카운트 리셋            
            }
        }
    return true;
    }    
    
    static int [] stones;
    static int k;
    
    public int solution(int[] temp, int kk) {
        k=kk;
        stones=temp;
        
        int answer = 0;
        int l=0;
        int r=200000000;

        while(l<=r){
            int c=(l+r)/2;   //c명의 사람이 건널 수 있는지 봄 
            if (canPass(c)){
                answer=c;
                l=c+1;
            }else{
                r=c-1;
            }
        }
        return answer;
    }
}
