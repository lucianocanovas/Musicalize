import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User {

    protected int id;
    protected String username;
    protected String password;
    protected String type;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = "user";
    }

    // INICIAR SESIÓN
    public static void login() {
        Scanner input = new Scanner(System.in);
        System.out.print("║ [↪] USERNAME ⇒ ");
        String username = input.nextLine();
        System.out.print("║ [↪] PASSWORD ⇒ ");
        String password = input.nextLine();
        Connection connection = Database.connect();
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println("║ [✓] LOGIN SUCCESSFUL");
                switch (result.getString("type")) {
                    case "user":
                        User user = new User(result.getInt("id"), result.getString("username"), result.getString("password"));
                        Database.close(connection);
                        user.mainMenu();
                        break;
                    case "musician":
                        Musician musician = new Musician(result.getInt("id"), result.getString("username"), result.getString("password"));
                        Database.close(connection);
                        musician.mainMenu();
                        break;
                    case "host":
                        Host host = new Host(result.getInt("id"), result.getString("username"), result.getString("password"));
                        Database.close(connection);
                        host.mainMenu();
                        break;
                }
            } else {
                System.out.println("║ [!] LOGIN FAILED");
                Main.main(null);
            }
        } catch (SQLException e) {
            System.out.println("║ [!] ERROR: " + e.getMessage());
        }
    }

    // REGISTRAR USUARIO
    public static void register() {
        Scanner input = new Scanner(System.in);
        System.out.print("║ [↪] USERNAME ⇒ ");
        String username = input.nextLine();
        String password;
        String confirm;
        do {
            System.out.print("║ [↪] PASSWORD ⇒ ");
            password = input.nextLine();
            System.out.print("║ [↪] CONFIRM PASSWORD ⇒ ");
            confirm = input.nextLine();
        } while (!password.equals(confirm) && password.length() < 1);
        System.out.println("║ [◎] ACCOUNT TYPES: USER | MUSICIAN | HOST");
        System.out.print("║ [↪] ACCOUNT TYPE ⇒ ");
        String type = input.nextLine();
        while (!type.equals("user") && !type.equals("musician") && !type.equals("host")) {
            System.out.println("║ [!] INVALID ACCOUNT TYPE");
            System.out.println("║ [◎] ACCOUNT TYPES: USER | ARTIST | MUSICIAN | HOST");
            System.out.print("║ [↪] ACCOUNT TYPE ⇒ ");
            type = input.nextLine();
        }
        Connection connection = Database.connect();
        String query = "INSERT INTO Users (username, password, type) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, type);
            statement.executeUpdate();
            System.out.println("║ [✓] ACCOUNT CREATED");
            Database.close(connection);
            Main.main(null);
        } catch (SQLException e) {
            System.out.println("║ [!] ERROR: " + e.getMessage());
        }
    }

    // MENÚ PRINCIPAL
    public void mainMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("║ MAIN MENU");
        System.out.println("╠═ [~] PROFILE");
        System.out.println("╠═ [~] MEDIA");
        System.out.println("╠═ [~] ALBUMS");
        System.out.println("╠═ [~] PLAYLISTS");
        System.out.println("╠═ [~] LOGOUT");
        System.out.print("║ [#] ⇒ ");
        String command = input.nextLine().toLowerCase();
        switch (command) {
            case "profile":
                profileMenu();
                break;
            case "media":
                mediaMenu();
                break;
            case "albums":
                albumMenu();
                break;
            case "playlists":
                playlistMenu();
                break;
            case "logout":
                Main.main(null);
                break;
            default:
                System.out.println("║ [!] INVALID COMMAND");
                mainMenu();
                break;
        }
    }

    // MENÚ DE PERFIL
    public void profileMenu() {
        Scanner input = new Scanner(System.in);
        Connection connection = Database.connect();
        String query = "SELECT * FROM Users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println("║ PROFILE");
                System.out.println("╠═ [◎] ID: " + result.getInt("id"));
                System.out.println("╠═ [◎] USERNAME: " + result.getString("username"));
                System.out.println("╠═ [◎] PASSWORD: " + result.getString("password"));
                System.out.println("╠═ [◎] TYPE: " + result.getString("type"));
                System.out.println("╠═ [#] EDIT");
                System.out.println("╠═ [#] BACK");
                System.out.print("║ [#] ⇒ ");
                String command = input.nextLine().toLowerCase();
                while (!command.equals("edit") && !command.equals("back")) {
                    System.out.println("[!] INVALID COMMAND");
                    System.out.print("[#] ⇒ ");
                    command = input.nextLine();
                }
                if (command.equals("edit")) {

                    query = "DELETE FROM Users WHERE id = ?";
                    try {
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, this.id);
                        statement.executeUpdate();
                        Database.close(connection);
                        register();
                    } catch (SQLException e) {
                        System.out.println("║ [!] ERROR: " + e.getMessage());
                    }
                } else {
                    mainMenu();
                }
            } else {
                System.out.println("║ [!] USER NOT FOUND");
            }
        } catch (SQLException e) {
            System.out.println("║ [!] ERROR: " + e.getMessage());
        }
    }

    // MENÚ DE MEDIOS
    public void mediaMenu() {
        Scanner input = new Scanner(System.in);
        Media.list();
        System.out.println("║ MEDIA");
        System.out.println("╠═ [~] PLAY");
        System.out.println("╠═ [~] BACK");
        System.out.print("║ [#] ⇒ ");
        String command = input.nextLine().toLowerCase();
        switch (command) {
            case "play":
                System.out.print("║ [↪] ID ⇒ ");
                try {
                    int id = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    mediaMenu();
                }
                input.nextLine();
                Connection connection = Database.connect();
                String query = "SELECT * FROM Media WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        if (result.getString("type").equals("song")) {
                            Song song = new Song(result.getString("path"), result.getString("name"), result.getString("author"));
                            song.play();
                            mediaMenu();
                        } else {
                            Podcast podcast = new Podcast(result.getString("path"), result.getString("name"), result.getString("author"));
                            podcast.play();
                            mediaMenu();
                        }
                    } else {
                        System.out.println("║ [!] MEDIA NOT FOUND");
                        mediaMenu();
                    }
                    Database.close(connection);
                    mediaMenu();
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

    // MENÚ DE ÁLBUMES
    public void albumMenu() {
        Scanner input = new Scanner(System.in);
        Connection connection = Database.connect();
        String query = "SELECT * FROM Album";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                System.out.println("║ [" + result.getInt("id") + "] " + result.getString("name"));
            }
            Database.close(connection);
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
        }
        System.out.println("║ ALBUMS");
        System.out.println("╠═ [~] PLAY");
        System.out.println("╠═ [~] BACK");
        System.out.print("║ [#] ⇒ ");
        String command = input.next().toLowerCase();
        switch (command) {
            case "play":
                System.out.print("║ [↪] ID ⇒ ");
                try {
                    int id = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    albumMenu();
                }
                input.nextLine();
                connection = Database.connect();
                query = "SELECT * FROM Album WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        Album album = new Album(result.getInt("id"), result.getString("name"), result.getInt("author"));
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
            case "back":
                mainMenu();
                break;
            default:
                System.out.println("║ [!] INVALID COMMAND");
                albumMenu();
                break;
        }
    }

    // MENÚ DE LISTAS DE REPRODUCCIÓN
    public void playlistMenu() {
        Scanner input = new Scanner(System.in);
        Playlist.list(this.id);
        System.out.println("║ PLAYLIST");
        System.out.println("╠═ [~] CREATE");
        System.out.println("╠═ [~] DELETE");
        System.out.println("╠═ [~] VIEW");
        System.out.println("╠═ [~] BACK");
        System.out.print("║ [#] ⇒ ");
        String command = input.nextLine().toLowerCase();
        switch (command) {
            case "create":
                System.out.print("║ [↪] NAME ⇒ ");
                String name = input.nextLine();
                Connection connection = Database.connect();
                String query = "INSERT INTO Playlists (name, userID) VALUES (?, ?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setInt(2, this.id);
                    statement.executeUpdate();
                    Database.close(connection);
                    playlistMenu();
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    playlistMenu();
                }
                break;
            case "delete":
                System.out.print("║ [↪] ID ⇒ ");
                try {
                    int id = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    playlistMenu();
                }
                input.nextLine();
                connection = Database.connect();
                query = "DELETE FROM Playlists WHERE id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    Database.close(connection);
                    playlistMenu();
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                    playlistMenu();
                }
                break;
            case "view":
                System.out.print("║ [↪] ID ⇒ ");
                try {
                    int id = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("║ [!] INVALID ID");
                    playlistMenu();
                }
                input.nextLine();
                connection = Database.connect();
                query = "SELECT * FROM Playlists WHERE id = ? AND userID = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    statement.setInt(2, this.id);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        Playlist playlist = new Playlist(result.getString("name"), result.getInt("userID"));
                        Database.close(connection);
                        playlist.view();
                    } else {
                        System.out.println("║ [!] PLAYLIST NOT FOUND");
                        Database.close(connection);
                    }
                } catch (SQLException e) {
                    System.out.println("║ [!] DATABASE ERROR: " + e.getMessage());
                    Database.close(connection);
                }
                playlistMenu();
                break;
            case "back":
                mainMenu();
                break;
            default:
                System.out.println("║ [!] INVALID COMMAND");
                playlistMenu();
                break;
        }
    }
}