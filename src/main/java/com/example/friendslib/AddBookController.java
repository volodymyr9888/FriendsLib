package com.example.friendslib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddBookController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    private ClientApp clientApp;

    public void setClientApp(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    @FXML
    void handleAddBook(ActionEvent event) {
        // Implement logic to add a book using clientApp.getLibraryClient()
    }

    @FXML
    void handleBack(ActionEvent event) throws Exception {
        clientApp.showWelcomeScene();
    }
}
