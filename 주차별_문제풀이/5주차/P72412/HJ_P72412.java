import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

class Solution {
	
	public int[] solution(String[] info, String[] query) {
    	
        // info?—?„œ ì§??›? ? •ë³´ë?? ?•˜?‚˜?”© ë°›ì•„?˜¬ ?•Œ language, domain, career, foodë¡? ê°??Š¥?•œ ì¡°í•© ? „ë¶?ë¥? keyë¡? ë§Œë“ ?‹¤.
		// ?•´?‹¹ key ?†?„±?“¤?„ ê°?ì§? ì§??›??˜ ì½”ë”©?…Œ?Š¤?Š¸ ? ?ˆ˜?“¤?„ ë°°ì—´?— ????¥?•œ?‹¤.
		// keyë¡? ë§Œë“¤ ?•Œ info?— ?“¤?–´?˜¤?Š” ê²ƒë“¤ë§? ê°?ì§?ê³? String?¸ keyë¥? ë§Œë“¤ë©? ?•ˆ?œ?‹¤.
		// query?—?„œ ?•´?‹¹ ?†?„±?´ '-'ë¡? ?“¤?–´?˜¬ ?ˆ˜ ?ˆê¸? ?•Œë¬¸ì—, ?•´?‹¹ ì¼??´?Š¤?„ ?¬?•¨?•´?„œ keyë¥? ë§Œë“¤?–´?•¼ ?•œ?‹¤.
		
		Map<String, List<Integer>> applicants = new HashMap<>();
		int[] answer = new int[query.length];
		
		for (String indivInfo : info) {
			
			StringTokenizer st = new StringTokenizer(indivInfo);
			
			String language = st.nextToken();
			String domain = st.nextToken();
			String career = st.nextToken();
			String food = st.nextToken();
			int score = Integer.parseInt(st.nextToken());
			
		    String[] conditions = {language, domain, career, food};

		    makeKeys(conditions, 0, "", score, applicants);
			
		}
		
		// ì§??›??˜ ? ?ˆ˜ listë¥? ? •? ¬
		for (List<Integer> scores : applicants.values()) {
			Collections.sort(scores);
		}
		
		// query?—?„œ ?›?•˜?Š” ì¡°ê±´ ë°›ì•„????„œ ????¥
		for (int i = 0; i < query.length; i++) {
			
			String indivQuery = query[i];
			
			StringTokenizer st = new StringTokenizer(indivQuery);
			
			String language = st.nextToken();
			st.nextToken();
			String domain = st.nextToken();
			st.nextToken();
			String career = st.nextToken();
			st.nextToken();
			String food = st.nextToken();
			int score = Integer.parseInt(st.nextToken());
			
			String key = language + " " + domain + " " + career + " " + food;
			
			List<Integer> scores = applicants.get(key);
			
			if (scores == null) {
				answer[i] = 0;
				continue;
			}
			
			int low = 0;
			int high = scores.size();
			
			while (low < high) {
				int mid = (low + high) / 2;
				if (scores.get(mid) >= score) {
					high = mid;
				} else {
					low = mid + 1;
				}
			}
			
			answer[i] = scores.size() - low;
		}

        return answer;
    
    }
	
	// info?—?„œ ê°?? ¸?˜¬ ?•Œ ì§??›? ? •ë³´ë?? String?œ¼ë¡? ê°?? ¸?˜¬ ?•Œ query?—?„œ?Š” ?•´?‹¹ ì¡°ê±´?„ ?Š¹? • ì§“ì?? ?•Š?Š” ê²½ìš°ê°? ë°œìƒ?•  ?ˆ˜ ?ˆ?Œ
	// ?• ì´ˆì— info?—?„œ 
	private void makeKeys(String[] conditions, int depth, String key, int score, Map<String, List<Integer>> applicants) {
		
		// query?—?„œ ì°¾ì„ ?•Œ 4ê°?ì§? ì¡°ê±´?„ ëª¨ë‘ ?„ ?ƒ?•œ ê²½ìš°
		if (depth == 4) {
			if (!applicants.containsKey(key)) {
				applicants.put(key, new ArrayList<>());
			}
			
			applicants.get(key).add(score);
			return;
		}
		
		// query?—?„œ ì°¾ì„ ?•Œ 4ê°?ì§? ì¡°ê±´ ì¤? -?´ ?¬?•¨?œ ê²½ìš°
		// case 1. query?—?„œ ?‹¤? œ ì¡°ê±´?„ ?‚¬?š©?•  ê²½ìš°
	    String actualKey;

	    if (key.isEmpty()) {
	        actualKey = conditions[depth];
	    } else {
	        actualKey = key + " " + conditions[depth];
	    }

	    makeKeys(conditions, depth + 1, actualKey, score, applicants);

	    //case 2. query?—?„œ ?•´?‹¹ ì¡°ê±´?„ "-"ë¡? ?‚¬?š©?•˜?Š” ê²½ìš°
	    String wildcardKey;

	    if (key.isEmpty()) {
	        wildcardKey = "-";
	    } else {
	        wildcardKey = key + " -";
	    }

	    makeKeys(conditions, depth + 1, wildcardKey, score, applicants);
		
	}

}