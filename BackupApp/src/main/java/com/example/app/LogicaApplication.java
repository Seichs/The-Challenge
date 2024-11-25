package com.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogicaApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Laad de HomePage.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/app/HomePage.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Maximaliseer het venster en vergrendel de grootte
            StageUtils.maximizeAndLock(stage);

            // Zet de titel voor het venster
            stage.setTitle("Sow Sigma");

            // Toon het venster
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
