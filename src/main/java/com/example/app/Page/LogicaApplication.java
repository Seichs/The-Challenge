package com.example.app.Page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class LogicaApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML for the Home Page
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));

        // Set the Scene
        Scene scene = new Scene(root);

        // Set the stage with the scene
        stage.setScene(scene);

        // Set the stage title
        stage.setTitle("Home Page");

        // Show the stage
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
