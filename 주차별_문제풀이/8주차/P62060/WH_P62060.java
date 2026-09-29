import java.util.*;

class Solution {
    // 배열 크기만큼 boolean배열 만들어두고 설치 시 true로 바꾸기
    static boolean[][] pillar; 
    static boolean[][] bo;
    int n;
    
    public int[][] solution(int n, int[][] build_frame) {
        this.n=n;
        
        pillar=new boolean[n+1][n+1];
        bo=new boolean[n+1][n+1];
        
        for(int[] command: build_frame){
            int x=command[0];
            int y=command[1];
            int a=command[2];
            int b=command[3];
            
            if(b==1){ //설치
                if(a==0){ //기둥
                    if(pillarKeyi(x,y)){
                        pillar[x][y]=true;
                    }
                }else{ //보
                    if(boKeyi(x,y)){
                        bo[x][y]=true;
                    }
                }
            }else{ //삭제
                if(a==0){
                    pillar[x][y]=false;
                    
                    if(!deleteKeyi()){
                        pillar[x][y]=true;
                    }
                }else{
                    bo[x][y]=false;
                    
                    if(!deleteKeyi()){
                        bo[x][y]=true;
                    }
                }
            }
        }
        
        ArrayList<int[]> result = new ArrayList<>();
        // x 오름차순 > y 오름차순 > 기둥 > 보
        for (int x = 0; x <= n; x++) {
            for (int y = 0; y <= n; y++) {
                if (pillar[x][y]) {
                    result.add(new int[]{x, y, 0});
                }
                
                if (bo[x][y]) {
                    result.add(new int[]{x, y, 1});
                }
            }
        }
        
        int[][] answer = result.toArray(new int[0][]);    
        return answer;
    }
        boolean pillarKeyi(int x, int y){
        // 바닥
        if(y==0){
            return true;
        }
        
        //기둥 위
        if(pillar[x][y-1]){
            return true;
        }        
        
        //왼쪽 끝에 보가 있으면 
        if(bo[x][y]){
            return true;
        }
        
        //오른쪽 끝에 보가 있으면 
        if(x>0 && bo[x-1][y]){
            return true;
        }        
        return false;
    }
    
    boolean boKeyi(int x, int y){
        //왼쪽 끝 아래에 기둥이 있으면
        if(pillar[x][y-1]){
            return true;
        }
        //오른쪽 끝 아래에 기둥이 있으면
        if(pillar[x+1][y-1]){
            return true;
        }
        //양쪽에 보가 있으면
        if(x>0 && bo[x-1][y] && x+1<=n && bo[x+1][y]){
            return true;
        }
        return false;
    }
    
    boolean deleteKeyi(){
        for(int x=0;x<=n;x++){
            for(int y=0;y<=n;y++){
                if(pillar[x][y]){
                    if (!pillarKeyi(x,y)){
                        return false;
                    }
                }
                if(bo[x][y]){
                    if(!boKeyi(x,y)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    

}
