package com.example.app.Page;

import com.example.app.database.dbase;
import com.example.app.Page.Slimmekas;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;

public class KassenPage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button addKasButton;

    @FXML
    private VBox dataBox;  // VBox where data is displayed

    private int loadedKasCount = 0;

    @FXML
    private void initialize() {
        // Action for the "Go Back" button
        goBackButton.setOnAction(event -> goBackToHomePage());

        // Load initial data for the Slimmekas (only 2 boxes initially)
        loadInitialSlimmekasData();

        // Action for the "Add Kas" button
        addKasButton.setOnAction(event -> loadOneSlimmekas());
    }

    private void goBackToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInitialSlimmekasData() {
        try {
            dbase database = new dbase();
            List<Slimmekas> slimmekasList = database.getSlimmekasData();

            // Load the first 2 Kassen, or less if there aren't enough
            for (int i = 0; i < Math.min(2, slimmekasList.size()); i++) {
                Slimmekas kas = slimmekasList.get(i);
                HBox kasBox = createKasBox(kas);
                dataBox.getChildren().add(kasBox);
                loadedKasCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOneSlimmekas() {
        try {
            dbase database = new dbase();
            List<Slimmekas> slimmekasList = database.getSlimmekasData();

            // Check if there are more Kassen to load (only up to 4)
            if (loadedKasCount >= 4) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Geen Nieuwe Kassen");
                alert.setHeaderText("Er zijn geen nieuwe kassen beschikbaar om te laden.");
                alert.setContentText("Alle beschikbare kassen zijn al geladen.");
                alert.showAndWait();
                return;  // Stop loading if all kassen are already loaded
            }

            // Load the next Kas
            Slimmekas kas = slimmekasList.get(loadedKasCount);
            HBox kasBox = createKasBox(kas);
            dataBox.getChildren().add(kasBox);
            loadedKasCount++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createKasBox(Slimmekas kas) {
        // Create a new HBox for the Kas with horizontal layout
        HBox kasBox = new HBox(15);  // Reduced space between each item to make it more compact
        kasBox.setStyle("-fx-background-color: white; -fx-padding: 5; -fx-border-radius: 5px; -fx-border-color: #1ca120; -fx-border-width: 2px;"); // Reduced padding for compact boxes
        kasBox.setMaxHeight(120);  // Set a maximum height for the box to keep it compact

        // Create a VBox to arrange the icon and the label vertically
        VBox kasIDBox = createLabelWithIcon("Kas ID: " + kas.getKasID(), "/com/example/app/icons/Kassen2.png", 60, 60);  // Slightly smaller Kas icon size
        VBox tempBox = createLabelWithIcon(kas.getTemperatuur() + " °C", null, 45, 45);  // Slightly smaller temperature icon
        VBox humidityBox = createLabelWithIcon(kas.getLuchtvochtigheid() + " %", "/com/example/app/icons/waterdrop2.png", 45, 45);  // Slightly smaller humidity icon
        VBox dayBox = createLabelWithIcon("Dag: " + kas.getDagnummer(), "/com/example/app/icons/agendaaa2.png", 45, 45);  // Slightly smaller day icon

        // Add the label boxes to the main HBox
        kasBox.getChildren().addAll(kasIDBox, tempBox, humidityBox, dayBox);

        return kasBox;
    }

    // Method to create a VBox with an icon above the label and allow custom icon size
    private VBox createLabelWithIcon(String labelText, String iconPath, int iconWidth, int iconHeight) {
        VBox vbox = new VBox(5); // 5px space between the icon and label
        vbox.setStyle("-fx-alignment: center;");  // Center the content (icon and label) inside the VBox

        if (iconPath != null) {
            Image icon = new Image(getClass().getResource(iconPath).toExternalForm());
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(iconWidth); // Set the custom width for the icon
            imageView.setFitHeight(iconHeight); // Set the custom height for the icon
            imageView.setPreserveRatio(true); // Preserve the aspect ratio to avoid distortion
            vbox.getChildren().add(imageView); // Add the icon to the VBox
        }

        // Create and style the label
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 12px; -fx-font-family: 'Arial'; -fx-alignment: center;");
        vbox.getChildren().add(label); // Add the label below the icon

        return vbox;
    }
}
