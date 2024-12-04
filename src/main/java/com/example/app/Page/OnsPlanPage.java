package com.example.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class OnsPlanPage {

    @FXML
    private Button goToOnsPlanButton; // Updated button ID for OnsPlanPage navigation

    @FXML
    private void initialize() {
        // Initialize the action for the goToOnsPlanButton
        goToOnsPlanButton.setOnAction(event -> goToOnsPlanPage());
    }

    private void goToOnsPlanPage() {
        try {
            // Load the OnsPlanPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/OnsPlanPage.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) goToOnsPlanButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Maximize the window manually to avoid OS issues
            javafx.stage.Screen screen = javafx.stage.Screen.getPrimary();
            javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();

            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());

            // Set the stage title
            stage.setTitle("Ons Plan Page");

            // Show the stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
