import java.util.Scanner;
public class MY_CHSAT10P1 {
    static int size;
    static int limit;
    static int[] positions;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        positions = new int[N];
        for (int i = 0; i < N; i++) {
            positions[i] = scanner.nextInt();
        }
        // Please write your code here.
        size = N; limit = K;
        
        int length = binarySearch(1, positions[N-1] - positions[0] + 1);
        System.out.println(length);
    }

    // size는 전부 다 탐색했는지 확인을 위한 크기
    // cnt는 패치 몇개가 가능한지 체크
    public static int binarySearch(int start, int end) {
        // mid는 현재 찾고 있는 길이
        // 이 길이가 가능하다면 이것보다 긴 길이도 전부 가능하기 때문에 줄이면 됨
        int mid = (start + end) / 2;
        if (start >= end) {
            return mid;
        }

        int idx = 0, cnt = 0;
        // cnt만큼 패치를 배치하고, 모든 구멍을 다 커버했는지 검사해야함
        while(cnt < limit && idx < size) {
            int cover = positions[idx] + mid - 1;

            while(idx < size && positions[idx] <= cover) {
                // System.out.println("idx: " + idx + ", size: " + size);
                idx++;
            }
            cnt++;
        }
        // System.out.println("cnt: " + cnt + ", idx: " + idx);

        if (idx < size) {
            return binarySearch(mid + 1, end);
        }
        else {
            return binarySearch(start, mid);
        }

    }
}
}
