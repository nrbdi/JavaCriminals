package data;

import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private String host;
    private String username;
    private String password;
    private String dnName;

    private Connection connection;

    public PostgresDB(String host, String username, String password, String dnName) {
        setHost(host);
        setUsername(username);
        setPassword(password);
        setDnName(dnName);
    }

    public String getDnName() {
        return dnName;
    }

    public void setDnName(String dnName) {
        this.dnName = dnName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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
            }catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}
