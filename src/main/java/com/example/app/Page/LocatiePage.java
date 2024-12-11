package com.example.app.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class LocatiePage {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/GroeiTipsPage.fxml"));
            Parent root = loader.load();

            // Get the current stage (from the goBackButton's scene)
            Stage stage = (Stage) goBackButton.getScene().getWindow();

            // Set the new scene to the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Set the stage title
            stage.setTitle("My Application");

            // Show the stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}