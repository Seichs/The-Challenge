package com.example.app.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Page { //Voeg hier de pagina naam toe!

    @FXML
    private Button goBackButton;

    @FXML
    private void initialize() {
        // Geef aan wat de "Ga terug" knop doet
        goBackButton.setOnAction(event -> goBackToHomePage());
    }

    private void goBackToHomePage() {
        try {
            // Door deze functie gaat hij terug naar de HomePage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
            Parent root = loader.load();

            // Haalt de status op van de "Ga terug knop"
            Stage stage = (Stage) goBackButton.getScene().getWindow();

            // zet teh stage wow
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Ze hier de titel die boven de applicatie stata voor deze pagina
            stage.setTitle("Blank Page");

            // Show the stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}