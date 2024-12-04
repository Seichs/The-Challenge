package com.example.app.Page;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class GroeiTipsPage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button zaadjesButton;

    @FXML
    private Button overpottenButton;

    @FXML
    private Button locatieButton;

    @FXML
    private Button voedingButton;

    @FXML
    private void initialize() {
        // Initialize button actions
        goBackButton.setOnAction(event -> navigateTo("/com/example/app/PageUIDesign/HomePage.fxml", "Home Page"));
        zaadjesButton.setOnAction(event -> navigateTo("/com/example/app/PageUIDesign/ZaadjesPage.fxml", "Zaadjes"));
        overpottenButton.setOnAction(event -> navigateTo("/com/example/app/PageUIDesign/OverpottenPage.fxml", "Overpotten"));
        locatieButton.setOnAction(event -> navigateTo("/com/example/app/PageUIDesign/LocatiePage.fxml", "Locatie"));
        voedingButton.setOnAction(event -> navigateTo("/com/example/app/PageUIDesign/VoedingPage.fxml", "Voeding"));
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get the current stage from any button's scene
            Stage stage = (Stage) goBackButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Set the title
            stage.setTitle(title);

            // Show the stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
