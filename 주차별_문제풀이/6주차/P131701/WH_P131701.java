import java.util.*;
class Solution {
    public int solution(int[] elements) {
        int answer = 0;

        ArrayList<Integer> element_list=new ArrayList<>();
        for (int i=0;i<elements.length;i++){
            element_list.add(elements[i]);
        }
        for (int i=0;i<elements.length;i++){
            element_list.add(elements[i]);
        }
        
        TreeSet<Integer> sum_list=new TreeSet<>();
        
        ArrayList<Integer> selected_list;
        for(int size=0;size<elements.length;size++){
            for (int i=0;i<elements.length;i++){
            	selected_list=new ArrayList<>();
                for(int j=i;j<=i+size;j++){
                	//System.out.println(element_list.get(j));
                    selected_list.add(element_list.get(j));
                }
                int sum = selected_list.stream()
                        .mapToInt(Integer::intValue)
                        .sum();
                sum_list.add(sum);
            }
        }

        return sum_list.size();
    }
}
