import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class MY_P118669 {
	
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        List<int[]> [] graph = new ArrayList[n + 1];
        int[] intensity = new int[n + 1];
        Set<Integer> summitSet = new HashSet<>();
        
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
            intensity[i] = Integer.MAX_VALUE;
        }
        
        for (int i = 0; i < summits.length; i++) {
            summitSet.add(summits[i]);
        }
        
        for (int[] path: paths) {
            int i = path[0];
            int j = path[1];
            int cost = path[2];
            
            graph[i].add(new int[] {j, cost});
            graph[j].add(new int[] {i, cost});
        }
        
        int[] answer = {-1, Integer.MAX_VALUE};
        
        Queue<int[]> pq = new PriorityQueue<>(
            Comparator.comparingInt(a -> a[1])
        );
        
        for (int gate: gates) {
            // 모든 게이트에 대해 intensity 0으로 만들고
            // 큐에 동시에 추가하기
            intensity[gate] = 0;
            pq.offer(new int[] {gate, 0});
        }
        
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int start = cur[0];
            int cost = cur[1];
            
            if (cost > intensity[start]) continue;
            
            for (int[] next: graph[start]) {
                int nextNode = next[0];
                int edgeCost = next[1];
                
                if (edgeCost == 0) continue;
                
                int curIntensity = Math.max(cost, edgeCost);
                if (curIntensity >= intensity[nextNode]) continue;
                
                intensity[nextNode] = curIntensity;
                
                if (summitSet.contains(nextNode)) {
                    if (answer[0] == -1 || curIntensity < answer[1]
                       || (curIntensity == answer[1] && nextNode < answer[0])) {
                        answer[0] = nextNode;
                        answer[1] = curIntensity;
                    }
                    continue;
                }
                pq.offer(new int[] {nextNode, curIntensity});
            }
        }
        
        return answer;
    }

}
