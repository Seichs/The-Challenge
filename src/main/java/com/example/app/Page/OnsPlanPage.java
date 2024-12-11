package com.example.app.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class OnsPlanPage {

    @FXML
    private Button goBackButton; // Button voor 'Ga terug'

    @FXML
    private void initialize() {
        // Deze methode kan hier leeg blijven, de actie wordt via FXML gekoppeld.
    }

    @FXML
    private void goBack() {
        try {
            // Laad de HomePage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
            Parent root = loader.load();

            // Krijg het huidige venster (Stage)
            Stage stage = (Stage) goBackButton.getScene().getWindow();

            // Zet de nieuwe scène
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Zet de titel van het venster (optioneel)
            stage.setTitle("Home Page");

            // Toon de nieuwe scène (HomePage)
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
