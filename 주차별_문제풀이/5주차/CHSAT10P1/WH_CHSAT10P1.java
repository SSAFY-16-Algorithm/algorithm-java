import java.util.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int[] positions = new int[N];
        Arrays.sort(positions);
        
        for (int i = 0; i < N; i++) {
            positions[i] = scanner.nextInt();
        }

        Arrays.sort(positions);

        

        // Please write your code here.
        //ArrayList<Integer> candidates=new ArrayList<>();
        // for (int i=1;i<=positions[N-1]<i++){
        //     candidates.add(i); //보수 패치의 길이 후보
        // }

        //가운데 찔러보고 그걸로 통과 가능한지 판별하는 이진탐색
        //통과 가능한지 확인하는방법: 해당 길이의 보수패치를 K개 이하로 써서 모두 보수 가능하면 가능 

        int l=1;
        int r=positions[N-1];

        while (l<r){
            int c=(l+r)/2; 
            int cnt=0;
            //패치길이 c로 가능한지 확인 
            //remove 해서 indexoutofbounds 오류 났었음 
            int i=0;
            while(i<N){
                int end=positions[i]+c-1;
                cnt++;
                while(i<N && positions[i]<=end){
                    i++;
                }
            }

            if (cnt<=K){ 
                r=c; 
            }else{ 
                l=c+1; 
            } 
        }
        System.out.println(l);
    }    
}
