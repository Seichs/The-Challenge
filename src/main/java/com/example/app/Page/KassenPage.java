package com.example.app.Page;

import com.example.app.database.dbase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KassenPage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button addKasButton;

    @FXML
    private Button goBackButton1; // Right arrow button for next page

    @FXML
    private VBox dataBox; // VBox where data is displayed

    private int currentPage = 0; // Current page index
    private static final int KASSEN_PER_PAGE = 3; // Limit of kassen per page
    private List<Slimmekas> allKassen = new ArrayList<>(); // Full list of kassen from the database
    private boolean isInitialLoad = true; // Flag to track initial load state

    @FXML
    private void initialize() {
        // Load the first kas only
        loadFirstKas();

        // Actions for navigation buttons
        goBackButton.setOnAction(event -> handleGoBack());
        goBackButton1.setOnAction(event -> navigateToNextPage());

        // Action for the "+" button to load all kassen
        addKasButton.setOnAction(event -> {
            loadAllSlimmekasData();
            showPage(currentPage);
        });
    }

    private void loadFirstKas() {
        try {
            dbase database = new dbase();
            allKassen = database.getSlimmekasData();

            // Show only the first kas initially
            if (!allKassen.isEmpty()) {
                List<Slimmekas> initialKas = allKassen.subList(0, 1); // First kas
                allKassen = initialKas;
                showPage(currentPage);
            } else {
                showNoKassenAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllSlimmekasData() {
        if (isInitialLoad) {
            try {
                dbase database = new dbase();
                allKassen = database.getSlimmekasData(); // Load all kassen
                isInitialLoad = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showNoKassenAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Geen Kassen Beschikbaar");
        alert.setHeaderText(null);
        alert.setContentText("Er zijn geen kassen in de database.");
        alert.showAndWait();
    }

    private void handleGoBack() {
        if (currentPage == 0) {
            // Navigate to homepage if on the first page
            goBackToHomePage();
        } else {
            navigateToPreviousPage();
        }
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

    private void showPage(int pageIndex) {
        dataBox.getChildren().clear(); // Clear the existing kassen

        int start = pageIndex * KASSEN_PER_PAGE;
        int end = Math.min(start + KASSEN_PER_PAGE, allKassen.size());

        for (int i = start; i < end; i++) {
            Slimmekas kas = allKassen.get(i);
            HBox kasBox = createKasBox(kas);
            dataBox.getChildren().add(kasBox);
        }
    }

    private void navigateToNextPage() {
        if ((currentPage + 1) * KASSEN_PER_PAGE < allKassen.size()) {
            currentPage++;
            showPage(currentPage);
        }
    }

    private void navigateToPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            showPage(currentPage);
        }
    }

    private HBox createKasBox(Slimmekas kas) {
        HBox kasBox = new HBox(10);
        kasBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 5px; -fx-border-color: #1ca120; -fx-border-width: 3px;");
        kasBox.setPrefHeight(100);
        kasBox.setPrefWidth(500);

        VBox kasIDBox = createLabelWithIcon("Kas " + kas.getKasID(), "/com/example/app/icons/Kassen2.png", 70, 70);
        Region spacer1 = new Region();
        spacer1.setPrefWidth(20);
        HBox.setHgrow(spacer1, Priority.NEVER);

        VBox pinBox = createPinIconWithBackground(kas);
        Region spacer2 = new Region();
        spacer2.setPrefWidth(20);
        HBox.setHgrow(spacer2, Priority.NEVER);

        HBox iconsAndData = new HBox(20);
        iconsAndData.setStyle("-fx-alignment: center-right;");
        iconsAndData.getChildren().addAll(
                createLabelWithIcon(kas.getTemperatuur() + "°C", "/com/example/app/icons/temperature.png", 50, 50),
                createLabelWithIcon(kas.getLuchtvochtigheid() + "%", "/com/example/app/icons/waterdrop2.png", 50, 50),
                createLabelWithIcon(kas.getDagnummer() + "", "/com/example/app/icons/agendaaa2.png", 50, 50)
        );

        kasBox.getChildren().addAll(kasIDBox, spacer1, pinBox, spacer2, iconsAndData);
        return kasBox;
    }

    private VBox createPinIconWithBackground(Slimmekas kas) {
        StackPane stackPane = new StackPane();

        Rectangle greenBackground = new Rectangle(50, 50);
        greenBackground.setFill(Color.LIGHTGREEN);
        greenBackground.setArcWidth(10);
        greenBackground.setArcHeight(10);

        ImageView pinIcon = new ImageView(new Image(getClass().getResource("/com/example/app/icons/pin.png").toExternalForm()));
        pinIcon.setFitWidth(30);
        pinIcon.setFitHeight(30);
        pinIcon.setPreserveRatio(true);

        stackPane.getChildren().addAll(greenBackground, pinIcon);
        stackPane.setOnMouseClicked(event -> pinKasToHomePage(kas));

        VBox pinBox = new VBox(stackPane);
        pinBox.setStyle("-fx-alignment: center;");
        return pinBox;
    }

    private void pinKasToHomePage(Slimmekas kas) {
        try {
            dbase database = new dbase();
            database.resetHomepageKas();
            database.setKasAsHomepage(kas.getKasID());

            // Show notification for pinning
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kas Vastgepind");
            alert.setHeaderText(null);
            alert.setContentText("Kas " + kas.getKasID() + " is vastgepind naar de homepage.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createLabelWithIcon(String labelText, String iconPath, int iconWidth, int iconHeight) {
        VBox vbox = new VBox(5);
        vbox.setStyle("-fx-alignment: center;");
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
