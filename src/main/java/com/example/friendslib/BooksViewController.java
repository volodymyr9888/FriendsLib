package com.example.friendslib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class BooksViewController {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> ownerColumn;

    @FXML
    private TextField bookIdField;

    private DatabaseHandler databaseHandler;
    private User currentUser; // Assuming you have a way to set the current user in this controller

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    private void initialize() {
        // Initialize columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));

        // Initialize the DatabaseHandler
        DatabaseHandler databaseHandler = new DatabaseHandler();
        this.setDatabaseHandler(databaseHandler);
        // Load books from the database
        loadBooks();
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    private void loadBooks() {
        if (databaseHandler != null) {
            List<Book> books = databaseHandler.getAllBooks();
            ObservableList<Book> bookObservableList = FXCollections.observableArrayList(books);
            booksTable.setItems(bookObservableList);
        }
    }

    @FXML
    private void handleDeleteBook(ActionEvent event) {
        // Get the value from the bookIdField TextField
        String bookIdText = bookIdField.getText();

        // Perform your logic with the bookIdText, e.g., convert it to an integer
        try {
            int bookId = Integer.parseInt(bookIdText);

            // Now you can use the bookId in your delete logic
            boolean deleted = DatabaseHandler.deleteBookById(bookId);

            if (deleted) {
                // Handle the case where the book was successfully deleted
                System.out.println("Book deleted successfully");
            } else {
                // Handle the case where the book deletion failed
                System.out.println("Failed to delete book");
            }

        } catch (NumberFormatException e) {
            // Handle the case where the entered value is not a valid integer
            System.err.println("Invalid book ID format");
        }
    }
}