package Database;
import java.sql.*;

public class Database {
    public static Connection connect() {
        String url = "jdbc:sqlite:Musicalize.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("[✓] DATABASE CONNECTION READY");
            return connection;
        } catch (SQLException e) {
            System.out.println("[!] DATABASE CONNECTION ERROR: " + e.getMessage());
            return null;
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
            System.out.println("[✓] DATABASE CONNECTION CLOSED");
        } catch (SQLException e) {
            System.out.println("[!] DATABASE CONNECTION ERROR: " + e.getMessage());
        }
    }
}
