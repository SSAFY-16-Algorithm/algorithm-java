import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution { 
    static int P;   
    static int search(int goal) {
        int cnt=0;
        int l=1, r=P;
         
        while(l<=r) {
            int c=(l+r)/2;
            cnt++;
            if (c==goal) {
                return cnt;
            }else if (c<goal) {
                l=c;
            }else {
                r=c;
            }
        }
        return cnt;
    }
     
    public static void main(String[] args) throws Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int T=Integer.parseInt(br.readLine());
         
        for (int t=1;t<=T;t++) {
            StringTokenizer st=new StringTokenizer(br.readLine());
            P=Integer.parseInt(st.nextToken());
            int Pa=Integer.parseInt(st.nextToken());
            int Pb=Integer.parseInt(st.nextToken());
             
            int a=search(Pa);
            int b=search(Pb);
             
            if(a==b) {
                System.out.println("#"+t+" 0");
            }           
            else if (a>b) {
                System.out.println("#"+t+" B");
            }else {
                System.out.println("#"+t+" A");
            }
        }       
    }
}
 
                                
