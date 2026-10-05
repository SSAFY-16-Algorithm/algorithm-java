import java.util.HashMap;
import java.util.Map;

public class MY_P77486 {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        Seller[] sellers;
        Map<String, Integer> sellerNameToIdx = new HashMap<>();
        int sellerSize = enroll.length;
        sellers = new Seller[sellerSize];
        Seller center = new Seller("center");
        
        // 이름 등록
        for (int i = 0; i < sellerSize; i++) {
            Seller s = new Seller(enroll[i]);
            sellers[i] = s;
            sellerNameToIdx.put(s.name, i);
        }
        
        // 부모 등록
        for (int i = 0; i < sellerSize; i++) {
            Seller s = sellers[i];
            String parentName = referral[i];
            if (parentName.equals("-")) {
                s.parent = center;
            } else {
                s.parent = sellers[sellerNameToIdx.get(parentName)];
            }
        }
        
        // 판매 전파
        for (int i = 0; i < seller.length; i++) {
            int idx = sellerNameToIdx.get(seller[i]);
            Seller s = sellers[idx];
            int earn = amount[i] * 100;
            while (s.parent != null) {
                int curEarn = (int) Math.ceil(earn * 0.9);
                // 1원 단위로 안 잘리면 그냥 다 가짐
                if (earn * 0.1 < 1.0) {
                    s.amount += earn;
                    break;
                }
                else {
                    s.amount += curEarn;
                }
                
                earn -= curEarn;
                s = s.parent;
            }
        }
        
        int[] answer = new int[sellerSize];
        for (int i = 0; i < sellerSize; i++){
            answer[i] = sellers[i].amount;
        }
        return answer;
    }
    
    class Seller {
        Seller parent;
        String name;
        int amount;
        
        Seller (String name) {
            this.name = name;
        }
    }
}
