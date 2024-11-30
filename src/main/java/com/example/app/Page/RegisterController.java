package com.example.app.Page;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class RegisterController {

    @FXML
    private Pane rootPane;

    @FXML
    private Pane registerPane;

    @FXML
    private Pane loginPane;

    @FXML
    private void handleBackToLogin() {
        try {
            // Check if the login pane is already loaded to prevent duplication
            if (rootPane.getChildren().stream().anyMatch(node -> node == loginPane)) {
                return; // Login pane is already present
            }

            // Load the LoginView FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/LoginView.fxml"));
            Parent loginRoot = loader.load();

            // Add the Login view to the rootPane if it's not already there
            rootPane.getChildren().clear(); // Clear any existing children to prevent duplication
            rootPane.getChildren().add(loginRoot);

            // Animate the current register view sliding out
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), registerPane);
            slideOut.setFromY(0);
            slideOut.setToY(rootPane.getHeight()); // Move off-screen below

            // Animate the login view sliding in
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), loginRoot);
            slideIn.setFromY(-rootPane.getHeight());
            slideIn.setToY(0); // Bring to center

            // Play animations
            slideOut.play();
            slideIn.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToLogin() {
        // Fade out the Register pane
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), registerPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Ensure the Register pane is hidden only after the fade-out finishes
        fadeOut.setOnFinished(event -> {
            registerPane.setVisible(false); // Hide Register pane after fade-out completes

            // Reset the opacity of the Register pane for future use
            registerPane.setOpacity(1.0);

            // Check if loginPane is already loaded to prevent duplication
            if (rootPane.getChildren().contains(loginPane)) {
                return; // Login pane is already present
            }

            // Now make the Login pane visible and fade it in
            loginPane.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), loginPane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }
}
