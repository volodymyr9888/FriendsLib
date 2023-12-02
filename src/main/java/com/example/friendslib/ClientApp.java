package com.example.friendslib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApp extends Application {
    private Stage primaryStage;
    private LibraryClientImpl libraryClient;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        libraryClient = new LibraryClientImpl();

        showLoginScene();
    }

    public void showLoginScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library Management System - Login");
        primaryStage.show();

        LoginController loginController = loader.getController();
        loginController.setClientApp(this);
    }

    public void showMainScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library Management System - Main");
        primaryStage.show();

        MainController mainController = loader.getController();
        mainController.setClientApp(this);
    }

    public void showAddBookScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBookScene.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library Management System - Add Book");
        primaryStage.show();

        AddBookController addBookController = loader.getController();
        addBookController.setClientApp(this);
    }

    // Other methods for handling various scenes and actions

    public LibraryClientImpl getLibraryClient() {
        return libraryClient;
    }
}
