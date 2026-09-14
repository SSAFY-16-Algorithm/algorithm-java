class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        String answer = "";
        int N=logs.length;
        int[] startTimes=new int[N];
        int[] endTimes=new int[N];
        
        int min_line=Integer.MAX_VALUE;
        int max_line=0;
        
        //초 변환
        String[] play=play_time.split(":");
        int playsec=Integer.parseInt(play[0]) * 3600 + Integer.parseInt(play[1]) * 60 + Integer.parseInt(play[2]);
        String[] adv=adv_time.split(":");
        int advsec=Integer.parseInt(adv[0]) * 3600 + Integer.parseInt(adv[1]) * 60 + Integer.parseInt(adv[2]);
        
        for(int i=0;i<N;i++){
            String[] times = logs[i].split("-");
            String[] start = times[0].split(":");
            String[] end=times[1].split(":");

            int startsec=Integer.parseInt(start[0]) * 3600 + Integer.parseInt(start[1]) * 60 + Integer.parseInt(start[2]);
            int endsec=Integer.parseInt(end[0]) * 3600 + Integer.parseInt(end[1]) * 60 + Integer.parseInt(end[2]);
            startTimes[i]=startsec;
            endTimes[i]=endsec;
            
            if(min_line>startsec) min_line=startsec;
            if(max_line<endsec) max_line=endsec;
        }
        
        //보고 있는 시청자 매핑 
        int[] totalTimeLine=new int[playsec];
        for (int i=0;i<N;i++){
            int s=startTimes[i];
            int e=endTimes[i];
            for(int j=s;j<s+e;j++){
                totalTimeLine[j]+=1;
            }
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
        int max_left=0;
        
        while(right<max_line){
            current_sum-=totalTimeLine[left];
            left++;
            right++;
            current_sum+=totalTimeLine[right];
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
}

//시간초과..
