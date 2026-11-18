import java.util.*;

class Solution {

    String[] user_id;
    String[] banned_id;

    boolean[] visited;
    Set<Set<String>> result;

    public int solution(String[] user_id, String[] banned_id) {

        this.user_id = user_id;
        this.banned_id = banned_id;

        // 사용자 선택 여부와 최종 제재 사용자 집합 저장
        visited = new boolean[user_id.length];
        result = new HashSet<>();

        // 첫 번째 불량 사용자 패턴부터 탐색 시작
        dfs(0);

        return result.size();
    }

    // depth: 현재 검사할 banned_id의 인덱스
    private void dfs(int depth) {

        // 모든 불량 사용자 패턴에 사용자 배정 완료
        if (depth == banned_id.length) {

            // 현재 선택된 사용자들을 하나의 집합으로 생성
            Set<String> selected = new HashSet<>();

            for (int i = 0; i < user_id.length; i++) {
                if (visited[i]) {
                    selected.add(user_id[i]);
                }
            }

            // 동일한 사용자 집합은 중복 저장되지 않음
            result.add(selected);
            return;
        }

        // 현재 불량 사용자 패턴과 매칭되는 사용자 탐색
        for (int i = 0; i < user_id.length; i++) {

            // 이미 선택한 사용자라면 건너뛰기
            if (visited[i]) {
                continue;
            }

            // 현재 패턴과 일치하지 않으면 건너뛰기
            if (!isMatch(user_id[i], banned_id[depth])) {
                continue;
            }

            // 1. 현재 사용자 선택
            visited[i] = true;

            // 2. 다음 불량 사용자 패턴 탐색 (DFS)
            dfs(depth + 1);

            // 3. 선택 취소 및 상태 복원 (백트래킹)
            visited[i] = false;
        }
    }

    // 사용자 ID와 불량 사용자 패턴의 일치 여부 확인
    private boolean isMatch(String user, String banned) {

        // 길이가 다르면 매칭 불가능
        if (user.length() != banned.length()) {
            return false;
        }

        for (int i = 0; i < user.length(); i++) {

            // '*'는 모든 문자와 매칭 가능
            if (banned.charAt(i) == '*') {
                continue;
            }

            // 일반 문자가 일치하지 않으면 매칭 실패
            if (user.charAt(i) != banned.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}