import java.sql.*;

public class Database {
    public static Connection connect() {
        String url = "jdbc:sqlite:src/main/resources/Musicalize.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            return connection;
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE CONNECTION ERROR: " + e.getMessage());
            return null;
        }
    }

    public static void search() {

    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE CONNECTION ERROR: " + e.getMessage());
        }
    }
}
