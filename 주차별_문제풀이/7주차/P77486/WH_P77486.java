import java.util.HashMap;

class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        
        HashMap<String, String> childToParent=new HashMap<>();
        HashMap<String, Integer> money=new HashMap<>();

        // 직원 정보 저장
        for (int i=0;i<enroll.length;i++){
            childToParent.put(enroll[i], referral[i]);
            money.put(enroll[i], 0);
        }
      
        // 수익 분배
        for(int i=0;i<seller.length;i++){
            String employee = seller[i];
            int currentAmount = amount[i]*100;
            
            while(true){
                int parentMoney=currentAmount/10;
                int currentMoney=currentAmount-parentMoney; // amount/10이 0이면 그대로임
                money.put(employee, money.get(employee)+currentMoney);
                
                if (parentMoney == 0) {
                    break;
                }                

                // 부모가 "-"라면 종료
                if (childToParent.get(employee).equals("-")) {
                    //money.put(employee, money.get(employee)+parentMoney);
                    break;
                }
                
                //employee 갱신 후 위로 올라가기 
                currentAmount = parentMoney;
                employee = childToParent.get(employee);
               
            }
        }
        int[] answer = new int[enroll.length];

        for (int i = 0; i < enroll.length; i++) {
            answer[i] = money.get(enroll[i]);
        }

        return answer;
    }
}
