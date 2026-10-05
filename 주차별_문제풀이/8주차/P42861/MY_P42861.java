import java.util.*;

public class MY_P42861 {
    static int[] parent;
    public static int solution(int n, int[][] costs) {
        // 비용을 오름차순으로 정렬
        Arrays.sort(costs, Comparator.comparingInt(c -> c[2]));
        
        // union - find 준비
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        // 간선 수만큼 계산?
        // 근데 다 연결된 걸 확인했으면 안해도 될 것 같긴한뎅..
        // 그럼 연결이 다 될 때 멈추도록 하기?
        // 아 근데 이미 포함된 정점인지 생각하긴해야하네
        int cnt = 0;
        int answer = 0;
        
        for (int i = 0; i < costs.length; i++) {
            int[] cost = costs[i];
            int v1 = cost[0];
            int v2 = cost[1];
            int weight = cost[2];
            
            if (find(v1) != find(v2)) {
                union(v1, v2);
                cnt++;
                answer += weight;
            }
            
            // 간선이 정점의 1개보다 작을 때 다 연결됨
            if (cnt == n - 1) {
                break;
            }
        }
        
        
        return answer;
    }
    
    static int find(int num) {
        if (parent[num] == num) return num;
        return parent[num] = find(parent[num]);
    }
    
    static void union (int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            parent[rootB] = rootA;
        } 
    }

	public static void main(String[] args) {
		solution(4, new int[][] {{0,1,1}, {0,2,2},{1,2,5}, {1,3,1}, {2,3,8}});
	}
}
