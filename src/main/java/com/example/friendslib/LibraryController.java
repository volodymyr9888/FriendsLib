package com.example.friendslib;

// WelcomeController.java

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LibraryController {

    @FXML
    private Label errorLabel;

    private ClientApp.SceneManager sceneManager;

    private User currentUser; // Assuming you have a way to set the current user in this controller

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField yearField;
    @FXML
    private TextField idField;
    @FXML
    private TextField assignedtoField;

    public void setSceneManager(ClientApp.SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    private void handleAdminAddBook(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String year = yearField.getText();
        String assigned = assignedtoField.getText();
        if (title.isEmpty() || author.isEmpty()) {
            // Display an error message or handle the case where title or author is empty
            return;
        }

        // Assuming the DatabaseHandler has methods to add a book and create a record in user_books
        Book addedBook = DatabaseHandler.AdminaddBook(title, author, year, assigned, currentUser.getId());

        if (addedBook != null) {
            // Book added successfully, now associate it with the current user
            boolean success = DatabaseHandler.addBookToUser(currentUser.getId(), addedBook.getId());

            if (success) {
                // Successfully associated the book with the user
                // You might want to show a success message or navigate to another scene
                System.out.println("Book added and associated with the user successfully.");
            } else {
                // Failed to associate the book with the user
                // Handle this case accordingly
                System.out.println("Failed to associate the book with the user.");
            }
        } else {
            // Failed to add the book
            // Handle this case accordingly
            System.out.println("Failed to add the book.");
        }
    }

    @FXML
    private void handleAddBook(ActionEvent event) {

        if (currentUser != null) {
            // Successful login, navigate to the next scene

            System.out.println("Successful switched to add book scene");
            Role adminRole = DatabaseHandler.getRoleById(1);
            List<Role> userRoles =  currentUser.getRoles();
            userRoles.forEach(role -> {
                if (role.equals(adminRole)) {
                    System.out.println("User is admin load scene AdminAddBooks for admin");
//                    LibraryController libraryController = new LibraryController();
//                    libraryController.setSceneManager(sceneManager);
//                    System.out.println("scene manager from = " + this.getClass().getName() + " " + sceneManager);
                    loadScene("AdminAddBookScene.fxml", event, currentUser);
                } else {
                    loadScene("AddBookScene.fxml", event, currentUser);
                }
            });

        } else {
            // Invalid credentials, show an error message
            showError("Invalid credentials");
            System.out.println("Invalid credentials");
        }
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        if (sceneManager != null) {
            sceneManager.switchScene("RegisterView.fxml");
        }
    }
    @FXML
    private void handleShowBooks(ActionEvent event) {

        if (currentUser != null) {
            // Successful login, navigate to the next scene
            System.out.println("Successful switched to show books scene");
            loadScene("BooksView.fxml", event, currentUser);
            // ...
        }

        else {
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
    private void handleAdminRegister(ActionEvent event) {
        if (currentUser != null) {
            // Successful login, navigate to the next scene
            System.out.println("Successful switched to show books scene");
            loadScene("AdminRegisterUserView.fxml", event, currentUser);
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

            if (controller instanceof AdminAddBookController) {
                ((AdminAddBookController) controller).setCurrentUser(authenticatedUser);
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

