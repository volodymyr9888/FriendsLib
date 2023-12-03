package com.example.friendslib;

import java.time.LocalDateTime;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class User {

    private int id;
    private String fullName;
    private String username;
    private String password;
    private String hashedPassword;
    private List<Book> books; // Assuming a user can have multiple books
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public User(String fullName, String username, String rawPassword, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.fullName = fullName;
        this.username = username;
        this.password = rawPassword;
        this.hashedPassword = hashPassword(rawPassword);
//        this.books = books;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


    public User(int id, String fullName, String username, String rawPassword) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = rawPassword;
        this.hashedPassword = hashPassword(rawPassword);
//        this.books = books;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    // Update setter to hash the password
    public void setRawPassword(String rawPassword) {
        this.hashedPassword = hashPassword(rawPassword);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    private String hashPassword(String password) {
        // Implement a secure password hashing algorithm (e.g., BCrypt)
        // Return the hashed password

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
