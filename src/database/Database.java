package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connect() {

        String url = "jdbc:sqlite:src/database/Musicalize.db";
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
}
