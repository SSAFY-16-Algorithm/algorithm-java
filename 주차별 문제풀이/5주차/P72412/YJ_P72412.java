import java.util.*;

class Solution {
    static HashMap<String, ArrayList<Integer>> map;
    
    public int[] solution(String[] info, String[] query) {
        map = new HashMap<>();
		 
		 int[] answer = new int[query.length];
		 
		 // 가능한 모든 조합 만들기
		 for(int i=0; i<info.length; i++) {
			 dfs("", info[i].split(" "), 0);
		 }
		 
		 // score 오름차순 시키기 -> 이분탐색 해야돼서
		 for(String key: map.keySet()) {
			 Collections.sort(map.get(key));
		 }
		 
		 // 쿼리마다 몇명인지 저장
		 for(int i=0; i<query.length; i++) {
			 answer[i] = countAppl(query[i].replaceAll(" and", "").split(" "));
		 }
		 
	     return answer;
    }
    
    public void dfs(String str, String[] dfsInfo, int depth) {
		 if(depth==4) {
			 int score = Integer.parseInt(dfsInfo[4]);
			 if(map.containsKey(str)) {
				 // value ArrayList에 해당 score 추가
				 map.get(str).add(score);
			 } else { // 없으면 새로 str, list 쌍 추가
				 ArrayList<Integer> list = new ArrayList<>();
				 list.add(score);
				 map.put(str, list);
			 }
			 return;
		 }
		 
		 dfs(str+"-",dfsInfo, depth+1); // "-" 포함 조합
		 dfs(str+dfsInfo[depth], dfsInfo, depth+1); // "-" 제외 조합
	 }
	 
	 // 입력받은 쿼리마다 지원자 몇명인지 카운트하는 함수(여기서 String[] query는 query 하나를 split으로 나눈것.) 
	 public int countAppl(String[] query) {
		 String key = "";
		 int score =0; 
		 
		 // 지원자 조건과 점수 분리
		 for(int i=0; i<query.length; i++) {
			 if(i!=query.length-1) key += query[i];
			 else score = Integer.parseInt(query[i]);
		 }
		 
		 if(map.containsKey(key)) {
			 ArrayList<Integer> list = map.get(key);
			 int low = 0; 
			 int mid = 0; 
			 int high = list.size()-1;
			 
			 while(low<=high) {
				 mid = (low+high)/2;
				 
				 if(list.get(mid)<score) {
					 low = mid+1;
				 } else {
					 high = mid-1;
				 }
			 }
			 return list.size()-low; // 최소 점수 이후 사람들 몇 명인지
		 }
		 return 0; // 없으면 0명 반환
	 }
    
}