package media;

public class Song extends Media {

    private String name;
    private String artist;

    public Song(String path, String name, String artist) {
        super(path);
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
