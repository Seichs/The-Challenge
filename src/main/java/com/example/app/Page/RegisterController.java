package com.example.app.Page;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private Pane rootPane;

    @FXML
    private TextField fullNameField; // Invoer voor volledige naam

    @FXML
    private TextField emailField; // Invoer voor email

    @FXML
    private PasswordField passwordField; // Invoer voor wachtwoord

    @FXML
    private PasswordField confirmPasswordField; // Invoer voor bevestigen wachtwoord

    // Methode die wordt aangeroepen wanneer de "Register" knop wordt ingedrukt
    @FXML
    private void handleRegister() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Valideer de invoer
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Fout", "Alle velden moeten ingevuld zijn.", AlertType.ERROR);
            return;
        }

        // Controleer of wachtwoorden overeenkomen
        if (!password.equals(confirmPassword)) {
            showAlert("Fout", "Wachtwoorden komen niet overeen. Probeer het opnieuw.", AlertType.ERROR);
            return;
        }

        // Voeg de gebruiker toe aan de database
        if (registerUser(fullName, email, password)) {
            showAlert("Succes", "Registratie geslaagd! Je kunt nu inloggen.", AlertType.INFORMATION);
            switchToLogin(); // Ga naar login-scherm na succesvolle registratie
        } else {
            showAlert("Fout", "Dit emailadres is al geregistreerd. Probeer een ander emailadres.", AlertType.ERROR);
        }
    }

    // Methode om de gebruiker toe te voegen aan de database
    private boolean registerUser(String fullName, String email, String password) {
        String url = "jdbc:mysql://localhost:3306/thechallenge"; // Je database-URL
        String dbUsername = "root"; // Je databasegebruikersnaam
        String dbPassword = "root"; // Je databasewachtwoord

        // Eerst controleren of het emailadres al bestaat
        if (emailExists(email)) {
            return false; // Email bestaat al
        }

        String query = "INSERT INTO Klant (FullName, Email, Wachtwoord) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, password); // Gebruik versleuteling voor wachtwoorden in productie!

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Als er rijen zijn toegevoegd, is de registratie geslaagd

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Nieuwe methode om te controleren of het emailadres al bestaat in de database
    private boolean emailExists(String email) {
        String url = "jdbc:mysql://localhost:3306/thechallenge"; // Je database-URL
        String dbUsername = "root"; // Je databasegebruikersnaam
        String dbPassword = "C@ncordia2"; // Je databasewachtwoord

        String query = "SELECT COUNT(*) FROM Klant WHERE Email = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Als er een resultaat is, bestaat het emailadres al
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Methode om een alert te tonen aan de gebruiker
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Methode voor de "Back to Login" knop
    @FXML
    private void handleBackToLogin() {
        switchToLogin(); // Ga naar het login-scherm
    }

    // Methode om naar het login-scherm te schakelen met animatie
    public void switchToLogin() {
        try {
            // Laad de LoginFXML-bestand
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/LoginView.fxml"));
            Parent loginRoot = loader.load();

            // Haal de huidige Stage op
            Stage stage = (Stage) rootPane.getScene().getWindow();

            // Zet de loginRoot in de Scene van de Stage
            Scene loginScene = new Scene(loginRoot);

            // Voeg een TranslateTransition toe om de animatie van het uit het zicht schuiven van de huidige root en in de nieuwe root te laten plaatsvinden
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), rootPane);
            slideOut.setFromY(0);
            slideOut.setToY(-rootPane.getHeight());

            // Na de slideOut animatie, pas de nieuwe Scene aan de Stage toe
            slideOut.setOnFinished(event -> {
                stage.setScene(loginScene); // Zet de nieuwe login scene
                stage.setTitle("Login - SmartGreenHouse"); // Optioneel: Zet de titel
            });

            // Start de slideOut animatie
            slideOut.play();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een fout opgetreden bij het schakelen naar het login-scherm.", AlertType.ERROR);
        }
    }

    // Methode om de registratie uit te voeren wanneer de Enter-toets wordt ingedrukt
    @FXML
    private void handleEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleRegister(); // Roep de registratie methode aan als Enter is ingedrukt
        }
    }
}
