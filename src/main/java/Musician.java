import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Musician extends Artist {

    public Musician(int id, String username, String password) {
        super(id, username, password);
        this.type = "musician";
    }

    @Override
    public void mediaMenu() {
        Scanner input = new Scanner(System.in);
        Connection connection = Database.connect();
        Song.list();
        System.out.println("║ MEDIA MENU");
        System.out.println("║ (YOU CAN PLAY, UPLOAD, DELETE, OR EDIT A SONG)");
        System.out.println("╠═ [~] PLAY");
        System.out.println("╠═ [~] UPLOAD");
        System.out.println("╠═ [~] DELETE");
        System.out.println("╠═ [~] EDIT");
        System.out.println("╠═ [~] BACK");
        System.out.print("║ [#] ⇒ ");
        String command = input.nextLine().toLowerCase();
        switch (command) {
            case "play":
                System.out.print("║ [#] ID ⇒ ");
                int mediaID = 0;
                try {
                    mediaID = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    mediaMenu();
                    break;
                }
                input.nextLine();
                String query = "SELECT * FROM Media WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, mediaID);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        Song song = new Song(result.getString("path"), result.getString("name"), result.getString("author"));
                        song.play();
                        Database.close(connection);
                        mediaMenu();
                    } else {
                        System.out.println("║ [!] SONG NOT FOUND");
                        Database.close(connection);
                        mediaMenu();
                    }
                    Database.close(connection);
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    mediaMenu();
                }
                break;
            case "upload":
                System.out.print("║ [↪] SONG NAME ⇒ ");
                String name = input.nextLine();
                System.out.println("║ [ ] NO ALBUM ");
                Album.list(this.username);
                System.out.print("║ [↪] ALBUM ID ⇒ ");
                int album = input.nextInt();
                input.nextLine();
                System.out.print("║ [#] FILE NAME ⇒ ");
                String path = input.nextLine();
                query = "INSERT INTO Media (name, author, type, albumID, path) VALUES (?, ?, ?, ?, ?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, this.username);
                    statement.setString(3, "song");
                    statement.setInt(4, album);
                    statement.setString(5, "src/main/resources/songs/"+path+".mp3");
                    statement.executeUpdate();
                    System.out.println("║ [✓] SONG UPLOADED");
                    System.out.println("║ [!] MAKE SURE TO ADD THE .MP3 FILE TO src/main/resources/songs");
                    Database.close(connection);
                    mediaMenu();
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    mediaMenu();
                }
                break;
            case "delete":
                System.out.print("║ [#] ID ⇒ ");
                mediaID = 0;
                try {
                    mediaID = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    mediaMenu();
                    break;
                }
                input.nextLine();
                query = "SELECT * FROM Media WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, mediaID);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        query = "DELETE FROM Media WHERE id = ?";
                        try {
                            statement = connection.prepareStatement(query);
                            statement.setInt(1, mediaID);
                            statement.executeUpdate();
                            System.out.println("║ [✓] SONG DELETED");
                            Database.close(connection);
                        } catch (SQLException e) {
                            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                        }
                        Database.close(connection);
                        mediaMenu();
                    } else {
                        System.out.println("║ [!] YOU CAN ONLY DELETE YOUR OWN SONGS");
                        Database.close(connection);
                        mediaMenu();
                    }
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    mediaMenu();
                }
                break;
            case "edit":
                System.out.print("║ [#] ID ⇒ ");
                mediaID = 0;
                try {
                    mediaID = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    mediaMenu();
                    break;
                }
                input.nextLine();
                query = "SELECT * FROM Media WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, mediaID);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        System.out.print("║ [↪] SONG NAME ⇒ ");
                        name = input.next();
                        System.out.print("║ [ ] NO ALBUM ");
                        Album.list(this.username);
                        System.out.print("║ [↪] ALBUM ID ⇒ ");
                        album = input.nextInt();
                        System.out.print("║ [#] FILE NAME ⇒ ");
                        path = input.next();
                        query = "UPDATE Media SET name = ?, albumID = ?, path = ? WHERE id = ?";
                        try {
                            statement = connection.prepareStatement(query);
                            statement.setString(1, name);
                            statement.setInt(2, album);
                            statement.setString(3, "src/main/resources/songs/"+path);
                            statement.executeUpdate();
                            System.out.println("║ [✓] SONG UPDATED");
                            Database.close(connection);
                        } catch (SQLException e) {
                            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                            Database.close(connection);
                        }
                    } else {
                        System.out.println("║ [!] YOU CAN ONLY EDIT YOUR OWN SONGS");
                        Database.close(connection);
                    }
                    mediaMenu();
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    mediaMenu();
                }
                break;
            case "back":
                Database.close(connection);
                mainMenu();
                break;
            default:
                System.out.println("║ [!] INVALID COMMAND");
                Database.close(connection);
                mediaMenu();
                break;
        }
    }

    @Override
    public void albumMenu() {
        Scanner input = new Scanner(System.in);
        Connection connection = Database.connect();
        Album.list(this.username);
        System.out.println("║ ALBUM MENU");
        System.out.println("║ (YOU CAN CREATE, DELETE, OR EDIT AN ALBUM)");
        System.out.println("╠═ [~] PLAY");
        System.out.println("╠═ [~] CREATE");
        System.out.println("╠═ [~] DELETE");
        System.out.println("╠═ [~] EDIT");
        System.out.println("╠═ [~] BACK");
        System.out.print("║ [#] ⇒ ");
        String command = input.nextLine().toLowerCase();
        switch (command) {
            case "play":
                System.out.print("║ [↪] ID ⇒ ");
                int albumID = 0;
                try {
                    albumID = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    albumMenu();
                    break;
                }
                input.nextLine();
                connection = Database.connect();
                String query = "SELECT * FROM Album WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, albumID);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        Album album = new Album(result.getInt("id"), result.getString("name"), result.getString("author"));
                        album.play();
                    } else {
                        System.out.println("║ [!] ALBUM NOT FOUND");
                    }
                    Database.close(connection);
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                }
                albumMenu();
                break;
            case "create":
                System.out.print("║ [↪] ALBUM NAME ⇒ ");
                String name = input.nextLine();
                query = "INSERT INTO Album (name, author) VALUES (?, ?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, this.username);
                    statement.executeUpdate();
                    System.out.println("║ [✓] ALBUM CREATED");
                    Database.close(connection);
                    albumMenu();
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    albumMenu();
                }
                break;
            case "delete":
                System.out.print("║ [#] ID ⇒ ");
                albumID = 0;
                try {
                    albumID = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    albumMenu();
                    break;
                }
                input.nextLine();
                query = "SELECT * FROM Album WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, albumID);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        query = "DELETE FROM Album WHERE id = ?";
                        try {
                            statement = connection.prepareStatement(query);
                            statement.setInt(1, albumID);
                            statement.executeUpdate();
                            System.out.println("║ [✓] ALBUM DELETED");
                            Database.close(connection);
                        } catch (SQLException e) {
                            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                            Database.close(connection);
                        }
                        albumMenu();
                    } else {
                        System.out.println("║ [!] YOU CAN ONLY DELETE YOUR OWN ALBUMS");
                        Database.close(connection);
                        albumMenu();
                    }
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    albumMenu();
                }
                break;
            case "edit":
                System.out.print("║ [#] ID ⇒ ");
                albumID = 0;
                try {
                    albumID = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    albumMenu();
                    break;
                }
                input.nextLine();
                query = "SELECT * FROM Album WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, albumID);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        System.out.print("║ [↪] ALBUM NAME ⇒ ");
                        name = input.nextLine();
                        query = "UPDATE Album SET name = ? WHERE id = ?";
                        try {
                            statement = connection.prepareStatement(query);
                            statement.setString(1, name);
                            statement.setInt(2, albumID);
                            statement.executeUpdate();
                            System.out.println("║ [✓] ALBUM UPDATED");
                            Database.close(connection);
                        } catch (SQLException e) {
                            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                            Database.close(connection);
                        }
                    } else {
                        System.out.println("║ [!] YOU CAN ONLY EDIT YOUR OWN ALBUMS");
                        Database.close(connection);
                    }
                    albumMenu();
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    albumMenu();
                }
                break;
            case "back":
                mainMenu();
                break;
            default:
                System.out.println("║ [!] INVALID COMMAND");
                albumMenu();
                break;
        }
    }

}