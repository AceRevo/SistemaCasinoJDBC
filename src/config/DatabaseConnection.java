package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_DATABASE = "gestion_deportiva";
    private static final String DEFAULT_USER = "root";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String host = setting("DB_HOST", DEFAULT_HOST);
        String port = setting("DB_PORT", DEFAULT_PORT);
        String database = setting("DB_NAME", DEFAULT_DATABASE);
        String user = setting("DB_USER", DEFAULT_USER);
        String password = setting("DB_PASSWORD", "");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(url, user, password);
    }

    private static String setting(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
