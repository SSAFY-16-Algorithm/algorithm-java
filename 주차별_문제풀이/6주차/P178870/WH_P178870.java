class Solution {
    public int[] solution(int[] sequence, int k) {
        int left = 0;
        int right = 0;
        int sum=sequence[0];
        int min_length=sequence.length;
        
        int[] answer=new int[2];
        
        while(right<sequence.length){
            if (sum==k){
                int current_length=right-left;
                if (current_length<min_length){
                    answer[0]=left;
                    answer[1]=right;
                    min_length=current_length;
                }
                sum-=sequence[left];
                left++;
            }
            else if (sum>k){
                sum-=sequence[left];
                left++;
            }else{
                right++;
                if(right<sequence.length){
                    sum+=sequence[right];
                }
            }
        }
        
        return answer;
    }
}
