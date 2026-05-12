package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/enrollment_system";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}