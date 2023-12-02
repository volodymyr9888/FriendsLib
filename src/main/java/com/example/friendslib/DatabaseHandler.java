package com.example.friendslib;

import java.sql.*;
//import mysql

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/friendslib";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static boolean createUsersTable() {
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public static boolean registerUser(User user) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Begin transaction

            // Insert user information
            String insertUserQuery = "INSERT INTO users (full_name, email, password, created_at, modified_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                userStatement.setString(1, user.getFullName());
                userStatement.setString(2, user.getEmail());
                userStatement.setString(3, user.getPassword());
                userStatement.setObject(4, user.getCreatedAt());
                userStatement.setObject(5, user.getModifiedAt());

                userStatement.executeUpdate();

                // Retrieve the auto-generated user ID
                try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Failed to get the user ID after registration.");
                    }
                }
            }

            // Insert user_roles information to assign the default role
            String insertUserRoleQuery = "INSERT INTO user_roles (user_id, role_id, created_at, modified_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement userRoleStatement = connection.prepareStatement(insertUserRoleQuery)) {
                // Assuming 'user' role has an ID of 1, adjust accordingly
                userRoleStatement.setInt(1, user.getId());
                userRoleStatement.setInt(2, 1); // Assuming 'user' role has an ID of 1, adjust accordingly
                userRoleStatement.setObject(3, user.getCreatedAt());
                userRoleStatement.setObject(4, user.getModifiedAt());

                userRoleStatement.executeUpdate();
            }

            connection.commit(); // Commit the transaction
            connection.setAutoCommit(true); // Reset auto-commit mode

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
