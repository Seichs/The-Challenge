package com.example.app.Page;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;

public class LoginController {

    @FXML
    private Pane rootPane;

    @FXML
    private TextField usernameField;  // For email

    @FXML
    private PasswordField passwordField;  // For password

    private static final String DB_URL = "jdbc:mysql://localhost:3306/thechallenge";  // Database URL
    private static final String DB_USER = "root";  // Replace with your MySQL username
    private static final String DB_PASSWORD = "root";  // Replace with your MySQL password

    // Method to handle login
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();  // Get email
        String password = passwordField.getText();  // Get password

        // Check if email or password fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            showErrorMessage("Error", "Both fields are required!");
            return;
        }

        // Validate the login credentials
        if (validateLogin(username, password)) {
            // If valid, transition to HomePage
            switchToHomePage();  // Method to switch to the HomePage
        } else {
            // If invalid, show an error message
            showErrorMessage("Invalid Login", "Invalid email or password.");
        }
    }

    // Validate login credentials against the database
    private boolean validateLogin(String email, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM Klant WHERE Email = ? AND Wachtwoord = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();  // If a row is returned, the login is valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // In case of an error, consider the login as invalid
        }
    }

    // Show an error message in a pop-up alert
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to switch to HomePage after successful login
    private void switchToHomePage() {
        try {
            // Load the HomePage FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
            Parent homePageRoot = loader.load();

            // Get the current stage (window)
            Stage currentStage = (Stage) rootPane.getScene().getWindow();

            // Set the HomePage scene
            Scene homePageScene = new Scene(homePageRoot);
            currentStage.setScene(homePageScene);  // Change scene to HomePage
            currentStage.setTitle("Home Page - SmartGreenHouse");  // Set the title of the new page

        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Error", "Failed to load the HomePage.");
        }
    }

    // Handle the registration button click
    @FXML
    private void handleRegister() {
        try {
            // Load the RegisterView FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/RegisterView.fxml"));
            Parent registerRoot = loader.load();

            // Get the current stage (window) and set the new scene
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            Scene registerScene = new Scene(registerRoot);

            // Add the translation animation for Register button
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), rootPane);
            slideOut.setFromX(0);
            slideOut.setToX(-rootPane.getWidth()); // Slide out to the left

            // After the animation, apply the new scene (RegisterView)
            slideOut.setOnFinished(event -> {
                currentStage.setScene(registerScene);  // Change to register scene
                currentStage.setTitle("Register - SmartGreenHouse");  // Set the title of the register page
            });

            slideOut.play();  // Start the slide-out animation

        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Error", "Failed to load the registration form.");
        }
    }

    // Add event handler for the Enter key press for login
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            handleLogin();  // Trigger login if Enter is pressed
        }
    }

    // Add event handler for the Enter key press for register (when on the Register page)
    @FXML
    private void handleKeyPressRegister(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            handleRegister();  // Trigger register if Enter is pressed
        }
    }
}
