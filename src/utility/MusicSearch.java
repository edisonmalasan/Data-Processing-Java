package utility;

import model.Music;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class MusicSearch {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static List<Music> displaySongsByArtist(List<Music> musicList, String artist) {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list.");
            return List.of();
        }

        List<Music> artistSongs = musicList.stream()
                .filter(music -> music.getSongArtist().equalsIgnoreCase(artist))
                .collect(Collectors.toList());

        if (artistSongs.isEmpty()) {
            System.out.println("No songs found for the artist: " + artist);
        } else {
            System.out.println("\nSongs by " + artist + ":");
            System.out.println("======================================================");
            System.out.println("|                    SONG TITLE                     |");
            System.out.println("======================================================");
            artistSongs.forEach(music -> System.out.printf("| %-50s |\n", music.getSongTitle()));
            System.out.println("======================================================");
        }
        return artistSongs;
    }


    public static List<Music> searchSongByTitle(List<Music> musicList, String title) {
        List<Music> matchedSongs = musicList.stream()
                .filter(music -> music.getSongTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());

        if (matchedSongs.isEmpty()) {
            System.out.println("No songs found with the title: " + title);
        } else {
            System.out.println("\nSongs found with the title '" + title + "':");
            System.out.println("====================================================================================================================================================================================");
            System.out.println("|           SONG TITLE                 |                   ARTIST                      |   DURATION   |   YEAR   |   POPULARITY   |   KEY   |   TEMPO   |   GENRE   |   EXPLICIT   |");
            System.out.println("====================================================================================================================================================================================");

            matchedSongs.forEach(music -> {
                String songTitle = music.getSongTitle();
                String artist = music.getSongArtist();
                int durationMs = music.getSongDuration();
                int minutes = durationMs / 60000;
                int seconds = (durationMs % 60000) / 1000;
                String formattedDuration = String.format("%d:%02d", minutes, seconds);
                int year = music.getYearReleased();
                String popularity = music.getSongPopularity();
                String key = music.getSongKey();
                double tempo = music.getSongTempo();
                String genre = music.getSongGenre();
                boolean isExplicit = music.isExplicit();

                System.out.printf("| %-36s | %-30s | %-14s | %-8d | %-12s | %-8s | %-8.2f | %-12s | %-12b |%n",
                        songTitle, artist, formattedDuration, year, popularity, key, tempo, genre, isExplicit);
            });

            System.out.println("====================================================================================================================================================================================");
        }
        return matchedSongs;
    }
}