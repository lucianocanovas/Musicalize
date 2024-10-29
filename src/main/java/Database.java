import java.sql.*;

public class Database {

    // METODO PARA CONECTAR A LA BASE DE DATOS
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

    // METODO PARA CERRAR LA CONEXIÓN A LA BASE DE DATOS
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("║ [!] DATABASE CONNECTION ERROR: " + e.getMessage());
        }
    }

}
