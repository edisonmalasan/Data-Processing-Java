package im_exercise1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MusicDataManager {

    void readSong() throws FileNotFoundException, IOException;

    void displayAllSongsAlphabetically() throws IllegalArgumentException;

    void displaySongsByYearReleased() throws IllegalArgumentException;

    void displaySongsByArtist(String artist) throws IllegalArgumentException;

    void displaySongsByGenre(String genre) throws IllegalArgumentException;

    Map<String, Integer> getTotalSongsPerGenre() throws IllegalArgumentException;

    Map<String, Double> getAverageTempoPerGenre() throws IllegalArgumentException;

    void displayTopSongsByPopularity() throws IllegalArgumentException;

    List<Music> searchSongByTitle(String title) throws IllegalArgumentException;

    void displaySongsByDuration(String durationCategory) throws IllegalArgumentException;

    void displaySongsWithoutExplicit() throws IllegalArgumentException;
}
