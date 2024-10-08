package media;

public class Podcast extends Media {

    private String name;
    private String host;

    public Podcast(String path, String name, String host) {
        super(path);
        this.name = name;
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
