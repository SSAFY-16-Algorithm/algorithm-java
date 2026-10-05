public class MY_P64062 {
    static int[] stones;
    static int answer;
    public int solution(int[] stones, int k) {
        MY_P64062.stones = stones;
        binarySearch(1, 200000000, k);
        return answer;
    }
    
    public void binarySearch(int left, int right, int num) {
        // overflow 방지
        int mid = left + (right - left) / 2;
        if (left > right) {
            return;
        }
        
        int jump = 1, maxJump = 1;
        for (int i = 0; i < stones.length; i++) {
            if (stones[i] < mid) jump++;
            else {
                maxJump = Math.max(maxJump, jump);
                jump = 1;
            }
        }
        maxJump = Math.max(maxJump, jump);
        
        if (maxJump <= num) {
            answer = mid;
            binarySearch(mid + 1, right, num);
        }
        else {
            binarySearch(left, mid - 1, num);
        }
    }
}