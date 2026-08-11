import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

	// 노래의 고유 번호와 재생 횟수를 함께 저장하는 클래스
    static class Song {
        int id;
        int play;

        Song(int id, int play) {
            this.id = id;
            this.play = play;
        }
    }

    public int[] solution(String[] genres, int[] plays) {

        // 1. 장르별 전체 재생 횟수
        Map<String, Integer> genreTotal = new HashMap<>();

        // 2. 장르별 노래 목록
        Map<String, List<Song>> genreSongs = new HashMap<>();

        // 모든 노래를 한 번씩 확인
        for (int i = 0; i < genres.length; i++) {

            String genre = genres[i];
            int play = plays[i];

            // 장르별 전체 재생 횟수 누적
            genreTotal.put(
                genre,
                genreTotal.getOrDefault(genre, 0) + play
            );

            // 처음 등장한 장르라면 빈 리스트 생성
            if (!genreSongs.containsKey(genre)) {
                genreSongs.put(genre, new ArrayList<>());
            }

            // 해당 장르의 노래 목록에 현재 노래 추가
            genreSongs.get(genre).add(new Song(i, play));
        }

        // 3. 장르 이름을 리스트로 가져오기
        List<String> genreOrder = new ArrayList<>(genreTotal.keySet());

        // 장르 총 재생 횟수를 기준으로 내림차순 정렬
        genreOrder.sort((genre1, genre2) ->
            Integer.compare(
                genreTotal.get(genre2),
                genreTotal.get(genre1)
            )
        );

        // 최종적으로 선택된 노래 번호를 저장
        List<Integer> answerList = new ArrayList<>();

        // 4. 재생 횟수가 높은 장르부터 확인
        for (String genre : genreOrder) {

            List<Song> songs = genreSongs.get(genre);

            // 장르 내부의 노래 정렬
            songs.sort((song1, song2) -> {

                // 재생 횟수가 다르면 재생 횟수 내림차순
                if (song1.play != song2.play) {
                    return Integer.compare(song2.play, song1.play);
                }

                // 재생 횟수가 같으면 고유 번호 오름차순
                return Integer.compare(song1.id, song2.id);
            });

            // 장르에 속한 노래 중 최대 2개 선택
            int selectCount = Math.min(2, songs.size());

            for (int i = 0; i < selectCount; i++) {
                answerList.add(songs.get(i).id);
            }
        }

        // List<Integer>를 int[]로 변환
        int[] answer = new int[answerList.size()];

        for (int i = 0; i < answerList.size(); i++) {
            answer[i] = answerList.get(i);
        }

        return answer;
    }
}