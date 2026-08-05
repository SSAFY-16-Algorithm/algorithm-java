import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class MY_P42579 {
	static HashMap<String, ArrayList<Song>> map;
	static HashMap<String, Integer> playsOfGenres;
	static ArrayList<Integer> result;

	public static int[] solution(String[] genres, int[] plays) {
		map = new HashMap<>();
		playsOfGenres = new HashMap<>();
		result = new ArrayList<>();
		
		for (int i = 0; i < genres.length; i++) {
//			map.computeIfAbsent(genres[i], k -> new ArrayList<>())
//				.add(new Song(i, genres[i], plays[i]));
			map.putIfAbsent(genres[i], new ArrayList<>());
			map.get(genres[i]).add(new Song(i, genres[i], plays[i]));

			playsOfGenres.put(genres[i], playsOfGenres.getOrDefault(genres[i], 0) + plays[i]);
		}

		for (ArrayList<Song> songs : map.values()) {
			songs.sort(Comparator.comparingInt((Song song) -> song.play).reversed().thenComparingInt(song -> song.num));
		}
		
		ArrayList<String> orderedGenre =
			    new ArrayList<>(playsOfGenres.keySet());

		
		orderedGenre.sort((a,b) -> playsOfGenres.get(b) - playsOfGenres.get(a));
		
		for(String genre: orderedGenre) {
			ArrayList<Song> songs = map.get(genre);
			songs.stream().limit(2).forEach(song -> result.add(song.num));
		}

		return  result.stream()
		        .mapToInt(Integer::intValue)
		        .toArray();
	}

	public static class Song {
		int num;
		String genre;
		int play;

		Song(int n, String g, int p) {
			num = n;
			genre = g;
			play = p;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(new String[] { "classic", "pop", "classic", "classic", "pop" },
				new int[] { 500, 600, 150, 800, 2500 })));
	}
}
