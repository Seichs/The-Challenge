package com.example.app.Page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        showLoginView(primaryStage);
    }

    public static void showLoginView(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/app/PageUIDesign/LoginView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Login - SmartGreenHouse");
        stage.show();
    }

    public static void showMainView(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("SmartGreenHouse");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
