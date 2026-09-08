import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class SM_P72412 {
	static int N;
	static int idx = 4;
	static HashMap<String, ArrayList<Integer>> map;
	static boolean[] isSelected;
	public static void main(String[] args) {
		String[] info = {"java backend junior pizza 150","python frontend senior chicken 210","python frontend senior chicken 150","cpp backend senior pizza 260","java backend junior chicken 80","python backend senior chicken 50"};
		String[] query = {"java and backend and junior and pizza 100","python and frontend and senior and chicken 200","cpp and - and senior and pizza 250","- and backend and senior and - 150","- and - and - and chicken 100","- and - and - and - 150"};
		
		int answer[] = solution(info, query);
		System.out.println(Arrays.toString(answer));
	}
	
	public static int[] solution(String[] info, String[] query) {
		N = query.length;
        int[] answer = new int[N];
        
        isSelected = new boolean[4];
        map = new HashMap<>();
        
        for(int i = 0; i < info.length; i ++) {
        	String[] infoSplit = info[i].split(" ");  
        	addMap(0, infoSplit);
        
        }
        
        for(ArrayList<Integer> scores : map.values()) {
    	    Collections.sort(scores);
    	}
        
        for(int q = 0; q < query.length; q ++) {
        	String[] querySplit = query[q].replace(" and ", " ").split(" ");
        	String queryStr = "";
        	for(int i = 0; i < idx; i ++) {
        		queryStr += querySplit[i];
        	}
        	int queryInt = Integer.parseInt(querySplit[idx]);
        	
        	ArrayList<Integer> scores = map.get(queryStr);
        	
        	if(scores == null) {
        		answer[q] = 0;
        		continue;
        	}

        	
        	int left = 0;
        	int right = scores.size();
        	
        	while(left < right) {
        		int center = (left + right) / 2;
        		if(scores.get(center) >= queryInt) {
        			right = center;
        		} else {
        			left = center + 1;
        		}
        		
        		
        	}
        	
        	answer[q] = scores.size() - left;
        }
        
        
        return answer;
    }

	
	static void addMap(int cnt, String[] info) {
		if(cnt == idx) { 
			String infoStr = "";
			for(int i = 0; i < idx; i ++) {
				infoStr += isSelected[i] ? info[i]: "-";
			}
			int infoInt = Integer.parseInt(info[idx]);
			if(!map.containsKey(infoStr)) {
				ArrayList<Integer> arr = new ArrayList<>();
				arr.add(infoInt);
				map.put(infoStr, arr);
			} else {
				ArrayList<Integer> arr = map.get(infoStr);
				arr.add(infoInt);
				map.put(infoStr, arr);
			}
			return;
		}
		
		
		isSelected[cnt] = true;
		addMap(cnt + 1, info);
		isSelected[cnt] = false;
		addMap(cnt + 1, info);
	}
	
}
