package im_exercise1;

import utility.MusicFilter;
import utility.MusicProcess;
import utility.MusicSearch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyProgram {

    private static final List<Music> musicList = new ArrayList<>();
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final String filename = "Team-8-DataSet.csv";

    public MyProgram() throws IOException {
        readSong();
    }

    public static void main(String[] args) throws IOException {
        MyProgram programUtility = new MyProgram();

        System.out.println(""" 
                Welcome to the Music Data Analysis Program
                Custodio, Adria
                Ferrer, Princess Kyla
                Malasan, Edison
                Quitaneg, CJ
                """);

        while (true) {
            System.out.println("\nPress [ENTER] to see the Main Menu");
            bufferedReader.readLine();

            System.out.println("""
                    ===============================================================
                                        What do you want to do?
                    ===============================================================
                    1. Filter
                    2. Search
                    3. Process Data
                    4. Exit Program
                    ===============================================================
                    Your Choice: """);

            String choice = bufferedReader.readLine().trim();

            switch (choice) {
                case "1" -> filterMenu(musicList);
                case "2" -> searchMenu(musicList);
                case "3" -> processDataMenu(musicList);
                case "4" -> {
                    System.out.println("Exiting program...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please select a valid option (1-4).");
            }
        }
    }

    private static void filterMenu(List<Music> musicList) throws IOException {
        if (musicList.isEmpty()) {
            System.out.println("The music list is empty. Please add songs to filter.");
            return;
        }

        while (true) {
            System.out.println("""
                ===============================================================
                                Filter Menu
                ===============================================================
                a. Filter by Year Released
                b. Filter by Genre
                c. Filter Songs with Explicit Content
                d. Filter Songs without Explicit Content
                e. Return to Main Menu
                ===============================================================
                Your Choice: """);

            String choice = bufferedReader.readLine().trim().toLowerCase();

            switch (choice) {
                case "a" -> MusicFilter.displaySongsByYearReleased(musicList);
                case "b" -> {
                    System.out.print("Enter the genre to filter by: ");
                    String genre = bufferedReader.readLine().trim();
                    MusicFilter.displaySongsByGenre(musicList, genre);
                }
                case "c" -> MusicFilter.displaySongsWithExplicit(musicList);
                case "d" -> MusicFilter.displaySongsWithoutExplicit(musicList);
                case "e" -> {
                    System.out.println("Returning to the main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void searchMenu(List<Music> musicList) throws IOException {
        if (musicList.isEmpty()) {
            System.out.println("The music list is empty. Please add songs to search.");
            return;
        }

        while (true) {
            System.out.println("""
                ===============================================================
                                Search Menu
                ===============================================================
                a. Search by Artist and Display Music
                b. Search by Music Title
                c. Return to Main Menu
                ===============================================================
                Your Choice: """);

            String choice = bufferedReader.readLine().trim().toLowerCase();

            switch (choice) {
                case "a" -> {
                    System.out.print("Enter the artist name to search: ");
                    String artist = bufferedReader.readLine().trim();
                    MusicSearch.displaySongsByArtist(musicList, artist);
                }
                case "b" -> {
                    System.out.print("Enter the title of the song: ");
                    String title = bufferedReader.readLine().trim();
                    MusicSearch.searchSongByTitle(musicList, title);
                }
                case "c" -> {
                    System.out.println("Returning to the main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void processDataMenu(List<Music> musicList) throws IOException {
        if (musicList.isEmpty()) {
            System.out.println("The music list is empty. Please add songs to process.");
            return;
        }

        while (true) {
            System.out.println("""
                ===============================================================
                                Process Data Menu
                ===============================================================
                a. Display Top 10 Songs by Popularity
                b. Get Average Tempo per Genre
                c. Get Total Number of Songs per Genre
                d. Return to Main Menu
                ===============================================================
                Your Choice: """);

            String choice = bufferedReader.readLine().trim().toLowerCase();

            switch (choice) {
                case "a" -> MusicProcess.displayTopSongsByPopularity(musicList);
                case "b" -> MusicProcess.getAverageTempoPerGenre(musicList);
                case "c" -> MusicProcess.getTotalSongsPerGenre(musicList);
                case "d" -> {
                    System.out.println("Returning to the main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void readSong() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] details = parseCSVLine(line);

                if (details.length != 9) {
                    System.err.println("Skipping invalid row: " + line);
                    continue;
                }

                try {
                    String artist = details[0].trim();
                    String song = details[1].trim();
                    int durationMs = Integer.parseInt(details[2].trim());
                    boolean explicit = Boolean.parseBoolean(details[3].trim());
                    int year = Integer.parseInt(details[4].trim());
                    String popularity = details[5].trim();
                    String key = details[6].trim();
                    double tempo = Double.parseDouble(details[7].trim());
                    String genre = details[8].trim();


                    Music music = new Music(artist, song, durationMs, explicit, year, popularity, key, tempo, genre);
                    musicList.add(music);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping row due to parsing error: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private String[] parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString().trim());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        fields.add(currentField.toString().trim());
        return fields.toArray(new String[0]);
    }
}
