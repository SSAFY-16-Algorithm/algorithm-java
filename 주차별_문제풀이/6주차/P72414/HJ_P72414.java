class Solution {

    public String solution(String play_time, String adv_time, String[] logs) {

        int playDuration = toSeconds(play_time);
        int adDuration = toSeconds(adv_time);

        // 각 초마다 시청자 수를 계산하기 위한 배열
        long[] viewerCount = new long[playDuration + 1];

        // 1. 각 시청 로그를 차분 배열에 기록
        for (String log : logs) {
            String[] times = log.split("-");

            int start = toSeconds(times[0]);
            int end = toSeconds(times[1]);

            viewerCount[start]++;
            viewerCount[end]--;
        }

        // 2. 누적합을 통해 각 초의 실제 시청자 수 계산
        for (int second = 1; second <= playDuration; second++) {
            viewerCount[second] += viewerCount[second - 1];
        }

        // 3. 광고가 0초에 시작하는 경우의 누적 시청시간 계산
        long currentWatchTime = 0;

        for (int second = 0; second < adDuration; second++) {
            currentWatchTime += viewerCount[second];
        }

        long maxWatchTime = currentWatchTime;
        int bestStartTime = 0;

        // 4. 광고 시작 시간을 1초씩 이동시키며 최대 누적 시청시간 탐색
        for (int start = 1; start + adDuration <= playDuration; start++) {

            // 이전 광고 구간에서 빠지는 1초
            currentWatchTime -= viewerCount[start - 1];

            // 새 광고 구간에 들어오는 1초
            currentWatchTime += viewerCount[start + adDuration - 1];

            // 동일한 경우에는 더 빠른 시각을 유지해야 하므로 > 사용
            if (currentWatchTime > maxWatchTime) {
                maxWatchTime = currentWatchTime;
                bestStartTime = start;
            }
        }

        return toTimeString(bestStartTime);
    }

    // HH:MM:SS -> 초
    private static int toSeconds(String time) {

        String[] parts = time.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        return hours * 3600 + minutes * 60 + seconds;
    }

    // 초 -> HH:MM:SS
    private static String toTimeString(int seconds) {

        int hours = seconds / 3600;
        seconds %= 3600;

        int minutes = seconds / 60;
        int remainSeconds = seconds % 60;

        return String.format(
                "%02d:%02d:%02d",
                hours,
                minutes,
                remainSeconds
        );
    }
}