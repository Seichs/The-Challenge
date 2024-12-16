package com.example.app.Page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.app.util.PasswordUtils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private VBox rootPane;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    // Databaseverbinding gegevens
    private static final String DB_URL = "jdbc:mysql://localhost:3306/thechallenge";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    /**
     * Verwerk de registratie en sla de gebruiker op in de database.
     */
    @FXML
    public void handleRegister(ActionEvent event) {
        // Haal formulierdata op
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Controleer of wachtwoorden overeenkomen
        if (!password.equals(confirmPassword)) {
            showAlert("Fout", "Wachtwoorden komen niet overeen.", Alert.AlertType.ERROR);
            return;
        }

        // Genereer een salt voor de gebruiker
        String salt = PasswordUtils.generateSalt();
        // Hash het wachtwoord met de gegenereerde salt
        String hashedPassword = PasswordUtils.hashPassword(password, salt);

        // Voeg de gebruiker toe aan de database
        String query = "INSERT INTO Klant (Username, Email, Wachtwoord, Salt) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);
            stmt.setString(4, salt);
            stmt.executeUpdate();
            showAlert("Succes", "Je bent succesvol geregistreerd!", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een probleem opgetreden bij het registreren.", Alert.AlertType.ERROR);
        }

        // Wissel naar het inlogscherm na succesvolle registratie
        switchToLogin();
    }

    /**
     * Wissel naar het login-scherm.
     */
    @FXML
    public void switchToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/LoginView.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();  // Haal de huidige stage op
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.setTitle("Login - SmartGreenHouse");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een probleem opgetreden bij het laden van het login-scherm.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Toon een waarschuwing of foutmelding.
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Verwerk het inloggen met de Enter-toets.
     */
    @FXML
    public void handleEnterPress() {
        handleRegister(null); // Herbruik de registratiemethode bij Enter-toets
    }
}
