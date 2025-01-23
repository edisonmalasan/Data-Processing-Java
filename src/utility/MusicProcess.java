package utility;

import im_exercise1.Music;

import java.util.*;
import java.util.stream.Collectors;

public class MusicProcess {

    public static Map<String, Integer> getTotalSongsPerGenre(List<Music> musicList) {
        Map<String, Integer> genreCountMap = musicList.stream()
                .flatMap(music -> Arrays.stream(music.getSongGenre().split(",")))
                .map(String::trim)
                .collect(Collectors.groupingBy(genre -> genre, Collectors.summingInt(e -> 1)));

        if (genreCountMap.isEmpty()) {
            System.out.println("There are no songs to display");
        } else {
            System.out.println("\n============================================================================================================");
            System.out.println("|                                   GENRE                              |             TOTAL SONGS           |");
            System.out.println("============================================================================================================");

            genreCountMap.forEach((genre, count) ->
                    System.out.printf("| %-69s | %-32d |%n", genre, count)
            );

            System.out.println("============================================================================================================");
        }

        return genreCountMap;
    }

    public static Map<String, Double> getAverageTempoPerGenre(List<Music> musicList) {
        Map<String, Double> averageTempoMap = musicList.stream()
                .collect(Collectors.groupingBy(Music::getSongGenre,
                        Collectors.averagingDouble(Music::getSongTempo)));

        if (averageTempoMap.isEmpty()) {
            System.out.println("There are no songs to display");
        } else {
            System.out.println("\n============================================================================================================");
            System.out.println("|                                   GENRE                              |             AVERAGE TEMPO         |");
            System.out.println("============================================================================================================");

            averageTempoMap.forEach((genre, avgTempo) ->
                    System.out.printf("| %-69s | %-32.2f |%n", genre, avgTempo)
            );
            System.out.println("============================================================================================================");
        }

        return averageTempoMap;
    }

    public static List<Music> displayTopSongsByPopularity(List<Music> musicList) {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list.");
            return Collections.emptyList();
        } else {
            List<Music> uniqueSongs = musicList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(
                                    Comparator.comparing(m -> (m.getSongTitle() + m.getSongArtist()).toLowerCase())
                            )), ArrayList::new));

            List<Music> topSongs = uniqueSongs.stream()
                    .sorted((m1, m2) -> {
                        try {
                            return Integer.parseInt(m2.getSongPopularity()) - Integer.parseInt(m1.getSongPopularity());
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    })
                    .limit(10)
                    .collect(Collectors.toList());

            if (topSongs.isEmpty()) {
                System.out.println("No songs found for popularity ranking.");
            } else {
                System.out.println("\n====================================================================================================================================================================================================================");
                System.out.println("|                                           SONG TITLE                                                    |                                                 ARTIST                                                 |            POPULARITY        |");
                System.out.println("====================================================================================================================================================================================================================");

                topSongs.forEach(music -> {
                    String songTitle = music.getSongTitle();
                    String songArtist = music.getSongArtist();
                    String songPopularity = music.getSongPopularity();

                    System.out.printf("| %-103s | %-102s | %-26s |%n", songTitle, songArtist, songPopularity);
                });

                System.out.println("====================================================================================================================================================================================================================");
            }
            return topSongs;
        }
    }
}