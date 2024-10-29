import java.util.Scanner;

public class Artist extends User {

    public Artist(int id, String username, String password) {
        super(id, username, password);
        this.type = "artist";
    }

}
