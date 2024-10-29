import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Host extends Artist {

    public Host(int id, String username, String password) {
        super(id, username, password);
        this.type = "host";
    }

    @Override
    public void mediaMenu() {
        Scanner input = new Scanner(System.in);
        Connection connection = Database.connect();
        System.out.println("║ MEDIA MENU");
        System.out.println("║ (YOU CAN PLAY, UPLOAD, DELETE, OR EDIT A PODCAST)");
        System.out.println("╠═ [~] PLAY");
        System.out.println("╠═ [~] UPLOAD");
        System.out.println("╠═ [~] DELETE");
        System.out.println("╠═ [~] EDIT");
        System.out.println("╠═ [~] BACK");
        System.out.print("║ [#] ⇒ ");
        String command = input.nextLine().toLowerCase();
        switch (command) {
            case "play":
                System.out.print("║ [↪] ID ⇒ ");
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
                        Podcast podcast = new Podcast(result.getString("path"), result.getString("name"), result.getString("author"));
                        podcast.play();
                        mediaMenu();
                    } else {
                        System.out.println("║ [!] PODCAST NOT FOUND");
                        mediaMenu();
                    }
                    Database.close(connection);
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    mediaMenu();
                }
                break;
            case "upload":
                System.out.print("║ [↪] PODCAST NAME ⇒ ");
                String name = input.nextLine();
                System.out.print("║ [#] FILE NAME ⇒ ");
                String path = input.nextLine();
                query = "INSERT INTO Media (name, author, type, path) VALUES (?, ?, ?, ?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, this.username);
                    statement.setString(3, "podcast");
                    statement.setString(4, "src/main/resources/podcasts/"+path+".mp3");
                    statement.executeUpdate();
                    System.out.println("║ [✓] PODCAST UPLOADED");
                    Database.close(connection);
                    mediaMenu();
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());

                    mediaMenu();
                }
                break;
            case "delete":
                System.out.print("║ [↪] ID ⇒ ");
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
                            statement.setInt(1, id);
                            statement.executeUpdate();
                            System.out.println("║ [✓] PODCAST DELETED");
                            Database.close(connection);
                        } catch (SQLException e) {
                            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                        }
                        mediaMenu();
                    } else {
                        System.out.println("║ [!] YOU CAN ONLY DELETE YOUR OWN PODCASTS");
                        mediaMenu();
                    }
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    mediaMenu();
                }
                break;
            case "edit":
                System.out.print("║ [↪] ID ⇒ ");
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
                        System.out.print("║ [↪] PODCAST NAME ⇒ ");
                        name = input.nextLine();
                        query = "UPDATE Media SET name = ?, host = ? WHERE id = ?";
                        try {
                            statement = connection.prepareStatement(query);
                            statement.setString(1, name);
                            statement.setString(2, this.username);
                            statement.setInt(3, mediaID);
                            statement.executeUpdate();
                            System.out.println("║ [✓] PODCAST UPDATED");
                            Database.close(connection);
                        } catch (SQLException e) {
                            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                        }
                        mediaMenu();
                    } else {
                        System.out.println("║ [!] YOU CAN ONLY EDIT YOUR OWN PODCASTS");
                        mediaMenu();
                    }
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    mediaMenu();
                }
                break;
            case "back":
                mainMenu();
                break;
            default:
                System.out.println("║ [!] INVALID COMMAND");
                mediaMenu();
                break;
        }
    }

}