package com.example.app.Page;

import com.example.app.database.dbase;
import com.example.app.Page.Slimmekas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class KassenPage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button addKasButton; // Button to add a new Kas

    @FXML
    private VBox dataBox;  // This is the VBox where data will be displayed

    private int loadedKasCount = 0; // Track how many Kassen have been loaded

    @FXML
    private void initialize() {
        // Set action for "Go Back" button
        goBackButton.setOnAction(event -> goBackToHomePage());

        // Load Slimmekas data when the page initializes
        loadInitialSlimmekasData();

        // Action for "Add Kas" button
        addKasButton.setOnAction(event -> loadOneSlimmekas());
    }

    private void goBackToHomePage() {
        try {
            // Load the FXML file for the homepage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            // Show an alert if navigation fails
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Navigatiefout");
            alert.setHeaderText("Kan niet terug naar de homepage");
            alert.setContentText("Er is een fout opgetreden bij het laden van de homepage.");
            alert.showAndWait();
        }
    }

    private void loadInitialSlimmekasData() {
        try {
            // Load the first batch of Slimmekas data (if any)
            dbase database = new dbase();
            List<Slimmekas> slimmekasList = database.getSlimmekasData();

            for (int i = 0; i < Math.min(3, slimmekasList.size()); i++) {
                Slimmekas kas = slimmekasList.get(i);
                HBox kasBox = createKasBox(kas);
                dataBox.getChildren().add(kasBox);
            }

            loadedKasCount = Math.min(3, slimmekasList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOneSlimmekas() {
        try {
            dbase database = new dbase();
            List<Slimmekas> slimmekasList = database.getSlimmekasData();

            // Check if there are more Kassen to load
            if (loadedKasCount >= slimmekasList.size()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Geen Nieuwe Kassen");
                alert.setHeaderText("Er zijn geen nieuwe kassen beschikbaar om te laden.");
                alert.setContentText("Alle beschikbare kassen zijn al geladen.");
                alert.showAndWait();
                return;
            }

            // Load the next Kas
            Slimmekas kas = slimmekasList.get(loadedKasCount);
            HBox kasBox = createKasBox(kas);
            dataBox.getChildren().add(kasBox);

            // Increment the count of loaded Kassen
            loadedKasCount++;
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
}
