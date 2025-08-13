package com.quizapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quizportal";
    private static final String DB_USER = "root";         // <-- change if needed
    private static final String DB_PASSWORD = "12345"; // <-- change to your DB password

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load driver (optional with newer JDBC, but safe)
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("[DBConnection] ✅ Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("[DBConnection] MySQL JDBC Driver not found.");
                throw new SQLException(e);
            } catch (SQLException e) {
                System.err.println("[DBConnection] Failed to connect to DB: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    // Optionally close connection on app shutdown
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore or log
            }
        }
    }
}
