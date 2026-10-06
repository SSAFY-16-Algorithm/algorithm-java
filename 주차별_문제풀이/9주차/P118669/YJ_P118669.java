import java.util.*;

public class YJ_P118669 {
    public static void main(String[] args) {
        int n = 6;
        int[][] paths = {{1, 2, 3},{2, 3, 5},{2, 4, 2},{2, 5, 4},{3, 4, 4},{4, 5, 3},{4, 6, 1},{5, 6, 1}};
        int[] gates = {1,3};
        int[] summits = {5};
        int[] result = solution(n, paths, gates, summits);
        for(int i=0; i<result.length; i++) {
            System.out.println(result[i]);
        }
    }
    
    static class Node implements Comparable<Node>{
        int to, weight;

        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight); // intensity 작은 순으로 꺼낸다. 
        }
    }
    
    static boolean[] isGate;
    static boolean[] isSummit;
    static List<List<Node>> graph;
    static int[] intensity;
    static int minSummit = -1;
    static int minIntensity = Integer.MAX_VALUE;
    
    public static int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        
        // 각 출발점에서 각 봉우리까지의 경로에 대해 모두 탐색 -> intensity 저장 (돌아오는 길은 어차피 똑같아서 고려할 필요x)
        
        // -> 문제 : 가는 길에 다른 봉우리나 출발점이 있으면 안된다.. 
        
        // -> 저장할 때 봉우리, intensity로 저장하고 더 작은 intensity가 나오면 update 한다. 
        
        isGate = new boolean[n+1];
        isSummit = new boolean[n+1];
        
        for(int gate:gates) {
            isGate[gate] = true;
        }
        
        for(int summit: summits) {
            isSummit[summit] = true;
        }
        
        graph = new ArrayList<>();
        for(int i=0; i<=n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for(int[] path: paths) {
            int u = path[0];
            int v = path[1];
            int w = path[2];
            
            // u가 출발점이거나 v가 봉우리면 u->v단방향만 의미가 있다. 
            if(isGate[u] || isSummit[v]) {
                graph.get(u).add(new Node(v,w));
            } else if(isGate[v] || isSummit[u]) {
                graph.get(v).add(new Node(u, w));
            }else {
                graph.get(u).add(new Node(v,w));
                graph.get(v).add(new Node(u,w));
            }
        }
        
        dijkstra(n, gates);
        findMinIntensityAndSummit(summits);
        
        // result[0]엔 산봉우리 노드 번호, result[1]에는 intensity 최소값
        int[] answer = new int[2];
        answer[0] = minSummit;
        answer[1] = minIntensity;
        return answer;
    }
    
    public static void dijkstra(int n, int[] gates) {
        intensity = new int[n+1]; //intensity가 최소가 돼야 한다. 
        Arrays.fill(intensity, Integer.MAX_VALUE);
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        // 출발 지점을 모두 pq에 넣는다. 
        for(int gate: gates) {
            intensity[gate] = 0;
            pq.offer(new Node(gate, 0)); // 각 출발지에서의 intensity는 0
        }
        
        while(!pq.isEmpty()) {
            Node current = pq.poll();
            
            // 현재 경로가 가려는 지점의 intensity 보다 크면 패스
            if(current.weight > intensity[current.to]) {
                continue;
            }
            
            // 봉우리면 패스
            if(isSummit[current.to]) {
                continue;
            }
            
            for(Node next: graph.get(current.to)) {
                
                // 경로 중 간선의 가중치가 가장 큰 값이 intensity다.
                int nextIntensity = Math.max(current.weight, next.weight);
                
                if(intensity[next.to] > nextIntensity) {
                    intensity[next.to] = nextIntensity;
                    pq.offer(new Node(next.to, nextIntensity));
                }
            }
        }
    }
    
    public static void findMinIntensityAndSummit(int[] summits) {
        Arrays.sort(summits);
        
        for(int summit: summits) {
            if(intensity[summit] <minIntensity) {
                minSummit = summit;
                minIntensity = intensity[summit];
            }
        }
    }
}
