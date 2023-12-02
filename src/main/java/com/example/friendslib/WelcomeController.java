package com.example.friendslib;

// WelcomeController.java

import javafx.fxml.FXML;

public class WelcomeController {

    private ClientApp.SceneManager sceneManager;

    public void setSceneManager(ClientApp.SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void handleLogin() {
        // Switch to the Login view
        System.out.println("test");
        if (sceneManager != null) {
            sceneManager.switchScene("LoginView.fxml");
        }
    }

    @FXML
    private void handleRegister() {
        // Switch to the Register view
        if (sceneManager != null) {
            sceneManager.switchScene("RegisterView.fxml");
        }
    }
}
