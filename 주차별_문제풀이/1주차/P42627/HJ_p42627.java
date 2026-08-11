import java.util.Arrays;
import java.util.PriorityQueue;

// 작업 정보를 저장하는 클래스
class Job{
	int number;
	int requestTime;
	int duration;
	
	Job(int number, int requestTime, int duration){
		this.number = number;
		this.requestTime = requestTime;
		this.duration = duration;
	}
}

class Solution {
	public int solution(int[][] jobs) {
		
		// 입력받은 jobs 목록 기준으로 작업 번호를 설정한 jobList 생성
		int n = jobs.length;
		Job[] jobList = new Job[n];
		
		for (int i = 0; i < n; i++) {
			jobList[i] = new Job(i, jobs[i][0], jobs[i][1]);
		}
		
		// 큐에 넣지 않은 다음 작업을 확인하기 위해 작업 목록을 요청 시각순으로 정렬
		Arrays.sort(jobList, (a, b) -> {
			if (a.requestTime != b.requestTime) {
				return Integer.compare(a.requestTime, b.requestTime);
			}
			
			return Integer.compare(a.requestTime, b.requestTime);
		});
		
		// 대기 큐를 만들기
		// 1. 짧은 소요시간 2. 빠른 요청시각, 3. 작은 작업번호 순으로 우선순위큐를 생성
		PriorityQueue<Job> waitingQueue = new PriorityQueue<>((a, b)->{
			if (a.duration != b.duration) {
				return Integer.compare(a.duration, b.duration);
			}
			
			if (a.requestTime != b.requestTime) {
				return Integer.compare(a.requestTime, b.requestTime);
			}
			
			return Integer.compare(a.number, b.number);
		});
		
		// 상태변수를 초기화하기
		int currentTime = 0;
		int nextJobIndex = 0;
		int completedCount = 0;
		long totalTurnaroundTime = 0;
		
		// while 문을 통해 작업을 하나씩 처리하기
		// 대기중인 작업이 없는 상태면 가장 빠른 다음 요청 시각으로 이동하기
		while (completedCount < n) {
			if (waitingQueue.isEmpty() && nextJobIndex < n && currentTime < jobList[nextJobIndex].requestTime) {
				currentTime = jobList[nextJobIndex].requestTime;
			}
			
			// 현재 시각까지 요청된 작업 모두 대기큐 추가하기
			while (nextJobIndex < n && jobList[nextJobIndex].requestTime <= currentTime) {
				waitingQueue.offer(jobList[nextJobIndex]);
				nextJobIndex++;
			}
			
			// 우선순위가 높은 작업 수행하기
			Job currentJob = waitingQueue.poll();
			
			// 현재 시각 업데이트하기 && Total 시간 현재시각과 요청시각 비교로 계산 && 작업완료카운트 늘리기
			currentTime += currentJob.duration;
			totalTurnaroundTime += currentTime - currentJob.requestTime;
			completedCount++;
		}
		int answer = (int) (totalTurnaroundTime / n);
		
        return answer;
    }
}
