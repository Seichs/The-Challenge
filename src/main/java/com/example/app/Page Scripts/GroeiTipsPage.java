package com.example.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class GroeiTipsPage {

    @FXML
    private Button goBackButton;

    @FXML
    private void initialize() {
        // Initialize the action for the goBackButton
        goBackButton.setOnAction(event -> goBackToHomePage());
    }

    private void goBackToHomePage() {
        try {
            // Load the HomePage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/HomePage.fxml"));
            Parent root = loader.load();

            // Get the current stage (from the goBackButton's scene)
            Stage stage = (Stage) goBackButton.getScene().getWindow();

            // Set the new scene to the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Set the stage title
            stage.setTitle("Groei Tips");

            // Show the stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}