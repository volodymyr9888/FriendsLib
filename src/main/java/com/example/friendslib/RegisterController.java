// RegisterController.java
package com.example.friendslib;


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
import java.time.LocalDateTime;

public class RegisterController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleRegister(ActionEvent event) {
        // Ensure the users table is created
        DatabaseHandler.createUsersTable();

        // Get user input
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (password.isEmpty()) {
            showError("Password, cannot be empty");
            return;
        }

        if (fullName.isEmpty() ) {
            showError("Fullname cannot be empty");
            return;
        }

        if (username.isEmpty()) {
            showError("Username cannot be empty");
            return;
        }

        // Perform user registration
        User newUser = new User(fullName, username, password, LocalDateTime.now(), LocalDateTime.now());
        boolean registrationSuccess = DatabaseHandler.registerUser(newUser);

        if (registrationSuccess) {
            System.out.println("User registration successful!");
            loadScene("AddBookScene.fxml", event, newUser);
            // Optionally, you can add logic to navigate to another view after registration
        } else {
            System.out.println("User registration failed.");
            // Handle the case where registration fails (e.g., duplicate username, database error, etc.)
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    public void reloadScene() {
        // Load the same FXML file to reset the scene
        loadScene("RegisterView.fxml", null, null);
        // Clear the error message
        errorLabel.setText("");
    }

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