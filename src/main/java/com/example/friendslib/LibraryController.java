package com.example.friendslib;

// WelcomeController.java

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryController {

    @FXML
    private Label errorLabel;

    private ClientApp.SceneManager sceneManager;

    private User currentUser; // Assuming you have a way to set the current user in this controller

    public void setSceneManager(ClientApp.SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    private void handleAddBook(ActionEvent event) {

        if (currentUser != null) {
            // Successful login, navigate to the next scene
            System.out.println("Successful switched to add book scene");
            loadScene("AddBookScene.fxml", event, currentUser);
            // ...
        } else {
            // Invalid credentials, show an error message
            showError("Invalid credentials");
            System.out.println("Invalid credentials");
            // ...
        }


//        // Switch to the Login view
//        if (sceneManager != null) {
//            sceneManager.switchScene("AddBookView.fxml");
//        }
    }

    @FXML
    private void handleShowBooks(ActionEvent event) {

        if (currentUser != null) {
            // Successful login, navigate to the next scene
            System.out.println("Successful switched to show books scene");
            loadScene("BooksView.fxml", event, currentUser);
            // ...
        } else {
            // Invalid credentials, show an error message
            showError("Invalid credentials");
            System.out.println("Invalid credentials");
            // ...
        }

        /*// Switch to the Register view
        if (sceneManager != null) {
            sceneManager.switchScene("BooksView.fxml");
        }*/
    }

    @FXML
    private void handleAdminShowBooks(ActionEvent event) {
        System.out.println("current User = " + currentUser);
        if (currentUser != null) {
            // Successful login, navigate to the next scene
            System.out.println("Successful switched to show books scene");
            loadScene("AdminBooksView.fxml", event, currentUser);
            // ...
        } else {
            // Invalid credentials, show an error message
            showError("Invalid credentials");
            System.out.println("Invalid credentials");
            // ...
        }
    }

    @FXML
    private void handleAdminRegisterUser(ActionEvent event) {
            System.out.println("Successful switched to register user scene");
            loadScene("RegisterUser.fxml", event);
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    private void loadScene(String fxmlFileName, ActionEvent event, User authenticatedUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            // Initialize the DatabaseHandler
            DatabaseHandler databaseHandler = new DatabaseHandler();

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

            // If the controller is an instance of AddBookController, set the currentUser
            if (controller instanceof BooksViewController) {
                ((BooksViewController) controller).setDatabaseHandler(databaseHandler);
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

    private void loadScene(String fxmlFileName, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            // Initialize the DatabaseHandler
            DatabaseHandler databaseHandler = new DatabaseHandler();

            // Get the controller of the loaded FXML file
            Object controller = loader.getController();

            // If the controller is an instance of AddBookController, set the currentUser
            if (controller instanceof BooksViewController) {
                ((BooksViewController) controller).setDatabaseHandler(databaseHandler);
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

