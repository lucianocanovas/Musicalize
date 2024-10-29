import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class Media {

    protected String path;
    protected AdvancedPlayer player;
    protected Thread mediaThread;

    public Media(String path) {
        this.path = path;
    }

    // METODO PARA LISTAR CANCIONES Y PODCASTS
    public static void list() {
        Connection connection = Database.connect();
        String query = "SELECT * FROM Media WHERE type = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "song");
            ResultSet SongResult = statement.executeQuery();
            System.out.println("║ SONGS");
            while (SongResult.next()) {
                System.out.println("║ ["+ SongResult.getString("id") +"] " + SongResult.getString("name"));
            }
            statement.setString(1, "podcast");
            ResultSet PodcastResult = statement.executeQuery();
            System.out.println("║ PODCASTS");
            while (PodcastResult.next()) {
                System.out.println("║ ["+ PodcastResult.getString("id") +"] " + PodcastResult.getString("name"));
            }
            Database.close(connection);
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
        }
    }

    // METODO PARA REPRODUCIR MEDIOS
    public void play() {
        Scanner input = new Scanner(System.in);
        mediaThread = new Thread(() -> {
            try {
                FileInputStream file = new FileInputStream(this.path);
                player = new AdvancedPlayer(file);
                player.play();
            } catch (FileNotFoundException | JavaLayerException e) {
                System.out.println("[!] ERROR: " + e.getMessage());
                stop();
            }
        });
        mediaThread.start();
        System.out.println("╠═ [~] PRESS ANY KEY TO PLAY NEXT");
        System.out.print("║ ");
        input.next();
        stop();
    }

    // METODO PARA DETENER REPRODUCCION
    public void stop() {
        if (player != null) {
            player.close();
        }
    }

}
