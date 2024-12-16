package com.example.app.Page;

import com.example.app.util.PasswordUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/thechallenge";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    /**
     * Verwerk de login actie.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Fout", "Vul zowel je gebruikersnaam als wachtwoord in.", Alert.AlertType.ERROR);
            return;
        }

        if (validateLogin(username, password)) {
            // Als de login succesvol is, ga naar het homepage-scherm
            switchToHomepage();
        } else {
            showAlert("Fout", "Ongeldige gebruikersnaam of wachtwoord.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Verwerk het inloggen met de Enter-toets.
     */
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

    /**
     * Wissel naar het registratie-scherm.
     */
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/RegisterView.fxml"));
            Parent registerRoot = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.setTitle("Register - SmartGreenHouse");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een probleem opgetreden bij het laden van het registratie-scherm.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Valideer de login door het wachtwoord te vergelijken met de database.
     */
    private boolean validateLogin(String username, String password) {
        String query = "SELECT Wachtwoord, Salt FROM Klant WHERE Username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("Wachtwoord");
                String salt = rs.getString("Salt");
                // Valideer wachtwoord door het te hashen met de opgeslagen salt
                return PasswordUtils.validatePassword(password, salt, storedHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
     * Wissel naar het homepage-scherm na succesvolle login.
     */
    private void switchToHomepage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/Homepage.fxml"));
            Parent homepageRoot = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(homepageRoot));
            stage.setTitle("Homepage - SmartGreenHouse");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een probleem opgetreden bij het laden van het homepage-scherm.", Alert.AlertType.ERROR);
        }
    }
}
