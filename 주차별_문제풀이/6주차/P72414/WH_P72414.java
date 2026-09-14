class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        String answer = "";
        int N=logs.length;
        int[] startTimes=new int[N];
        int[] endTimes=new int[N];
        
        //초 변환
        int playsec=toSec(play_time);
        int advsec=toSec(adv_time);
        
        //보고 있는 시청자 매핑 
        long[] totalTimeLine = new long[360001];
        
        int min_line=Integer.MAX_VALUE;
        int max_line=0;
        
        //이모스 알고리즘 사용
        for (String log : logs) {
            String[] times = log.split("-");
            int start = toSec(times[0]);
            int end = toSec(times[1]);
            totalTimeLine[start]++;
            totalTimeLine[end]--;
            if(min_line>start) min_line=start;
            if(max_line<end) max_line=end;
        }
        
        for (int i = 1; i <= playsec; i++) {
            totalTimeLine[i] += totalTimeLine[i - 1];
        }
        
        //min_line에서 (max_line+advsec 또는 playsec)까지 슬라이딩 윈도우
        //윈도우 크기: advsec 
        int left=min_line;
        int right=min_line+advsec;
        
        int current_sum=0;
        for(int i=left;i<right;i++){
            current_sum+=totalTimeLine[i];
        }
        
        int max_sum=current_sum;
        int max_left=left;
        if(playsec == advsec){
            return "00:00:00";
        }
        
        while(right<playsec){            
            current_sum-=totalTimeLine[left];
            left++;
            
            current_sum+=totalTimeLine[right];
            right++;
            
            if (current_sum>max_sum) {
                max_sum=current_sum;
                max_left=left;
            }
        }
        
        //max_left=(int)1000
        int h=max_left/3600;
        max_left-=(3600*h);
        int m=max_left/60;
        int s=max_left-(60*m);
        
        answer=String.format("%02d:%02d:%02d", h, m, s);
        return answer;
    }
    
    // 시간 변환 헬퍼 메서드
    private int toSec(String time) {
        String[] t = time.split(":");
        return Integer.parseInt(t[0]) * 3600 + Integer.parseInt(t[1]) * 60 + Integer.parseInt(t[2]);
    }
}
