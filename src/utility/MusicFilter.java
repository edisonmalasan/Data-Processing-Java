package utility;

import model.Music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class MusicFilter {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static List<Music> displayAllSongsAlphabetically(List<Music> musicList) {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
            return List.of();
        } else {
            System.out.println("\n====================================================================================================================================================================================================================");
            System.out.println("|                                           SONG TITLE                                                    |                                                 ARTIST                                                 |");
            System.out.println("====================================================================================================================================================================================================================");

            List<Music> sortedList = musicList.stream()
                    .sorted((m1, m2) -> m1.getSongTitle().compareToIgnoreCase(m2.getSongTitle()))
                    .collect(Collectors.toList());

            sortedList.forEach(music -> {
                String songTitle = music.getSongTitle();
                String artist = music.getSongArtist();

                System.out.printf("| %-103s | %-102s |%n", songTitle, artist);
            });
            System.out.println("====================================================================================================================================================================================================================");
            return sortedList;
        }
    }

    public static List<Music> displaySongsByGenre(List<Music> musicList, String genre) {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
            return List.of();
        } else {
            List<Music> filteredSongs = musicList.stream()
                    .filter(music -> music.getSongGenre() != null && music.getSongGenre().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());

            if (filteredSongs.isEmpty()) {
                System.out.println("No songs found for the genre " + genre);
            } else {
                System.out.println("\n=================================================================================================================================================================================================================================================");
                System.out.println("|                                           SONG TITLE                                                    |                                                 ARTIST                                                 |             GENRE          |");
                System.out.println("=================================================================================================================================================================================================================================================");

                filteredSongs.forEach(music -> {
                    String songTitle = music.getSongTitle();
                    String artist = music.getSongArtist();
                    String songGenre = music.getSongGenre();

                    System.out.printf("| %-103s | %-102s | %-26s |%n", songTitle, artist, songGenre);
                });

                System.out.println("=================================================================================================================================================================================================================================================");
            }
            return filteredSongs;
        }
    }


    public static List<Music> displaySongsByYearReleased(List<Music> musicList) {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
            return List.of();
        } else {
            try {
                System.out.println("Enter the year to filter the songs by:");
                int year = Integer.parseInt(bufferedReader.readLine());

                List<Music> filteredSongs = musicList.stream()
                        .filter(music -> music.getYearReleased() == year)
                        .sorted((m1, m2) -> m1.getYearReleased() - m2.getYearReleased())
                        .collect(Collectors.toList());

                if (filteredSongs.isEmpty()) {
                    System.out.println("No songs found for the year " + year);
                } else {
                    System.out.println("\n=================================================================================================================================================================================================================================================");
                    System.out.println("|                                           SONG TITLE                                                    |                                                 ARTIST                                                 |            YEAR            |");
                    System.out.println("=================================================================================================================================================================================================================================================");

                    filteredSongs.forEach(music -> {
                        String songTitle = music.getSongTitle();
                        String artist = music.getSongArtist();
                        int songYear = music.getYearReleased();

                        System.out.printf("| %-103s | %-102s | %-26d |%n", songTitle, artist, songYear);
                    });

                    System.out.println("=================================================================================================================================================================================================================================================");
                }
                return filteredSongs;
            } catch (NumberFormatException e) {
                System.out.println("Invalid year entered. Please enter a valid integer.");
                return List.of();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void displaySongsWithoutExplicit(List<Music> musicList) {
        List<Music> nonExplicitSongs = musicList.stream()
                .filter(music -> !music.isExplicit())
                .collect(Collectors.toList());

        if (nonExplicitSongs.isEmpty()) {
            System.out.println("No songs found without explicit content.");
        } else {
            System.out.println("\nSongs without Explicit Content:");
            System.out.println("====================================================================================================================================================================================");
            System.out.println("|                     SONG TITLE                     |                      ARTIST                         |                    GENRE                |            EXPLICIT        |");
            System.out.println("====================================================================================================================================================================================");
            nonExplicitSongs.forEach(music -> {
                String songTitle = music.getSongTitle();
                String artist = music.getSongArtist();
                String songGenre = music.getSongGenre();
                boolean songExplicit = music.isExplicit();

                System.out.printf("| %-50s | %-51s | %-40s | %-26b | %n", songTitle, artist, songGenre, songExplicit);
            });
            System.out.println("====================================================================================================================================================================================");
        }
    }


    public static void displaySongsWithExplicit(List<Music> musicList) {
        List<Music> explicitSongs = musicList.stream()
                .filter(Music::isExplicit)
                .collect(Collectors.toList());

        if (explicitSongs.isEmpty()) {
            System.out.println("No songs found with explicit content.");
        } else {
            System.out.println("\nSongs with Explicit Content:");
            System.out.println("====================================================================================================================================================================================");
            System.out.println("|                     SONG TITLE                     |                      ARTIST                         |                    GENRE                |            EXPLICIT        |");
            System.out.println("====================================================================================================================================================================================");
            explicitSongs.forEach(music -> {
                String songTitle = music.getSongTitle();
                String artist = music.getSongArtist();
                String songGenre = music.getSongGenre();
                boolean songExplicit = music.isExplicit();

                System.out.printf("| %-50s | %-51s | %-40s | %-26b | %n", songTitle, artist, songGenre, songExplicit);
            });
            System.out.println("====================================================================================================================================================================================");
        }
    }
}