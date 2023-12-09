package com.example.friendslib;

import java.time.LocalDateTime;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String fullName;
    private String username;
    private String password;
    private String hashedPassword;
    private List<Book> books; // Assuming a user can have multiple books
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<Role> roles; // Assuming a user can have multiple roles

    public User(String fullName, String username, String rawPassword, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.fullName = fullName;
        this.username = username;
        this.password = rawPassword;
        this.hashedPassword = hashPassword(rawPassword);
//        this.books = books;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public User() {};


    public User(int id, String fullName, String username, String rawPassword) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = rawPassword;
        this.hashedPassword = hashPassword(rawPassword);
//        this.books = books;
    }

    public User(int id, String fullName, String username, String password, LocalDateTime createdAt, LocalDateTime modifiedAt, List<Role> roles) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.roles = roles;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
