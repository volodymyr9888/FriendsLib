package com.example.friendslib;

// WelcomeController.java

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class WelcomeController {

    private ClientApp.SceneManager sceneManager;

    public void setSceneManager(ClientApp.SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void handleLogin() throws IOException {
        // Switch to the Login view
        LoginController loginController = new LoginController();
        loginController.setSceneManager(sceneManager);
        System.out.println("scene manager from = " + this.getClass().getName() + " " + sceneManager);
        if (sceneManager != null) {
            sceneManager.switchScene("LoginView.fxml");
        }
    }

    @FXML
    private void handleRegister() {
        // Switch to the Register view
        RegisterController registerController = new RegisterController();
        registerController.setSceneManager(sceneManager);
        System.out.println("scene manager from = " + this.getClass().getName() + " " + sceneManager);
        if (sceneManager != null) {
            sceneManager.switchScene("RegisterView.fxml");
        }
    }
}
