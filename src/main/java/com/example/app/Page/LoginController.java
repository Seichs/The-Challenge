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
    private TextField usernameField;  // Voor Username

    @FXML
    private PasswordField passwordField;  // Voor Password

    private static final String DB_URL = "jdbc:mysql://localhost:3306/thechallenge";  // Database URL
    private static final String DB_USER = "root";  // Vervang door je MySQL gebruikersnaam
    private static final String DB_PASSWORD = "root";  // Vervang door je MySQL wachtwoord

    // Methode om in te loggen
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();  // Verkrijg de Username
        String password = passwordField.getText();  // Verkrijg het Wachtwoord

        // Controleer of de velden leeg zijn
        if (username.isEmpty() || password.isEmpty()) {
            showErrorMessage("Error", "Beide velden zijn verplicht!");
            return;
        }

        // Valideer de logingegevens
        if (validateLogin(username, password)) {
            // Als de login succesvol is, ga naar de HomePage
            switchToHomePage();  // Methode om naar de HomePage te schakelen
        } else {
            // Als de login mislukt, toon een foutmelding
            showErrorMessage("Ongeldige Login", "Ongeldige username of wachtwoord.");
        }
    }

    // Valideer de logingegevens met de database
    private boolean validateLogin(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Query om te controleren of de username en het wachtwoord overeenkomen
            String query = "SELECT * FROM Klant WHERE Username = ? AND Wachtwoord = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();  // Als er een rij wordt geretourneerd, is de login geldig
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Bij een fout wordt de login als ongeldig beschouwd
        }
    }

    // Methode om een foutmelding te tonen in een pop-up
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Methode om naar de HomePage te schakelen na succesvolle login
    private void switchToHomePage() {
        try {
            // Laad de HomePage FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
            Parent homePageRoot = loader.load();

            // Verkrijg het huidige stage (venster)
            Stage currentStage = (Stage) rootPane.getScene().getWindow();

            // Stel de nieuwe scene in
            Scene homePageScene = new Scene(homePageRoot);
            currentStage.setScene(homePageScene);  // Verander de scene naar de HomePage
            currentStage.setTitle("Home Page - SmartGreenHouse");  // Stel de titel van de nieuwe pagina in

        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Fout", "Kan de HomePage niet laden.");
        }
    }

    // Methode voor de registratieknop
    @FXML
    private void handleRegister() {
        try {
            // Laad het RegisterView FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/RegisterView.fxml"));
            Parent registerRoot = loader.load();

            // Verkrijg het huidige stage (venster) en stel de nieuwe scene in
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            Scene registerScene = new Scene(registerRoot);

            // Voeg een vertaalanimatie toe voor de Register-knop
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), rootPane);
            slideOut.setFromX(0);
            slideOut.setToX(-rootPane.getWidth()); // Glijd naar links

            // Na de animatie, pas de nieuwe scene toe (RegisterView)
            slideOut.setOnFinished(event -> {
                currentStage.setScene(registerScene);  // Verander naar de registratie scene
                currentStage.setTitle("Registreren - SmartGreenHouse");  // Stel de titel van de registratiepagina in
            });

            slideOut.play();  // Start de glijdende animatie

        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Fout", "Kan het registratieformulier niet laden.");
        }
    }

    // Voeg een event handler toe voor de Enter-toets bij het inloggen
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            handleLogin();  // Trigger login als Enter wordt ingedrukt
        }
    }

    // Voeg een event handler toe voor de Enter-toets bij registratie (wanneer je op de Register-pagina bent)
    @FXML
    private void handleKeyPressRegister(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            handleRegister();  // Trigger registratie als Enter wordt ingedrukt
        }
    }
}
