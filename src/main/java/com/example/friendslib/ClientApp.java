package com.example.friendslib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientApp extends Application {
    private static Stage primaryStage;
    private static SceneManager sceneManager;
    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.sceneManager = new SceneManager(primaryStage);

        // Connect to the server
        connectToServer();

        // Pass the SceneManager instance to the controllers
        WelcomeController welcomeController = loadScene("WelcomeView.fxml");
        welcomeController.setSceneManager(sceneManager);
        System.out.println("scene manager from = " + this.getClass().getName() + " " + sceneManager);
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345); // Use the server's IP and port
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to the server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendDataToServer(Object data) {
        try {
            // Send data to the server
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object receiveDataFromServer() {
        try {
            // Receive data from the server
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showWelcomeScene() {
        sceneManager.switchScene("WelcomeView.fxml");
    }

    private <T> T loadScene(String fxmlFileName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/friendslib/" + fxmlFileName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FriendsLib App");
        primaryStage.show();

        return loader.getController();
    }

    public static class SceneManager {
        private final Stage primaryStage;

        public SceneManager(Stage primaryStage) {
            this.primaryStage = primaryStage;
        }

        public void switchScene(String fxmlFileName) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/friendslib/" + fxmlFileName));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("FriendsLib App");
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
