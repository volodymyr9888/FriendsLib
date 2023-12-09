package com.example.friendslib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class AdminAddBookController {
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
    private User currentUser; // Assuming you have a way to set the current user in this controller

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
            boolean success = DatabaseHandler.addBookToUser(Integer.parseInt(assigned), addedBook.getId());

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


}
