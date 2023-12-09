package com.example.friendslib;

import java.time.LocalDateTime;

public class Book {

    private int id;
    private String title;
    private String author;
    private String year;
    private int wasAddedBy; // User ID who added the book
    private int ownedBy; // User ID who owns the book
    private LocalDateTime createdAt;

    private String assignet;
    private LocalDateTime modifiedAt;

    private String ownerName;

    public Book(String title, String author, String year, int wasAddedBy, int ownedBy, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.wasAddedBy = wasAddedBy;
        this.ownedBy = ownedBy;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Book(int id, String title, String author, String year, int wasAddedBy, int ownedBy, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.wasAddedBy = wasAddedBy;
        this.ownedBy = ownedBy;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
/*    public Book(int id, String title, String author, String year, int assignet, int ownedBy, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.wasAddedBy = wasAddedBy;
        this.assignet = String.valueOf(assignet);
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }*/
    public Book(int id, String title, String author, String year, int owned_by_id, String ownerName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.ownedBy = owned_by_id;
        this.ownerName = ownerName;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getWasAddedBy() {
        return wasAddedBy;
    }

    public void setWasAddedBy(int wasAddedBy) {
        this.wasAddedBy = wasAddedBy;
    }

    public int getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(int ownedBy) {
        this.ownedBy = ownedBy;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwnerName() {
        return ownerName;
    }
}

