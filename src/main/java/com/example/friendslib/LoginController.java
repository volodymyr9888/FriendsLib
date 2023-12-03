package com.example.friendslib;

// LoginController.java

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    /*@FXML
    private void handleLogin() {
        // Code to handle login
        String username = usernameField.getText();
        String password = passwordField.getText();
        // Perform authentication logic here
    }*/

    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (password.isEmpty()) {
            // Password field is empty, show an error message
            showError("Password cannot be empty");
            return;
        }

        User authenticatedUser = DatabaseHandler.authenticateUser(username, password);

        if (authenticatedUser != null) {
            // Successful login, navigate to the next scene
            System.out.println("Successful login");
            loadScene("LibraryView.fxml", event, authenticatedUser);
            // ...
        } else {
            // Invalid credentials, show an error message
            showError("Invalid credentials");
            System.out.println("Invalid credentials");
            // ...
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    public void reloadScene() {
        // Load the same FXML file to reset the scene
        loadScene("LoginScene.fxml", null, null);
        // Clear the error message
        errorLabel.setText("");
    }

    /*private void loadScene(String fxmlFileName, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("FriendsLib App");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }*/

    private void loadScene(String fxmlFileName, ActionEvent event, User authenticatedUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            // Get the controller of the loaded FXML file
            Object controller = loader.getController();

            // If the controller is an instance of AddBookController, set the currentUser
            if (controller instanceof AddBookController) {
                ((AddBookController) controller).setCurrentUser(authenticatedUser);
            }

            // If the controller is an instance of AddBookController, set the currentUser
            if (controller instanceof LibraryController) {
                ((LibraryController) controller).setCurrentUser(authenticatedUser);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("FriendsLib App");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

}
