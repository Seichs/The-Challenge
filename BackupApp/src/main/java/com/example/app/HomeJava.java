package com.example.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeJava {

    @FXML
    private Label welcomeText;

    @FXML
    private ImageView buttonImage1;
    @FXML
    private ImageView buttonImage2;
    @FXML
    private ImageView buttonImage3;
    @FXML
    private ImageView buttonImage4;

    @FXML
    protected void initialize() {
        // Zet de afbeeldingen voor de knoppen
        try {
            buttonImage1.setImage(new Image(getClass().getResource("/com/example/app/icons/Kassen.png").toExternalForm()));
            buttonImage2.setImage(new Image(getClass().getResource("/com/example/app/icons/Ons_plan.png").toExternalForm()));
            buttonImage3.setImage(new Image(getClass().getResource("/com/example/app/icons/Over_ons.png").toExternalForm()));
            buttonImage4.setImage(new Image(getClass().getResource("/com/example/app/icons/Groei_tips.png").toExternalForm()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper functie om te schakelen tussen scènes
    private void switchScene(ActionEvent event, String fxmlFile, String pageTitle) {
        try {
            // Laad het nieuwe FXML-bestand
            Parent newPage = FXMLLoader.load(getClass().getResource(fxmlFile));

            // Verkrijg de huidige stage van de gebeurtenis
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Zet de nieuwe Scene
            Scene newScene = new Scene(newPage);
            currentStage.setScene(newScene);
            currentStage.setTitle(pageTitle);

            // Maximaliseer en vergrendel de grootte van het venster
            StageUtils.maximizeAndLock(currentStage);

            // Toon het venster
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void KlikKassen(ActionEvent event) {
        switchScene(event, "/com/example/app/KassenPage.fxml", "Kassen Page");
    }

    @FXML
    protected void KlikOnsPlan(ActionEvent event) {
        welcomeText.setText("Ons plan!");
    }

    @FXML
    protected void KlikOverOns(ActionEvent event) {
        welcomeText.setText("Over ons!");
    }

    @FXML
    protected void KlikGroeiTips(ActionEvent event) {
        welcomeText.setText("Groei tips!");
    }
}
