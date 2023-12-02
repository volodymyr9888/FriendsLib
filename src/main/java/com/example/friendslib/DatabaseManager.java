package com.example.friendslib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Initialize connection (replace URL, username, and password)
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fiendslib", "root", "password");
        }
        return connection;
    }
}