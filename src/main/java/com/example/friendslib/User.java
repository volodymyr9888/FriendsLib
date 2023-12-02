package com.example.friendslib;

import java.time.LocalDateTime;
import java.util.List;

public class User {

    private int id;
    private String fullName;
    private String email;
    private String password;
    private List<Book> books; // Assuming a user can have multiple books
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public User(String fullName, String email, String password, List<Book> books, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.books = books;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
