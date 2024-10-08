package client;

import database.Database;

import java.sql.*;
import java.util.Scanner;

public class User {

    private int id;
    private String username;
    private String password;
    protected String type;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = "user";
    }

    public static void LoginMenu() {

        Scanner input = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║ ▆▃▅█▇█▅▃▂  MUSICALIZE  ▂▃▅█▇█▅▃▆ ║");
        System.out.println("║                                  ║");
        System.out.println("╠═ [#] login                       ║");
        System.out.println("║                                  ║");
        System.out.println("╠═ [#] register                    ║");
        System.out.println("║                                  ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();
        System.out.println("TYPE A COMMAND TO CONTINUE");
        System.out.print("[#] ");

        String command = input.nextLine().toLowerCase();

        System.out.println();

        while (!command.equals("login") && !command.equals("register")) {
            System.out.println("[!] INVALID COMMAND");
            System.out.print("[#] ");
            command = input.nextLine();
        }

        if (command.equals("login")) {
            login();
        } else {
            register();
        }
    }

    public static void login() {

        Scanner input = new Scanner(System.in);

        System.out.print("USERNAME: ");
        String username = input.nextLine();
        System.out.print("PASSWORD: ");
        String password = input.nextLine();

        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        Connection connection = Database.connect();
        try {
            PreparedStatement Statement = connection.prepareStatement(query);
            Statement.setString(1, username);
            Statement.setString(2, password);
            ResultSet result = Statement.executeQuery();
            if (result.next()) {
                System.out.println("[✓] LOGIN SUCCESSFUL");
                if (result.getString("type").equals("user")) {
                    User currentUser = new User(result.getInt("id"), result.getString("username"), result.getString("password"));
                    currentUser.mainMenu();
                } else {
                    Artist currentArtist = new Artist(result.getInt("id"), result.getString("username"), result.getString("password"));
                    currentArtist.mainMenu();
                }
            } else {
                System.out.println("[!] LOGIN FAILED");
                LoginMenu();
            }
        } catch (SQLException e) {
            System.out.println("[!] QUERY ERROR: " + e.getMessage());
        }

    }

    public static void register() {

        Scanner input = new Scanner(System.in);

        System.out.print("USERNAME: ");
        String username = input.nextLine();
        System.out.print("PASSWORD: ");
        String password = input.nextLine();
        System.out.print("TYPE [ USER | ARTIST ]: ");
        String type = input.nextLine().toLowerCase();
        while (!type.equals("user") && !type.equals("artist")) {
            System.out.println("[!] INVALID TYPE");
            type = input.nextLine().toLowerCase();
        }

        String query = "INSERT INTO Users (username, password, type) VALUES (?, ?, ?)";
        Connection connection = Database.connect();
        try {
            PreparedStatement Statement = connection.prepareStatement(query);
            Statement.setString(1, username);
            Statement.setString(2, password);
            Statement.setString(3, type);
            Statement.executeUpdate();
            System.out.println("[✓] USER REGISTERED");
            login();
        } catch (SQLException e) {
            System.out.println("[!] QUERY ERROR: " + e.getMessage());
        }

    }

    public void mainMenu() {
        // TO-DO
    }

}
