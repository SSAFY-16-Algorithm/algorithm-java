import java.util.*;
import java.io.*;

public class MY_CT6C3P5 {
    static int[][] edges;
    static int n, m;
    static List<Integer>[] graph;
    static int[] inDegree;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        edges = new int[m][2];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges[i] = new int[] {a, b};
        }

        graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        inDegree = new int[n + 1];

        // start == m + 1이면 사이클이 없는 것
        int start = 0, end = m + 1, mid = -1;
        // start == end가 되는 순간 멈춤
        while (start < end) {
            mid = (start + end) / 2;
            if (hasCycle(mid)) {
                // System.out.println("사이클이 있습니다.");
                // mid를 후보에 포함
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        if (start != m + 1) {
            System.out.println(start);
        } else {
            System.out.println("Consistent");
        }
    }

    static boolean hasCycle(int end) {
        for (int i = 1; i <= n; i++) {
            graph[i].clear();
            inDegree[i] = 0;
        }

        for (int i = 0; i < end; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a].add(b);
            inDegree[b]++;
        }


        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) q.offer(i);
        }

        while (!q.isEmpty()) {
            int num = q.poll();
            for (int next: graph[num]) {
                inDegree[next]--;
                if (inDegree[next] == 0) q.offer(next);
            }    
        }

        for (int i = 1; i <= n; i++) {
            if (inDegree[i] > 0) {
                return true;
            }
        }
        return false;
    }
}
