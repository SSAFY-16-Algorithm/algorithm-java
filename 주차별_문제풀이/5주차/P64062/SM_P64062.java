public class SM_P64062 {
    public static void main(String[] args) {
        SM_P64062 solution = new SM_P64062();
        int[] stones = {2, 4, 5, 3, 2, 1, 4, 2, 5, 1};
        int k = 3;
        System.out.println(solution.solution(stones, k));
    }
    public int solution(int[] stones, int k) {
        int answer = 0;

        int left = 1;
        int right = 0;

        for(int stone: stones) {
            right = Math.max(right, stone);
        }

        while(left <= right) {
            int mid = (left + right) / 2;
            int count = 0;

            for(int stone: stones) {
                if(stone < mid) {
                    count++;
                } else {
                    count = 0;
                }

                if(count >= k) {
                    break;
                }
            }

            if(count >= k) {
                right = mid - 1;
            } else {
                answer = mid;
                left = mid + 1;
            }
        }
        return answer;
    }

}
