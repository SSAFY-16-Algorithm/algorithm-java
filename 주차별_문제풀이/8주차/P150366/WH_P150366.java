import java.util.*;

class Solution {
    
    public String[] solution(String[] commands) {
        ArrayList<String> answerList = new ArrayList<>();        
        int[] parent = new int[2500];
        for(int i = 0; i < 2500; i++){
            parent[i] = i;
        }
        
        String[] value = new String[2500];
        
        String[] command;
        for(int idx = 0; idx < commands.length; idx++){
            command = commands[idx].split(" ");
            String op = command[0];
            
            if (op.equals("UPDATE")){
                if (command.length == 4){ //값 초기화
                    int r = Integer.parseInt(command[1]) - 1;
                    int c = Integer.parseInt(command[2]) - 1;
                    // 해당 그룹의 대표 인덱스(parent)에만 값 저장
                    int p = parent[r*50+c];
                    value[p] = command[3];
                    
                } else { //3 : v1->v2 전부 변환
                    for(int i = 0; i < 2500; i++){
                        if (value[i] != null && value[i].equals(command[1])){
                            value[i] = command[2];
                        }
                    }
                }
                
            } else if(op.equals("MERGE")){
                 // (r1,c1)과 (r2,c2) 병합
                // 각 노드의 집합에 다른 원소가 있으면 
                // 한번에 집합 내 모든 원소의 대표자가 바뀜 
                int r1 = Integer.parseInt(command[1]) - 1;
                int c1 = Integer.parseInt(command[2]) - 1;
                int r2 = Integer.parseInt(command[3]) - 1;
                int c2 = Integer.parseInt(command[4]) - 1;
                
                int p1 = parent[r1*50+c1];
                int p2 = parent[r2*50+c2];
                
                if (p1 != p2) {
                    // (r1,c1)에 값이 있으면 우선 사용, 없으면 (r2,c2) 값 사용
                    String mergeVal = (value[p1] != null) ? value[p1] : value[p2];
                    
                    // p2 그룹에 속했던 모든 셀의 parent를 p1으로 변경
                    for(int i = 0; i < 2500; i++){
                        if(parent[i] == p2){
                            parent[i] = p1;
                        }
                    }
                    value[p1] = mergeVal;
                    value[p2] = null;
                }
                
            } else if(op.equals("UNMERGE")){
                // (r,c)가 병합 전 값으로 돌아가야 함 
                int r = Integer.parseInt(command[1]) - 1;
                int c = Integer.parseInt(command[2]) - 1;
                int targetIdx = r * 50 + c;
                int p = parent[targetIdx];
                String temp = value[p];
                
                // 같은 그룹이었던 모든 셀의 parent를 자기 자신으로 풀고 값 초기화
                for(int i = 0; i < 2500; i++){
                    if(parent[i] == p){
                        parent[i] = i;
                        value[i] = null;
                    }
                }
                
                // 지정된 (r, c) 위치만 기존 값 복원
                value[targetIdx] = temp; 
                
            } else { // PRINT
                int r = Integer.parseInt(command[1]) - 1;
                int c = Integer.parseInt(command[2]) - 1;
                
                if (value[parent[r*50+c]] == null){
                    answerList.add("EMPTY");
                } else {
                    answerList.add(value[parent[r*50+c]]);
                }
            }
        }
        
        String[] answer = new String[answerList.size()];
        for(int i = 0; i < answerList.size(); i++){
            answer[i] = answerList.get(i);
        }
        return answer;
    }
}
