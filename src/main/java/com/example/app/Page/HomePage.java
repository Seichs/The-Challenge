package com.example.app.Page;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.example.app.database.dbase;
import com.example.app.Page.Slimmekas;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;
import java.io.IOException;
import java.util.List;

public class HomePage {

    // Temperature label
    @FXML
    private Label temperatuur;

    @FXML
    private Label vochtigheid; // Humidity label

    @FXML
    private Label dagnummer; // Day number label

    @FXML
    private Label Kas1; // Kas label (Kas 1)

    @FXML
    private ImageView tempButtonImage;

    private dbase database = new dbase();

    @FXML
    protected void initialize() {
        // Set the image for the button
        try {
            tempButtonImage.setImage(new Image(getClass().getResource("/com/example/app/icons/Temp2.png").toExternalForm()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Automatically update Kas data for Kas 1 on page load
        updateKasData(1);  // Fetch and display data for Kas 1 (the first Kas)
    }

    // Method to update Kas data based on the selected Kas ID
    public void updateKasData(int kasID) {
        List<Slimmekas> slimmekasList = database.getSlimmekasData();

        // Loop through the list of Slimmekas to find the selected Kas by its ID
        for (Slimmekas kas : slimmekasList) {
            if (kas.getKasID() == kasID) {
                // Update the labels with data from the selected kas
                temperatuur.setText(String.valueOf((int) kas.getTemperatuur()) + "°C");
                vochtigheid.setText(String.valueOf((int) kas.getLuchtvochtigheid()) + "%");
                dagnummer.setText(String.valueOf(kas.getDagnummer()));

                // Update the Kas name (Kas 1)
                Kas1.setText("Kas " + kas.getKasID()); // Update Kas1 label based on the KasID

                // We only break when we find the kas with the matching KasID
                break;
            }
        }
    }

    // Existing methods for button clicks and easter egg
    private int tempButtonClickCount = 0;
    private final int easterEggTriggerCount = 10;

    @FXML
    private void handleTempButtonClick() {
        tempButtonClickCount++;
        if (tempButtonClickCount == easterEggTriggerCount) {
            triggerEasterEgg();
        }
    }

    private void triggerEasterEgg() {
        try {
            // Verander het afbeelding naar Lebron.png
            tempButtonImage.setImage(new Image(getClass().getResource("/com/example/app/icons/Lebron.png").toExternalForm()));

            // Verberg het temperatuurlabel
            temperatuur.setVisible(false);

            // Stel een vertraging in om de afbeelding terug te zetten en het label weer te tonen
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                try {
                    // Zet de afbeelding terug naar Temp2.png
                    tempButtonImage.setImage(new Image(getClass().getResource("/com/example/app/icons/Temp2.png").toExternalForm()));

                    // Maak het temperatuurlabel weer zichtbaar
                    temperatuur.setVisible(true);

                    // Reset de klik teller
                    tempButtonClickCount = 0;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error resetting the temperature button image.");
                }
            });
            pause.play();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error triggering easter egg.");
        }
    }

    // Button actions for navigating to different pages with ActionEvent
    @FXML
    protected void KlikKassen(ActionEvent event) {
        switchScene(event, "/com/example/app/PageUIDesign/KassenPage.fxml", "Kassen");
    }

    @FXML
    protected void KlikOnsPlan(ActionEvent event) {
        switchScene(event, "/com/example/app/PageUIDesign/OnsPlanPage.fxml", "Ons plan");
    }

    @FXML
    protected void KlikOverOns(ActionEvent event) {
        switchScene(event, "/com/example/app/PageUIDesign/OverOnsPage.fxml", "Over ons");
    }

    @FXML
    protected void KlikGroeiTips(ActionEvent event) {
        switchScene(event, "/com/example/app/PageUIDesign/GroeiTipsPage.fxml", "Groei Tips");
    }

    // Common method to switch scenes
    private void switchScene(ActionEvent event, String fxmlFile, String title) {
        try {
            // Load the new page
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene newScene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) temperatuur.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle(title); // Optionally, set the window title
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file: " + fxmlFile);
        }
    }
}
