package com.example.friendslib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApp extends Application {

    private static Stage primaryStage;
    private SceneManager sceneManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.sceneManager = new SceneManager(primaryStage);

        // Pass the SceneManager instance to the controllers
        WelcomeController welcomeController = loadScene("WelcomeView.fxml");
        welcomeController.setSceneManager(sceneManager);
        System.out.println("scene manager from = " + this.getClass().getName() + " " + sceneManager);

//        showWelcomeScene();
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
