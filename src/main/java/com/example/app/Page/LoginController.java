package com.example.app.Page;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.FadeTransition;

import java.io.IOException;

public class LoginController {

    @FXML
    private Pane rootPane;

    @FXML
    private TextField usernameField;  // Assuming there is a TextField for username

    @FXML
    private PasswordField passwordField;  // Assuming there is a PasswordField for password

    @FXML
    private void handleLogin() {
        // Get username and password from the fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate username and password
        if ("admin".equals(username) && "admin".equals(password)) {
            // If valid, transition to HomePage.fxml with animation
            try {
                // Load the HomePage FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
                Parent homeRoot = loader.load();

                // Fade out the current Login pane
                FadeTransition fadeOut = new FadeTransition(Duration.millis(500), rootPane);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                fadeOut.setOnFinished(event -> {
                    // Create a new Scene for the HomePage
                    Scene homeScene = new Scene(homeRoot);

                    // Get the current stage (window) and set the new scene
                    Stage currentStage = (Stage) rootPane.getScene().getWindow();
                    currentStage.setScene(homeScene);
                    currentStage.setTitle("Home - Smart Novme");

                    // Apply a fade-in animation to the HomePage
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), homeRoot);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                });

                fadeOut.play();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Handle invalid login
            System.out.println("Invalid username or password!");
        }
    }

    @FXML
    private void handleRegister() {
        try {
            // Load the RegisterView FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/RegisterView.fxml"));
            Parent registerRoot = loader.load();

            // Set up the Register view below the visible area
            rootPane.getChildren().add(registerRoot);
            registerRoot.setTranslateY(rootPane.getHeight());

            // Animate the current view out and the new view in
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), rootPane.getChildren().get(0));
            slideOut.setFromY(0);
            slideOut.setToY(-rootPane.getHeight());

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), registerRoot);
            slideIn.setFromY(rootPane.getHeight());
            slideIn.setToY(0);

            slideOut.setOnFinished(event -> rootPane.getChildren().remove(0)); // Remove the old view after the animation
            slideOut.play();
            slideIn.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Pane loginPane;

    @FXML
    private Pane registerPane;

    public void switchToRegister() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), loginPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            loadRegisterView();
        });
        fadeOut.play();
    }

    private void loadRegisterView() {
        registerPane.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), registerPane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
}
