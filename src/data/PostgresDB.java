package data;

import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private static PostgresDB instance;
    private String host;
    private String username;
    private String password;
    private String dnName;
    private Connection connection;

    private PostgresDB(String host, String username, String password, String dnName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dnName = dnName;
    }

    public static synchronized PostgresDB getInstance(String host, String username, String password, String dnName) {
        if (instance == null) {
            instance = new PostgresDB(host, username, password, dnName);
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        String connectionUrl = host + "/" + dnName;
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(connectionUrl, username, password);
            return connection;
        } catch (Exception e) {
            System.out.println("Failed to connect to PostgreSQL database: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}
