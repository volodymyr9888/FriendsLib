package com.example.friendslib;

import java.time.LocalDateTime;

public class Role {

    private int id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Role(int id, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Getters and setters for all fields

    // ... Other fields and methods ...
}