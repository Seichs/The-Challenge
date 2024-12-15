package com.example.app.Page;

import com.example.app.database.dbase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;

public class KassenPage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button addKasButton;

    @FXML
    private VBox dataBox; // VBox where data is displayed

    private int loadedKasCount = 0; // Tracks how many kassen have been loaded on this page
    private static final int MAX_KASSEN_PER_PAGE = 3; // Limit of kassen per page

    private Slimmekas selectedKas; // Stores the selected kas

    @FXML
    private void initialize() {
        // Action for the "Go Back" button
        goBackButton.setOnAction(event -> goBackToHomePage());

        // Load initial data for the Slimmekas (only 1 box initially)
        loadInitialSlimmekasData();

        // Action for the "Add Kas" button
        addKasButton.setOnAction(event -> loadOneSlimmekas());
    }

    private void goBackToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/PageUIDesign/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            HomePage homePageController = loader.getController();

            if (selectedKas != null) {
                // Pass the selected kas to HomePageController to update the data
                homePageController.updateKasData(selectedKas.getKasID());
            }

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

            // Load the first Slimmekas (only 1 box initially)
            if (slimmekasList.size() > 0) {
                Slimmekas kas = slimmekasList.get(0);
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

            // Check if the maximum number of kassen per page is reached
            if (loadedKasCount >= MAX_KASSEN_PER_PAGE) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Limiet Bereikt");
                alert.setHeaderText("Maximaal aantal kassen bereikt");
                alert.setContentText("Er kunnen maximaal " + MAX_KASSEN_PER_PAGE + " kassen op deze pagina worden toegevoegd.");
                alert.showAndWait();
                return;
            }

            // Load the next kas if available
            if (loadedKasCount < slimmekasList.size()) {
                Slimmekas kas = slimmekasList.get(loadedKasCount);
                HBox kasBox = createKasBox(kas);
                dataBox.getChildren().add(kasBox);
                loadedKasCount++;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Geen Nieuwe Kassen");
                alert.setHeaderText("Geen meer beschikbare kassen");
                alert.setContentText("Alle beschikbare kassen zijn al geladen.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createKasBox(Slimmekas kas) {
        // Create a new HBox for the Kas with horizontal layout
        HBox kasBox = new HBox(10); // Add spacing between sections
        kasBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 5px; -fx-border-color: #1ca120; -fx-border-width: 3px;");
        kasBox.setPrefHeight(100); // Match the example height
        kasBox.setPrefWidth(500); // Consistent width to match layout

        // Kas ID section on the left
        VBox kasIDBox = createLabelWithIcon("Kas " + kas.getKasID(), "/com/example/app/icons/Kassen2.png", 70, 70);

        // Spacer for layout
        Region spacer1 = new Region();
        spacer1.setPrefWidth(20);
        HBox.setHgrow(spacer1, Priority.NEVER);

        // Pin icon
        VBox pinBox = createLabelWithIcon("", "/com/example/app/icons/pin.png", 50, 50);
        pinBox.setOnMouseClicked(event -> pinKasToHomePage(kas)); // Add click listener

        // Spacer for layout
        Region spacer2 = new Region();
        spacer2.setPrefWidth(20);
        HBox.setHgrow(spacer2, Priority.NEVER);

        // Icons and data section aligned right
        HBox iconsAndData = new HBox(20); // Space between icons
        iconsAndData.setStyle("-fx-alignment: center-right;");
        iconsAndData.getChildren().addAll(
                createLabelWithIcon(kas.getTemperatuur() + "°C", "/com/example/app/icons/temperature.png", 50, 50),
                createLabelWithIcon(kas.getLuchtvochtigheid() + "%", "/com/example/app/icons/waterdrop2.png", 50, 50),
                createLabelWithIcon(kas.getDagnummer() + "", "/com/example/app/icons/agendaaa2.png", 50, 50)
        );

        // Add everything to the main HBox
        kasBox.getChildren().addAll(kasIDBox, spacer1, pinBox, spacer2, iconsAndData);

        return kasBox;
    }

    private void pinKasToHomePage(Slimmekas kas) {
        selectedKas = kas; // Store the selected kas for pinning
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kas Vastgepind");
        alert.setHeaderText(null);
        alert.setContentText("Kas " + kas.getKasID() + " is vastgepind naar de Homepagina.");
        alert.showAndWait();
    }

    // Method to create a VBox with an icon and label aligned at the center
    private VBox createLabelWithIcon(String labelText, String iconPath, int iconWidth, int iconHeight) {
        VBox vbox = new VBox(5); // Reduced space between the icon and label
        vbox.setStyle("-fx-alignment: center;"); // Align content center

        try {
            if (iconPath != null) {
                Image icon = new Image(getClass().getResource(iconPath).toExternalForm());
                ImageView imageView = new ImageView(icon);
                imageView.setFitWidth(iconWidth);
                imageView.setFitHeight(iconHeight);
                imageView.setPreserveRatio(true);
                vbox.getChildren().add(imageView);
            }
        } catch (NullPointerException e) {
            System.err.println("Icon not found at path: " + iconPath);
        }

        if (labelText != null && !labelText.isEmpty()) {
            Label label = new Label(labelText);
            label.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
            vbox.getChildren().add(label);
        }

        return vbox;
    }
}
