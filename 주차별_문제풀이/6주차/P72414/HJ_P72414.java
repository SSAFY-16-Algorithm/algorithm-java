class Solution {

	public String solution(String play_time, String adv_time, String[] logs) {

		// play_time : 배열의 최댓값
		int playSec = toSec(play_time);
		int advSec = toSec(adv_time);

		// 전체 시청 시각 담은 일차원 배열
		long[] viewers = new long[playSec + 1];

		// 각 로그를 차분 배열에 기록
		for (String log : logs) {
			String[] times = log.split("-");
			int startTime = toSec(times[0]);
			int endTime = toSec(times[1]);

			viewers[startTime]++;
			viewers[endTime]--;
		}

		// 각 초마다 실제 시청자 수를 계산하기 위해 누적합 진행
		for (int i = 1; i <= playSec; i++) {
			viewers[i] += viewers[i - 1];
		}
		
		// 슬라이딩 윈도우 적용 전 초기 광고 시청시간 
		int left = 0;
		int right = advSec;
		long currentSum = 0;
		
		for(int i = left; i < right; i++) {
			currentSum += viewers[i];
		}
		
		// 슬라이딩 윈도우를 적용해서 최대 광고 시청 시간이 나올 때마다 광고시작시각 업데이트
		long maxSum = currentSum;
		int maxLeft = 0;
		
		while (right < playSec) {
			currentSum -= viewers[left];
			left++;
			
			currentSum += viewers[right];
			right++;
			
			if (currentSum > maxSum) {
				maxSum = currentSum;
				maxLeft = left;
			}
		}

		String answer = toTime(maxLeft);
		return answer;

	}

	// HH:MM:SS을 HH, MM, SS로 분할하고 정수형으로 바꿔서 초 계산
	// 최대 99:99:99를 초로 바꿔도 약 36만 밖에 되지 않음
	static int toSec(String time) {

		String[] t = time.split(":");
		int HH = Integer.parseInt(t[0]);
		int MM = Integer.parseInt(t[1]);
		int SS = Integer.parseInt(t[2]);
		int PlayTime = HH * 3600 + MM * 60 + SS;

		return PlayTime;

	}
	
	static String toTime(int time) {
		
		int HH = time / 3600;
		time -= (HH*3600);
		int MM = time / 60;
		time -= (MM*60);
		int SS = time;
		
		String result = String.format("%02d:%02d:%02d", HH, MM, SS);
		
		return result;
	}

}