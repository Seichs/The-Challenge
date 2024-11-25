package com.example.app;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

public class KassenJava {

    @FXML
    private Button goBackButton;

    @FXML
    private void initialize() {
        // Stel de actie in voor de Go Back-knop
        goBackButton.setOnAction(event -> goBackToHomePage(event));
    }

    private void goBackToHomePage(ActionEvent event) {
        try {
            // Laad de HomePage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/HomePage.fxml"));
            Parent root = loader.load();

            // Verkrijg de huidige stage van de goBackButton
            Stage stage = (Stage) goBackButton.getScene().getWindow();

            // Zet de nieuwe scene voor de stage
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Maximaliseer het venster en vergrendel de grootte
            StageUtils.maximizeAndLock(stage);

            // Zet de titel van het venster
            stage.setTitle("Home Page");

            // Toon het venster
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
