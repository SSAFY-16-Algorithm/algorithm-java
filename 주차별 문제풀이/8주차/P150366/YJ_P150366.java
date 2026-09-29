import java.util.*;

public class YJ_P150366 {
    
	static String[] table = new String[2501]; // 1차원으로 2차원 배열을 관리한다.
    static int[] parent = new int[2501];
	static List<String> list;
    
    public static void main(String[] args) {
        String[] commands1 = {"UPDATE 1 1 menu", "UPDATE 1 2 category", "UPDATE 2 1 bibimbap", "UPDATE 2 2 korean", "UPDATE 2 3 rice", "UPDATE 3 1 ramyeon", "UPDATE 3 2 korean", "UPDATE 3 3 noodle", "UPDATE 3 4 instant", "UPDATE 4 1 pasta", "UPDATE 4 2 italian", "UPDATE 4 3 noodle", "MERGE 1 2 1 3", "MERGE 1 3 1 4", "UPDATE korean hansik", "UPDATE 1 3 group", "UNMERGE 1 4", "PRINT 1 3", "PRINT 1 4"};
        solution(commands1);
    }
    
    public static String[] solution(String[] commands) {
        
        list = new ArrayList<>();
        
        for(int i=1; i<=2500; i++) {
        	parent[i] = i;
        }
        
        
        for(String command : commands) {
            String[] tokens = command.split(" ");
            String commandType = tokens[0];
            if(commandType.equals("UPDATE")) {
                // 두 종류 분기 처리 필요
                // 다음 값이 int 인지 string인지 모를 땐 어떻게 해야 되나. 
                if(tokens.length == 4) {
                	int r = Integer.parseInt(tokens[1]);
                	int c = Integer.parseInt(tokens[2]);
                	String value = tokens[3];
                	updateCell(r, c, value);
                }
                else {
                	String value1 = tokens[1];
                	String value2 = tokens[2];
                	updateValue(value1, value2);
                }
            }else if(commandType.equals("MERGE")) {
                int r1 = Integer.parseInt(tokens[1]);
                int c1 = Integer.parseInt(tokens[2]);
                int r2 = Integer.parseInt(tokens[3]);
                int c2 = Integer.parseInt(tokens[4]);
                merge(r1, c1, r2, c2);
            }else if(commandType.equals("UNMERGE")) {
                int r = Integer.parseInt(tokens[1]);
                int c = Integer.parseInt(tokens[2]);
                unmerge(r, c);
            }else if(commandType.equals("PRINT")) {
                int r = Integer.parseInt(tokens[1]);
                int c = Integer.parseInt(tokens[2]);
                print(r, c);
            }
        }
        // list -> arr 로 바꿔서 return 하기
        String[] answer = list.toArray(new String[list.size()]);
        return answer;
    }
    
    // 2차원 -> 1차원으로 바꾸는 메소드
    public static int toIndex(int r, int c) {
    	return (r-1)*50 + c;
    }
    
    public static int find(int x) {
    	if(parent[x] == x) return x;
    	
    	return parent[x] = find(parent[x]);
    }
    
    
    // (r, c)의 값을 value로 바꿈
    public static void updateCell(int r, int c, String value) {
        int root = find(toIndex(r, c));
        table[root] = value; // 대표 값을 바꾸기
    }
    
    // value1 값을 가진 셀의 값을 전부 value2로 바꿈
    public static void updateValue(String value1, String value2) {
        // command 최대 천 개라 배열 다 돌아도 시간초과 안날듯. 
    	for(int i=1; i<=2500; i++) {
        	if(value1.equals(table[i])) {
        		table[i] = value2;
        	}
        }
    }
    
    // (r1, c1)과 (r2, c2) 병합하기 -> union 역할
    public static void merge(int r1, int c1, int r2, int c2) {
        int rootA = find(toIndex(r1, c1));
        int rootB = find(toIndex(r2, c2));
        
        if(rootA == rootB) return;
        
        // rootA의 값이 먼저
        String value = "";
        if(table[rootA] != null) {
        	value = table[rootA];
        } else {
        	value = table[rootB];
        }
        
        // 유니온
        parent[rootB] = rootA;
        table[rootA] = value;
        table[rootB] = null; 
    }
    
    //(r, c)에 값이 있다면 그 위치에만 남기고 병합된 모든 셀 해제하기. 
    public static void unmerge(int r, int c) {
        int target = toIndex(r, c);
        int root = find(target);
        String value = table[root]; // 원본값 남겨야 한다. 
        
        List deleteList = new ArrayList<>();
        for(int i=1; i<=2500; i++) {
        	if(find(i) == root) {
        		deleteList.add(i);
        	}
        }
        
        // unmerge하고 초기화
        for(int node: deleteList) {
        	parent[node] = node; 
        	table[node] = null; 
        }
        // (r, c)만 이전 값 복원
        table[target] = value; 
    }
    
    public static void print(int r, int c) {
    	int root = find(toIndex(r, c));
    	String value = table[root];
        // list에  추가
        list.add(value == null ? "EMPTY" :value);
    }
}