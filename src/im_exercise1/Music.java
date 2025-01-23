package im_exercise1;

public class Music implements Comparable<Music>{
    private String songArtist;
    private String songTitle;
    private int songDuration;
    private boolean explicit;
    private int yearReleased;
    private String songPopularity;
    private String songKey;
    private double songTempo;
    private String songGenre;

    public Music(String songArtist, String songTitle, int songDuration, boolean explicit, int yearReleased, String songPopularity, String songKey, double songTempo, String songGenre) {
        this.songArtist = songArtist;
        this.songTitle = songTitle;
        this.songDuration = songDuration;
        this.explicit = explicit;
        this.yearReleased = yearReleased;
        this.songPopularity = songPopularity;
        this.songKey = songKey;
        this.songTempo = songTempo;
        this.songGenre = songGenre;
    }

    public Music(String detail, String detail1, String detail2, boolean explicit, int yearReleased, double v) {
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public int getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public String getSongPopularity() {
        return songPopularity;
    }

    public void setSongPopularity(String songPopularity) {
        this.songPopularity = songPopularity;
    }

    public String getSongKey() {
        return songKey;
    }

    public void setSongKey(String songKey) {
        this.songKey = songKey;
    }

    public double getSongTempo() {
        return songTempo;
    }

    public void setSongTempo(double songTempo) {
        this.songTempo = songTempo;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }

    @Override
    public String toString() {
        return "Music{" +
                "songArtist='" + songArtist + '\'' +
                ", songTitle='" + songTitle + '\'' +
                ", songDuration=" + songDuration +
                ", explicit=" + explicit +
                ", yearReleased='" + yearReleased + '\'' +
                ", songPopularity='" + songPopularity + '\'' +
                ", songKey='" + songKey + '\'' +
                ", songTempo=" + songTempo +
                ", songGenre='" + songGenre + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return songTitle.compareTo(((Music) obj).getSongTitle()) == 0;
        } else {
            return false;
        }
    }


    @Override
    public int compareTo(Music o) {
        return this.songTitle.compareTo(o.songTitle);
    }
}
