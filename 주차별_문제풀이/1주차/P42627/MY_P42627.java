import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Arrays;

public class MY_P42627 {
    public static int solution(int[][] jobs) {
    	int workSize = jobs.length;
    	Work[] works = new Work[workSize];
    	PriorityQueue<Work> pq = new PriorityQueue<>(
			Comparator.comparingInt((Work work) -> work.duration)
				.thenComparingInt(w -> w.reqTime)
				.thenComparingInt(w -> w.num)
		);
    	
    	// 바로 작업 큐에 넣으면
    	// 3ms에 (5ms, 1)과 (0ms, 1)이 있는 상황에
    	// 없어야할 5ms 시작 작업을 꺼내게된다.
    	// pq의 정렬 때문에
    	// 그래서 작업 시간에 맞춰 while문에서 직접 큐에 넣어야 함
    	for (int i = 0; i < workSize; i++) {
    		works[i] = new Work(i, jobs[i][0], jobs[i][1]);
    	}
    	
    	// 요청시간, 작업 번호 순으로 정렬
    	Arrays.sort(works, 
			Comparator.comparingInt((Work work) -> work.reqTime)
				.thenComparingInt(work -> work.num)
		);
    	
    	int now = 0; // 현재 시간
    	int turnaroundTime = 0;
    	int idx = 0;
    	
    	// 모든 작업을 큐에 넣었음에도
    	// 더 이상 할 일이 없을 때 종료
    	while(idx < workSize || !pq.isEmpty()) {
    		// 현재 시간에 요청된 작업 큐에 추가
    		while(idx < workSize && works[idx].reqTime <= now) {
    			pq.add(works[idx]);
    			idx++;
    		}
    		
    		if (pq.isEmpty()) {
    			now++;
    			continue;
    		}
    		
    		Work work = pq.poll();
    		now += work.duration;
    		turnaroundTime += (now - work.reqTime);
    	}
    	
        int answer = (int) turnaroundTime/workSize;
        return answer;
    }

	public static void main(String[] args) {
		System.out.println(solution(new int[][] {{0, 3}, {1, 9}, {3, 5}}));

	}


	public static class Work {
		int num;
		int reqTime;
		int duration;
		Work(int n, int r, int d) {
			this.num = n;
			this.reqTime = r;
			this.duration = d;
		}
	}
}