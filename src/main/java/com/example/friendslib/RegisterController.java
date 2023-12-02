// RegisterController.java
package com.example.friendslib;


import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class RegisterController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleRegister() {
        // Ensure the users table is created
        DatabaseHandler.createUsersTable();

        // Get user input
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Perform user registration
        User newUser = new User(fullName, username, password, null, LocalDateTime.now(), LocalDateTime.now());
        boolean registrationSuccess = DatabaseHandler.registerUser(newUser);

        if (registrationSuccess) {
            System.out.println("User registration successful!");
            // Optionally, you can add logic to navigate to another view after registration
        } else {
            System.out.println("User registration failed.");
            // Handle the case where registration fails (e.g., duplicate username, database error, etc.)
        }
    }
}