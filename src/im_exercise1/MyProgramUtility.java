package im_exercise1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MyProgramUtility implements MusicDataManager {

    private static final List<Music> musicList = new ArrayList<>();

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    private static final String filename = "Team-8-DataSet.csv";

    public MyProgramUtility() throws FileNotFoundException, IOException {
        readSong();
    }


    public static void main(String[] args) throws IOException {
        int choice;
        MyProgramUtility myProgramUtility = new MyProgramUtility();

        System.out.println("""
                Welcome to the Music Data Analysis Program
                Custodio, Adria
                Ferrer, Princess Kyla
                Malasan, Edison
                Quitaneg, CJ""");

        while (true) {
            System.out.println("\nPress [ENTER] to see the Main Menu");
            bufferedReader.readLine();

            System.out.println("""
                ===============================================================
                                    What do you want to do?
                ===============================================================
                1. Display All Songs in the List (Alphabetical Order)
                2. Display Songs Based on Year Released
                3. Display Songs by a Specific Artist
                4. Display Songs of a Selected Genre
                5. Display Total Number of Songs per Genre
                6. Display Average Tempo of Songs per Genre
                7. Display Top 10 Songs by Popularity
                8. Search for a Song by Title
                9. Display Songs Based on Duration
                10. Display Songs without Explicit
                11. Display Songs with Explicit
                12. Exit Program
                ===============================================================
                Your Choice: """);

            String input = bufferedReader.readLine().trim();
            while (input.isEmpty() || !isValidInteger(input)) {
                System.out.println("Please enter a valid number (1-11):");
                input = bufferedReader.readLine().trim();
            }
            choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> {
                    myProgramUtility.displayAllSongsAlphabetically();
                }
                case 2 -> {
                    myProgramUtility.displaySongsByYearReleased();
                }
                case 3 -> {
                    myProgramUtility.displaySongsByArtist("");
                }
                case 4 -> {
                    // display all genre
                    System.out.println("Select a genre from the list below:");

                    // eto lang nakita ko sa list
                    String[] genres = {"country", "jazz", "rock", "metal", "Folk/Acoustic",
                            "easy listening", "World/Traditional", "hip hop", "blues",
                            "Dance/Electronic", "R&B", "pop", "rap", "latin", "REGGAE"};

                    System.out.println("======================================================");
                    for (int i = 0; i < genres.length; i++) {
                        System.out.printf("| %-2d. %-30s |\n", i + 1, genres[i]);
                    }
                    System.out.println("======================================================");

                    System.out.print("Enter the number of the genre you want to filter by: ");
                    try {
                        int genreChoice = Integer.parseInt(bufferedReader.readLine());

                        if (genreChoice >= 1 && genreChoice <= genres.length) {
                            String chosenGenre = genres[genreChoice - 1];
                            myProgramUtility.displaySongsByGenre(chosenGenre);
                        } else {
                            System.out.println("Invalid choice. Please enter a valid genre number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    } catch (IOException e) {
                        System.out.println("Error reading input.");
                    }
                }
                case 5 -> {
                    myProgramUtility.getTotalSongsPerGenre();
                }
                case 6 -> {
                    myProgramUtility.getAverageTempoPerGenre();
                }
                case 7 -> {
                    myProgramUtility.displayTopSongsByPopularity();
                }
                case 8 -> {
                    System.out.print("Enter the title of the song you want to search for: ");
                    String title = bufferedReader.readLine().trim();
                    myProgramUtility.searchSongByTitle(title);
                }
                case 9 -> {
                    System.out.println("Enter duration category (short, medium, long): ");
                    String durationCategory = bufferedReader.readLine().trim().toLowerCase();
                    myProgramUtility.displaySongsByDuration(durationCategory);
                }
                case 10 -> {
                    myProgramUtility.displaySongsWithoutExplicit();
                }
                case 11 -> {
                    myProgramUtility.displaySongsWithExplicit();
                }
                case 12 -> {
                    System.out.println("Exiting program...");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid choice, please select a valid option (1-11).");
                }
            }
        }
    }

    // helper method para sa menu
    private static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void readSong() throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            String[] parser = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            Music music = new Music(
                    parser[0], // artist
                    parser[1], // song title
                    Integer.parseInt(parser[2]), // duration
                    Boolean.parseBoolean(parser[3]), // explicit
                    Integer.parseInt(parser[4]), // year
                    parser[5], // popularity
                    parser[6], // song key
                    Double.parseDouble(parser[7]), // tempo
                    parser[8]  // genre
            );
            musicList.add(music);
        }
    }


    @Override
    public void displayAllSongsAlphabetically() throws IllegalArgumentException {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
        } else {
            System.out.println("\n====================================================================================================================================================================================================================");
            System.out.println("|                                           SONG TITLE                                                    |                                                 ARTIST                                                 |");
            System.out.println("====================================================================================================================================================================================================================");

            musicList.stream()
                    .sorted((m1, m2) -> m1.getSongTitle().compareToIgnoreCase(m2.getSongTitle()))
                    .forEach(music -> {
                        String songTitle = music.getSongTitle();
                        String artist = music.getSongArtist();

                        System.out.printf("| %-103s | %-102s |%n", songTitle, artist);
                    });
            System.out.println("====================================================================================================================================================================================================================");
        }
    }

    @Override
    public void displaySongsByYearReleased() throws IllegalArgumentException {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
        } else {
            try {
                System.out.println("Enter the year to filter the songs by:");
                int year = Integer.parseInt(bufferedReader.readLine());

                List<Music> filteredSongs = musicList.stream()
                        .filter(music -> music.getYearReleased() == year)
                        .sorted((m1, m2) -> m1.getYearReleased() - m2.getYearReleased())
                        .toList();

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
            } catch (NumberFormatException e) {
                System.out.println("Invalid year entered. Please enter a valid integer.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void displaySongsByArtist(String artist) throws IllegalArgumentException {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
        } else {
            try {
                System.out.println("Displaying all artists in the list: ");
                System.out.println("\n======================================================");
                System.out.println("|                    ARTIST NAME                    |");
                System.out.println("======================================================");
                musicList.stream()
                        .map(Music::getSongArtist)
                        .distinct()
                        .sorted()
                        .forEach(artistName -> System.out.printf("| %-50s |\n", artistName));
                System.out.println("======================================================");

                System.out.println("Enter the artist to filter the songs by: ");
                String chosenArtist = bufferedReader.readLine().trim();

                List<Music> artistSongs = musicList.stream()
                        .filter(music -> music.getSongArtist().equalsIgnoreCase(chosenArtist))
                        .collect(Collectors.toList());

                if (artistSongs.isEmpty()) {
                    System.out.println("No songs found for the artist " + chosenArtist);
                } else {
                    System.out.println("\nSongs by " + chosenArtist + " are: ");
                    System.out.println("======================================================");
                    System.out.println("|                    SONG TITLE                     |");
                    System.out.println("======================================================");
                    artistSongs.forEach(music ->
                            System.out.printf("| %-50s |\n", music.getSongTitle())
                    );
                    System.out.println("======================================================");
                }
            } catch (IOException e) {
                System.out.println("Error reading artist from the list");
            }
        }
    }


    @Override
    public void displaySongsByGenre(String genre) throws IllegalArgumentException {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
        } else {
            List<Music> filteredSongs = musicList.stream()
                    .filter(music -> music.getSongGenre().equalsIgnoreCase(genre))
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
        }
    }



    @Override
    public Map<String, Integer> getTotalSongsPerGenre() throws IllegalArgumentException {
        Map<String, Integer> genreCountMap = musicList.stream()
                .collect(Collectors.groupingBy(Music::getSongGenre, Collectors.summingInt(e -> 1)));

        if (genreCountMap.isEmpty()) {
            System.out.println("There are no songs to display");
        } else {
            System.out.println("\n============================================================================================================");
            System.out.println("|                                   GENRE                              |             TOTAL SONGS           |");
            System.out.println("============================================================================================================");

            int index = 1;
            for (Map.Entry<String, Integer> entry : genreCountMap.entrySet()) {
                System.out.printf("| %-69s | %-32d |%n", entry.getKey(), entry.getValue());
            }

            System.out.println("============================================================================================================");
        }

        return genreCountMap;
    }

    @Override
    public Map<String, Double> getAverageTempoPerGenre() throws IllegalArgumentException {
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

    @Override
    public void displayTopSongsByPopularity() throws IllegalArgumentException {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list.");
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
                            return 0; // Handle any parsing errors
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
        }
    }
    @Override
    public List<Music> searchSongByTitle(String title) throws IllegalArgumentException {
        List<Music> keySong = musicList.stream()
                .filter(music -> music.getSongTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());

        if (keySong.isEmpty()) {
            System.out.println("No songs found with the title: " + title);
        } else {
            System.out.println("\nSongs found with the title '" + title + "':");
            System.out.println("====================================================================================================================================================================================");
            System.out.println("|           SONG TITLE                 |                   ARTIST                      |   DURATION   |   YEAR   |   POPULARITY   |   KEY   |   TEMPO   |   GENRE   |   EXPLICIT   |");
            System.out.println("====================================================================================================================================================================================");

            keySong.forEach(music -> {
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
        return keySong;
    }


    @Override
    public void displaySongsByDuration(String durationCategory) throws IllegalArgumentException {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
        } else {
            List<Music> filteredSongs;

            switch (durationCategory.toLowerCase()) {
                case "short":
                    filteredSongs = musicList.stream()
                            .filter(music -> music.getSongDuration() < 180000)  // Less than 3 minutes
                            .collect(Collectors.toList());
                    break;
                case "medium":
                    filteredSongs = musicList.stream()
                            .filter(music -> music.getSongDuration() >= 180000 && music.getSongDuration() <= 300000)  // 3 to 5 minutes
                            .collect(Collectors.toList());
                    break;
                case "long":
                    filteredSongs = musicList.stream()
                            .filter(music -> music.getSongDuration() > 300000)  // More than 5 minutes
                            .collect(Collectors.toList());
                    break;
                default:
                    System.out.println("Invalid duration category. Use 'short', 'medium', or 'long'.");
                    return;
            }

            if (filteredSongs.isEmpty()) {
                System.out.println("No songs found for the selected duration category: " + durationCategory);
            } else {
                System.out.println("\n==================================================================");
                System.out.println("|        SONG TITLE         |       ARTIST       |    DURATION    |");
                System.out.println("==================================================================");

                filteredSongs.forEach(music -> {
                    int durationMs = music.getSongDuration();
                    int minutes = durationMs / 60000;
                    int seconds = (durationMs % 60000) / 1000;
                    String formattedDuration = String.format("%d:%02d", minutes, seconds);

                    System.out.printf("| %-25s | %-18s | %-14s |%n",
                            music.getSongTitle(), music.getSongArtist(), formattedDuration);
                });

                System.out.println("==================================================================");
            }
        }
    }

    @Override
    public void displaySongsWithoutExplicit() throws IllegalArgumentException {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
        } else {
            List<Music> filteredSongs = musicList.stream()
                    .filter(music -> !music.isExplicit() )
                    .collect(Collectors.toList());

            if (filteredSongs.isEmpty()) {
                System.out.println("No songs found");
            } else {
                System.out.println("\n============================================================================================================================================================================================================================================================================================");
                System.out.println("|                                           SONG TITLE                                                    |                                                 ARTIST                                                 |             GENRE              |              Explicit                 |");
                System.out.println("=============================================================================================================================================================================================================================================================================================");
                filteredSongs.forEach(music -> {
                    String songTitle = music.getSongTitle();
                    String artist = music.getSongArtist();
                    String songGenre = music.getSongGenre();
                    boolean songExplicit = music.isExplicit();
                    System.out.printf("| %-103s | %-102s | %-40s | %-26b | %n", songTitle, artist, songGenre, songExplicit);
                });

                System.out.println("============================================================================================================================================================================================================================================================================================");
            }
        }
    }

    @Override
    public void displaySongsWithExplicit() throws IllegalArgumentException {
        if (musicList.isEmpty()) {
            System.out.println("There are no songs in the list");
            } else {
            System.out.println("\n============================================================================================================================================================================================================================================================================================");
            System.out.println("|                                           SONG TITLE                                                    |                                                 ARTIST                                                 |             GENRE              |              Explicit                 |");
            System.out.println("=============================================================================================================================================================================================================================================================================================");

            musicList.forEach(music -> {
                String songTitle = music.getSongTitle();
                String artist = music.getSongArtist();
                String songGenre = music.getSongGenre();
                boolean songExplicit = music.isExplicit();
                System.out.printf("| %-103s | %-102s | %-40s | %-26b | %n", songTitle, artist, songGenre, songExplicit);
            });
            System.out.println("=============================================================================================================================================================================================================================================================================================");
           }
        }
    }

