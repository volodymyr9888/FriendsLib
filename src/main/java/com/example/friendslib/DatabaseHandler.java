package com.example.friendslib;

import java.sql.*;
import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;

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
                             + "full_name TEXT,username"
                             + " TEXT,"
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

    public static void registerUser(String fullName, String username, String password, int bookId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO users (full_name, username, password, book_id, created_at, modified_at) VALUES (?, ?, ?, ?, NOW(), NOW())"
             )) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, bookId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Updated registerUser method
    public static boolean registerUser(User user) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Begin transaction

            // Check if the user already exists
            if (userExists(connection, user.getUsername())) {
                System.out.println("User with username " + user.getUsername() + " already exists.");
                return false;
            }

            // Insert user information
            String insertUserQuery = "INSERT INTO users (full_name, username, password, created_at, modified_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                userStatement.setString(1, user.getFullName());
                userStatement.setString(2, user.getUsername());
                userStatement.setString(3, user.getHashedPassword());
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

    private static boolean userExists(Connection connection, String username) throws SQLException {
        String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery)) {
            checkUserStatement.setString(1, username);
            try (ResultSet resultSet = checkUserStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
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

    public static User authenticateUser(String username, String password) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Verify the hashed password
                        String hashedPassword = resultSet.getString("password");
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            // Authentication successful, return the User object
                            int userId = resultSet.getInt("id");
                            String fullName = resultSet.getString("full_name");
                            String user_name = resultSet.getString("username");
                            String pass_word = resultSet.getString("password");
                            return new User(userId, fullName, user_name,pass_word);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        // Return null if authentication fails
        return null;
    }

    public static Book addBook(String title, String author, String year, int addedByUserId) {
        ensureTablesExist(); // Ensure that necessary tables exist

        try (Connection connection = getConnection()) {
            // Insert book information
            String insertBookQuery = "INSERT INTO books (title, author, year, was_added_by, owned_by, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement bookStatement = connection.prepareStatement(insertBookQuery, Statement.RETURN_GENERATED_KEYS)) {
                bookStatement.setString(1, title);
                bookStatement.setString(2, author);
                bookStatement.setString(3, year);
                bookStatement.setInt(4, addedByUserId);
                bookStatement.setInt(5, addedByUserId);
                bookStatement.setObject(6, LocalDateTime.now());
                bookStatement.setObject(7, LocalDateTime.now());

                int affectedRows = bookStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Failed to add the book, no rows affected.");
                }

                // Retrieve the auto-generated book ID
                try (ResultSet generatedKeys = bookStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int bookId = generatedKeys.getInt(1);
                        return new Book(bookId, title, author, year, addedByUserId, addedByUserId, LocalDateTime.now(), LocalDateTime.now());
                    } else {
                        throw new SQLException("Failed to get the book ID after insertion.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return null;
    }

    public static boolean addBookToUser(int userId, int bookId) {
        try (Connection connection = getConnection()) {
            // Insert user_books information
            String insertUserBookQuery = "INSERT INTO user_books (user_id, book_id, created_at, modified_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement userBookStatement = connection.prepareStatement(insertUserBookQuery)) {
                userBookStatement.setInt(1, userId);
                userBookStatement.setInt(2, bookId);
                userBookStatement.setObject(3, LocalDateTime.now());
                userBookStatement.setObject(4, LocalDateTime.now());

                int affectedRows = userBookStatement.executeUpdate();

                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return false;
    }

    private static void ensureTablesExist() {
        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            // Check if the books table exists, create it if not
            if (!doesTableExist(metaData, "books")) {
                createBooksTable(connection);
            }

            // Check if the user_books table exists, create it if not
            if (!doesTableExist(metaData, "user_books")) {
                createUserBooksTable(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private static boolean doesTableExist(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (ResultSet resultSet = metaData.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }

    private static void createBooksTable(Connection connection) throws SQLException {
        String createBooksTableQuery = "CREATE TABLE IF NOT EXISTS books ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "title TEXT,"
                + "author TEXT,"
                + "year TEXT,"
                + "was_added_by INT,"
                + "owned_by INT,"
                + "created_at TIMESTAMP,"
                + "modified_at TIMESTAMP,"
                + "FOREIGN KEY (was_added_by) REFERENCES users(id),"
                + "FOREIGN KEY (owned_by) REFERENCES users(id)"
                + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createBooksTableQuery)) {
            preparedStatement.execute();
        }
    }

    private static void createUserBooksTable(Connection connection) throws SQLException {
        String createUserBooksTableQuery = "CREATE TABLE IF NOT EXISTS user_books ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "user_id INT,"
                + "book_id INT,"
                + "created_at TIMESTAMP,"
                + "modified_at TIMESTAMP,"
                + "FOREIGN KEY (user_id) REFERENCES users(id),"
                + "FOREIGN KEY (book_id) REFERENCES books(id)"
                + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createUserBooksTableQuery)) {
            preparedStatement.execute();
        }
    }

}
