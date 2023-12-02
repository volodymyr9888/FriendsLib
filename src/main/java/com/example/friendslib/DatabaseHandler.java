package com.example.friendslib;

import java.sql.*;

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/friendslib";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static void createUsersTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS users ("
                             + "id INT PRIMARY KEY AUTO_INCREMENT,"
                             + "full_name TEXT,"
                             + "email TEXT,"
                             + "password TEXT,"
                             + "created_at DATETIME,"
                             + "modified_at DATETIME)"
             )) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerUser(String fullName, String email, String password, int bookId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO users (full_name, email, password, book_id, created_at, modified_at) VALUES (?, ?, ?, ?, NOW(), NOW())"
             )) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, bookId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerUser(String fullName, String email, String password) {
        try (Connection connection = getConnection()) {
            // Insert user into the users table
            try (PreparedStatement userStatement = connection.prepareStatement(
                    "INSERT INTO users (full_name, email, password, created_at, modified_at) VALUES (?, ?, ?, NOW(), NOW())",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                userStatement.setString(1, fullName);
                userStatement.setString(2, email);
                userStatement.setString(3, password);
                userStatement.executeUpdate();

                // Get the generated user_id
                ResultSet generatedKeys = userStatement.getGeneratedKeys();
                int userId = -1;
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }

                // You can add more logic here to handle the case where userId is -1

                // Associate the user with books (optional)
                associateUserWithBooks(userId, null /* or book_id */);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void associateUserWithBooks(int userId, Integer bookId) {
        try (Connection connection = getConnection();
             PreparedStatement userBooksStatement = connection.prepareStatement(
                     "INSERT INTO user_books (user_id, book_id, created_at, modified_at) VALUES (?, ?, NOW(), NOW())"
             )) {
            userBooksStatement.setInt(1, userId);
            if (bookId != null) {
                userBooksStatement.setInt(2, bookId);
            } else {
                userBooksStatement.setNull(2, Types.INTEGER);
            }
            userBooksStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
