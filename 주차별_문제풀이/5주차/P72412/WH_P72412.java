import java.util.*;

class Solution {
    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        HashMap<String, ArrayList<Integer>> map=new HashMap<>();
        for (int i=0;i<info.length;i++){
            String[] person = info[i].split(" ");
            //person[0]~ person[3]: 조건, person[4]:점수
            int score = Integer.parseInt(person[4]);
                
            // person의 조건으로 16개 key 만들기
            for (int a = 0; a <= 1; a++) {
                for (int b = 0; b <= 1; b++) {
                    for (int c = 0; c <= 1; c++) {
                        for (int d = 0; d <= 1; d++) {
                            int[] check = {a, b, c, d};
                            String key = "";
                            for (int j = 0; j < 4; j++) {
                                if (check[j] == 1) {
                                    key += person[j];
                                } else {
                                    key += "-";
                                }
                            }

                            if (!map.containsKey(key)) { //key 처음 넣을떄만 ArrayList 초기화해줌 
                                map.put(key, new ArrayList<>());
                            }
                            map.get(key).add(score);
                        }
                    }
                }
            }
        }
        for (ArrayList<Integer> scores : map.values()) {
            Collections.sort(scores);
        }
        //모든 info에 대해 초기화 끝남
        
        //query 보기
        int target;
        for (int i=0;i<query.length;i++){
            String qr="";
            String[] querystring=query[i].split(" ");
            target=Integer.parseInt(querystring[querystring.length-1]);
            for (int j=0;j<querystring.length-1;j++){
                if (!querystring[j].equals("and")){
                    qr+=querystring[j];
                }
            }
        
            //처리
            ArrayList<Integer> candidates=map.get(qr); //중에 
            //이진탐색
            if (candidates == null) {
                answer[i] = 0;
                continue;
            }
            
            int l=0;int r=candidates.size();
            while(l<r){
                int c=(l+r)/2;
                if (candidates.get(c)>=target){
                    r=c;
                }
                else{
                    l=c+1;
                }
            }
            answer[i]=candidates.size()-l;
        }
        return answer;
    }
}
