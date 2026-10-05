import java.util.*;
class Solution {
    String[] answer;
    boolean[] isUsed;
    ArrayList<String> route;
     String[][] tickets;
    
    void dfs(String current){
        if (route.size() == tickets.length + 1){
            answer = route.toArray(new String[0]);
            return;
        }
        
        for(int i=0;i<tickets.length;i++){
            if (isUsed[i]==false && tickets[i][0].equals(current)){
                
                //선택
                route.add(tickets[i][1]);
                isUsed[i]=true;
                
                dfs(tickets[i][1]);
                
                //원상복구
                isUsed[i]=false;
                route.remove(route.size() - 1);
                if (answer!=null){
                    return;
                }
            }
        }
    }
    
    public String[] solution(String[][] tickets) {
        this.tickets = tickets;
        
        Arrays.sort(tickets, (a, b) -> {
            if (a[0].equals(b[0])) {
                return a[1].compareTo(b[1]);
            }
            return a[0].compareTo(b[0]);
        });
        
        answer = null; 
        isUsed=new boolean[tickets.length];        
        route=new ArrayList<>();
        
        //무조건 ICN에서 출발
        route.add("ICN");
        String current="ICN";
        dfs(current);
        
        return answer;
    }
}
