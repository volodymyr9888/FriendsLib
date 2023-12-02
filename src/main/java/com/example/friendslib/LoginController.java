package com.example.friendslib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private ClientApp clientApp;

    public void setClientApp(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    @FXML
    void handleLogin(ActionEvent event) {
        // Implement login logic using clientApp.getLibraryClient()
    }

    @FXML
    void handleRegister(ActionEvent event) {
        // Implement registration logic using clientApp.getLibraryClient()
    }
}
