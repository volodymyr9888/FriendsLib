package com.example.friendslib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
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
    private TableColumn<Book, Void> actionColumn;

    private DatabaseHandler databaseHandler;
    private User currentUser; // Assuming you have a way to set the current user in this controller


    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        // Update UI based on user role
        updateUI();
    }

    @FXML
    private void initialize() {
        // Initialize columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));

        // Initialize action column with a delete button
        actionColumn.setCellFactory(col -> {
            TableCell<Book, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Book book = getTableView().getItems().get(getIndex());

                        // Check if the current user has the "admin" role
                        if (currentUser != null && currentUser.getRoles().contains("admin")) {
                            deleteButton.setOnAction(event -> deleteBook(book));
                            setGraphic(deleteButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };

            return cell;
        });
        // Initialize the DatabaseHandler
        DatabaseHandler databaseHandler = new DatabaseHandler();
        this.setDatabaseHandler(databaseHandler);
        // Load books from the database
        loadBooks();
    }



    private void loadBooks() {
        if (databaseHandler != null) {
            List<Book> books = databaseHandler.getAllBooks();
            ObservableList<Book> bookObservableList = FXCollections.observableArrayList(books);
            booksTable.setItems(bookObservableList);
        }
    }

    private void deleteBook(Book book) {
        if (databaseHandler != null) {
            databaseHandler.deleteBook(book);
            // Reload the books after deletion
            loadBooks();
        }
    }

    private void updateUI() {
        // Hide the action column if the user is not an admin
        if (currentUser == null || !currentUser.getRoles().contains("admin")) {
            booksTable.getColumns().remove(actionColumn);
        }
    }

    @FXML
    private void handleDeleteBook(ActionEvent event) {}
}