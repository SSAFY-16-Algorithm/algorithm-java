import java.util.Collections;
import java.util.ArrayList;

class Edge implements Comparable<Edge>{
	int from;
	int to;
	int cost;
	Edge(int from, int to, int cost){
		this.from=from;
		this.to=to;
		this.cost=cost;
	}
	
	@Override
	public int compareTo(Edge other) {
		// 가중치 오름차순 정렬
		return this.cost - other.cost;
	}
}

class Solution {
    static int[] parent;
    
    static int find(int x) {
		if(parent[x]==x) {
			return x;
		}
		return parent[x]=find(parent[x]); //계속 부모를 따라가야 해서 느려질 수 있으므로 찾는 동시에 바로 대표자로 갈 수 있게 해줌
	}
	
	static boolean union(int a, int b) {
		int rootA=find(a);
		int rootB=find(b);
		
		//대표자가 같다면 사이클이 발생하므로 간선 무효화
		if(rootA==rootB) {
			return false;
		}
		
		parent[rootB]=rootA;
		return true;
	}
    
    public int solution(int n, int[][] costs) {
        // 간선 추가 
        ArrayList<Edge> edges= new ArrayList<>();
        for(int i=0;i<costs.length;i++){
            edges.add(new Edge(costs[i][0], costs[i][1], costs[i][2]));
        }
        
        Collections.sort(edges);
        
        parent=new int[n];
        for(int i=0;i<n;i++) {
				parent[i]=i;
        }	
        
        int answer = 0;
        int edgeCount=0;
        for(Edge edge: edges){
            if (edgeCount==n) break;
            if(union(edge.from, edge.to)){
                answer+=edge.cost;
                edgeCount++;
            }
            
        }
        return answer;
    }
}
