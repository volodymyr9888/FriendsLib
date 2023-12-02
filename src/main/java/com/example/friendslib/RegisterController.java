// RegisterController.java
package com.example.friendslib;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleRegister() {
        // Code to handle registration
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        // Perform registration logic here
    }
}