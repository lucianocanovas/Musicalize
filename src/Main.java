import media.Song;
import client.User;

public class Main {
    public static void main(String[] args) {

        User.LoginMenu();

        Song Song = new Song("songs/Homero Simpson - ME VOY.mp3", "Me voy", "Homero Simpson");
        System.out.println("Reproduciendo: " + Song.getName() + " de " + Song.getArtist());
    }
}
