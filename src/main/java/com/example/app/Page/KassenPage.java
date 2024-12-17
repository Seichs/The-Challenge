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
    private Button goBackButton1;

    @FXML
    private VBox dataBox;

    private int currentPage = 0;
    private static final int KASSEN_PER_PAGE = 3;
    private List<Slimmekas> allKassen = new ArrayList<>();
    private boolean isInitialLoad = true;

    @FXML
    private void initialize() {
        loadAllSlimmekasData();  // Start by loading all kassen data

        // Action for the "+" button to load new kassen
        addKasButton.setOnAction(event -> {
            loadNewKassenData();  // Load only new kassen with isLoaded = 0
            showPage(currentPage);
        });

        // Other navigation button actions
        goBackButton.setOnAction(event -> handleGoBack());
        goBackButton1.setOnAction(event -> navigateToNextPage());
    }

    private void loadAllSlimmekasData() {
        if (isInitialLoad) {
            try {
                dbase database = new dbase();
                allKassen = database.getSlimmekasData();
                isInitialLoad = false;
                showPage(currentPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadNewKassenData() {
        try {
            dbase database = new dbase();
            List<Slimmekas> newKassen = database.getNewSlimmekasData(); // Get kassen with isLoaded = 0

            if (newKassen.isEmpty()) {
                showNoNewKassenAlert(); // Show alert if there are no new kassen
            } else {
                allKassen.addAll(newKassen); // Add new kassen to the list
                database.updateKassenIsLoaded(newKassen); // Mark these kassen as loaded in the database
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNoNewKassenAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Geen Nieuwe Kassen");
        alert.setHeaderText(null);
        alert.setContentText("Er zijn geen nieuwe kassen om in te laden.");
        alert.showAndWait();
    }

    private void handleGoBack() {
        if (currentPage == 0) {
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
        dataBox.getChildren().clear();

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
