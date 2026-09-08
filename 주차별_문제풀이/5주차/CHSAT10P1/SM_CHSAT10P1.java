import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class SM_CHSAT10P1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] positions = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            positions[i] = Integer.parseInt(st.nextToken());
        }

        int left = 1;
        int right = positions[N - 1] - positions[0];
        int mid = (left + right) / 2;

        while(left <= right) {


            int patchCnt = 0;
            int coveredUntil = -1;

            for(int i = 0; i < N; i++) {
                if(positions[i] <= coveredUntil) {
                    continue;
                }

                patchCnt ++;

                coveredUntil = positions[i] + mid - 1;
            }

            if(patchCnt <= K) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }

            mid = (left + right) / 2;
        }

        System.out.println(left);
    }
}
