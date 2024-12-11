package com.example.app.Page;

import com.example.app.database.dbase;
import com.example.app.Page.Slimmekas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.List;

public class KassenPage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button addKasButton; // Button to add a new Kas

    @FXML
    private VBox dataBox;  // This is the VBox where data will be displayed

    private int loadedKasCount = 0; // Track how many Kassen have been loaded
    private static final int BATCH_SIZE = 3; // Number of kassen to load at once

    @FXML
    private void initialize() {
        // Set action for "Go Back" button
        goBackButton.setOnAction(event -> goBackToHomePage());

        // Load Slimmekas data when the page initializes
        loadSlimmekasData();

        // Action for "Add Kas" button
        addKasButton.setOnAction(event -> addNewKas());
    }

    private void goBackToHomePage() {
        try {
            // Logic to navigate back to the HomePage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSlimmekasData() {
        try {
            // Get list of Slimmekas objects from the database
            dbase database = new dbase();
            List<Slimmekas> slimmekasList = database.getSlimmekasData();

            // Only load the next batch of kassen
            for (int i = loadedKasCount; i < Math.min(loadedKasCount + BATCH_SIZE, slimmekasList.size()); i++) {
                Slimmekas kas = slimmekasList.get(i);
                HBox kasBox = createKasBox(kas); // Create HBox for each kas
                dataBox.getChildren().add(kasBox); // Add the HBox to the VBox
            }

            // Update the count of loaded kassen
            loadedKasCount = Math.min(loadedKasCount + BATCH_SIZE, slimmekasList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createKasBox(Slimmekas kas) {
        HBox kasBox = new HBox(10);
        kasBox.setAlignment(javafx.geometry.Pos.CENTER);
        kasBox.setStyle("-fx-background-color: lightgreen; -fx-padding: 10; -fx-border-radius: 5px;");

        Label kasLabel = new Label("Kas ID: " + kas.getKasID());
        kasLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");

        Label tempLabel = new Label(kas.getTemperatuur() + " °C");
        tempLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");

        Label humidityLabel = new Label(kas.getLuchtvochtigheid() + " %");
        humidityLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");

        Label dayLabel = new Label("Dag: " + kas.getDagnummer());
        dayLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");

        kasBox.getChildren().addAll(kasLabel, tempLabel, humidityLabel, dayLabel);
        return kasBox;
    }

    private void addNewKas() {
        // Example data for the new Kas
        int kasID = loadedKasCount + 1;  // New Kas ID, should be incremented from the loaded count
        String sensortype = "SensorType";  // Placeholder for sensortype (you could change this)
        int dagnummer = 1;  // Placeholder for dagnummer
        double temperatuur = 20.0;  // Placeholder for temperatuur
        double luchtvochtigheid = 50.0;  // Placeholder for luchtvochtigheid
        String timestamp = "2024-12-11 10:00:00";  // Placeholder for timestamp

        // Create new Slimmekas object
        Slimmekas newKas = new Slimmekas(kasID, sensortype, dagnummer, temperatuur, luchtvochtigheid, timestamp);

        // Create an HBox for the new Kas and add it to the VBox
        HBox kasBox = createKasBox(newKas);
        dataBox.getChildren().add(kasBox);

        // Show an alert that the new Kas was added
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Nieuwe Kas Toegevoegd");
        alert.setHeaderText("Een nieuwe kas is succesvol toegevoegd!");
        alert.setContentText("De kas is toegevoegd aan de lijst.");
        alert.showAndWait();
    }
}
