import java.util.*;
import java.io.*;

//[10차 1번] 도로 보수 로봇
public class YJ_CHSAT10P1 {
    static int[] holes;
    static int N, K; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        holes = new int[N];
        for(int i=0; i<N; i++) {
            holes[i] = Integer.parseInt(st.nextToken());
        }
        
        // 작은 순으로 정렬
        Arrays.sort(holes);
        int initLow = 1; 
        int initHigh = holes[N-1] - holes[0] +1;
        int result = findPatchLength(initLow, initHigh);
        System.out.println(result);
    }
    
    public static int findPatchLength(int low, int high) {
        
        // 종료 조건: 범위 더 이상 못 줄일 때
        if(low >= high) {
            return low;
        }
        
        
        int mid = (low+high)/2;
        
        // 길이가 mid일 때 K개 이하로 모든 구멍을 막을 수 있는 경우 -> high 값을 mid로 변경 후 재진행(최소값을 찾기 위해)
        if(canCover(mid)) {
            return findPatchLength(low, mid);
        }
        // 길이가 mid일 때 K개 이하로 모든 구멍을 막을 수 없는 경우 -> low를 mid+1로 변경 후 길이를 찾는다. 
        else { 
            return findPatchLength(mid+1, high);
        }
        
        // 문제점: 최소 길이인지 어떻게 구별할 것인가 ? -> 완전 탐색으로 반복하면 자동으로 찾아지나 ? 
    }
    
    // 길이를 받았을 때 K개 이하로 커버 가능한지 확인하는 함수 
    public static boolean canCover(int length) {
        
        int count = 0; 
        int lastCovered = -1; //마지막으로 패치가 덮은 인덱스
        int maxK = K; 
        
        // 정렬된 holes 배열을 돌며 커버 치고, 커버 개수 확인
        
        for(int i=0; i<N; i++) {
            if(holes[i] > lastCovered) {
                count++; // 새 패치 이용해야 함
                lastCovered = holes[i] + length -1; 
            }
        }
        
        if(count<=K) {
            return true; 
        }
        else {            
            return false; 
        }
    }

}