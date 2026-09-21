import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		int T=Integer.parseInt(st.nextToken());
		
		for(int t=1;t<=T;t++) {
			st= new StringTokenizer(br.readLine());
			int V=Integer.parseInt(st.nextToken());
			int E=Integer.parseInt(st.nextToken());
			int VA=Integer.parseInt(st.nextToken());
			int VB=Integer.parseInt(st.nextToken());
			
			int[] v1_list=new int[E];
			int[] v2_list=new int[E];
			
			st= new StringTokenizer(br.readLine());
			for(int i=0;i<E;i++) {
				int v1=Integer.parseInt(st.nextToken());
				int v2=Integer.parseInt(st.nextToken());
				v1_list[i]=v1;
				v2_list[i]=v2;			
			}
			
			ArrayList<Integer> VA_parents = new ArrayList<>();
			ArrayList<Integer> VB_parents = new ArrayList<>();
			
			int VA_temp=VA;
			int VB_temp=VB;
			
			
			boolean VA_done = false;
			boolean VB_done = false;

			while (!VA_done || !VB_done) {

			    VA_done = true;
			    VB_done = true;

			    for (int i = E - 1; i >= 0; i--) {
			        if (v2_list[i] == VA_temp) {
			            VA_temp = v1_list[i];
			            VA_parents.add(VA_temp);
			            VA_done = false;
			        }

			        if (v2_list[i] == VB_temp) {
			            VB_temp = v1_list[i];
			            VB_parents.add(VB_temp);
			            VB_done = false;
			        }
			    }
			}
			
			int answer=0;
			if (VA_parents.size()>=VB_parents.size()) {
				for(int i=0;i<VA_parents.size();i++) {
					if(VB_parents.contains(VA_parents.get(i))) {
						answer=VA_parents.get(i);
						break;
					}
				}
			}else {
				for(int i=0;i<VB_parents.size();i++) {
					if(VA_parents.contains(VB_parents.get(i))) {
						answer=VB_parents.get(i);
						break;
					}
				}
			}
			
			ArrayList<Integer> answerList=new ArrayList<>();
			answerList.add(answer);
			int cnt=1;
			int beforeSize = 0;

			while (beforeSize != answerList.size()) {

			    beforeSize = answerList.size();

			    for (int i = 0; i < E; i++) {
			        if (answerList.contains(v1_list[i])) {

			            if (!answerList.contains(v2_list[i])) {
			                answerList.add(v2_list[i]);
			                cnt++;
			            }
			        }
			    }
			}
			
			System.out.println("#"+t+" "+answer+" "+cnt);
			
		}
		
	}
}
