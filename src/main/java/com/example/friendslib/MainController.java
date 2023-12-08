package com.example.friendslib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
    private ClientApp clientApp;

    public void setClientApp(ClientApp clientApp) {
        this.clientApp = clientApp;
    }


    @FXML
    void handleAddBook(ActionEvent event) throws Exception {
//        clientApp.showAddBookScene();
        clientApp.showWelcomeScene();
    }

    @FXML
    void handleViewAllBooks(ActionEvent event) {
        // Implement logic to view all books using clientApp.getLibraryClient()
    }


}