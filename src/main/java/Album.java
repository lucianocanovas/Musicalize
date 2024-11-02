import java.sql.*;
import java.util.ArrayList;

public class Album {

    private int id;
    private String name;
    private String author;

    public Album(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    // METODO PARA LISTAR ALBUMES
    public static void list(String author) {
        Connection connection = Database.connect();
        String query = "SELECT * FROM Album WHERE author = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, author);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Album album = new Album(result.getInt("id"), result.getString("name"), result.getString("author"));
                System.out.println("║ ["+ album.id +"] " + album.name + " - " + album.author);
                query = "SELECT * FROM Media WHERE albumID = ?";
                try {
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, album.id);
                    ResultSet songResult = statement.executeQuery();
                    while (songResult.next()) {
                        System.out.println("║ - ["+ songResult.getInt("id") +"] " + songResult.getString("name"));
                    }
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                }
            }
            Database.close(connection);
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
        }
    }

    // METODO PARA REPRODUCIR UN ALBUM
    public void play () {
        Connection connection = Database.connect();
        String query = "SELECT * FROM Media WHERE albumID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.id);
            ResultSet result = statement.executeQuery();
            System.out.println("║ PLAYING ALBUM: " + this.name + " - " + this.author);
            while (result.next()) {
                System.out.println("║ [~] PLAYING: " + result.getString("name"));
                Media media = new Media(result.getString("path"));
                media.play();
            }
            Database.close(connection);
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
        }
    }
}