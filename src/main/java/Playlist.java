import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class Playlist {

    private String name;
    private int user;
    private ArrayList<Media> list;

    public Playlist(String name, int user) {
        this.name = name;
        this.user = user;
        this.list = new ArrayList<Media>();
    }

    // METODO PARA LISTAR LAS CANCIONES Y PODCASTS DE UNA PLAYLIST
    public void listMedia() {
        Connection connection = Database.connect();
        String query = "SELECT mediaID FROM PlaylistMedia WHERE playlistID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.user);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                query = "SELECT * FROM Media WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, result.getInt("mediaID"));
                ResultSet mediaResult = statement.executeQuery();
                while (mediaResult.next()) {
                    System.out.println("║ [" + mediaResult.getInt("id") + "] " + mediaResult.getString("name"));
                }
            }
            Database.close(connection);
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
        }
    }

    // METODO PARA REPRODUCIR UNA PLAYLIST
    public void play() {
        Connection connection = Database.connect();
        String query = "SELECT mediaID FROM PlaylistMedia WHERE playlistID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.user);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                query = "SELECT * FROM Media WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, result.getInt("mediaID"));
                ResultSet mediaResult = statement.executeQuery();
                while (mediaResult.next()) {
                    if (mediaResult.getString("type").equals("song")) {
                        Song song = new Song(mediaResult.getString("path"), mediaResult.getString("name"), mediaResult.getString("author"));
                        this.list.add(song);
                    } else {
                        Podcast podcast = new Podcast(mediaResult.getString("path"), mediaResult.getString("name"), mediaResult.getString("author"));
                        this.list.add(podcast);
                    }
                }
            }
            Database.close(connection);
            System.out.println("║ PLAYING PLAYLIST: " + this.name);
            for (Media media : list) {
                media.play();
            }
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
        }
    }

    // METODO PARA AGREGAR UNA CANCION O PODCAST A UNA PLAYLIST
    public void add(int mediaID) {
        Connection connection = Database.connect();
        String query = "INSERT INTO PlaylistMedia (playlistID, mediaID) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.user);
            statement.setInt(2, mediaID);
            statement.executeUpdate();
            Database.close(connection);
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
        }
    }

    // METODO PARA ELIMINAR UNA CANCION O PODCAST DE UNA PLAYLIST
    public void remove(int mediaID) {
        Connection connection = Database.connect();
        String query = "DELETE FROM PlaylistMedia WHERE playlistID = ? AND mediaID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.user);
            statement.setInt(2, mediaID);
            statement.executeUpdate();
            Database.close(connection);
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
        }
    }

}
