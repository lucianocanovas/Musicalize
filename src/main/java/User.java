import java.sql.*;
import java.util.Scanner;
import Database.Database;

public class User {

    private int id;
    protected String username;
    private String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static void login() {
        Scanner input = new Scanner(System.in);
        System.out.print("USERNAME: ");
        String username = input.nextLine();
        System.out.print("PASSWORD: ");
        String password = input.nextLine();
        Connection connection = Database.connect();
        System.out.println(connection);
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println("LOGIN SUCCESSFUL");
            } else {
                System.out.println("LOGIN FAILED");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    public static void register() {

    }

}