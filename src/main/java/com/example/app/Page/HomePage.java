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
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class HomePage {

    @FXML
    private Label temperatuur;

    @FXML
    private Label vochtigheid;

    @FXML
    private Label dagnummer;

    @FXML
    private Label Kas1;

    @FXML
    private ImageView tempButtonImage;

    private final dbase database = new dbase();

    @FXML
    protected void initialize() {
        try {
            tempButtonImage.setImage(new Image(getClass().getResource("/com/example/app/icons/Temp2.png").toExternalForm()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Haal de gepinde kas op
        Slimmekas homepageKas = database.getHomepageKas();
        if (homepageKas != null) {
            updateKasData(homepageKas.getKasID());
        }
    }

    // Update gegevens voor een specifieke kas
    public void updateKasData(int kasID) {
        List<Slimmekas> slimmekasList = database.getSlimmekasData();

        for (Slimmekas kas : slimmekasList) {
            if (kas.getKasID() == kasID) {
                temperatuur.setText((int) kas.getTemperatuur() + "°C");
                vochtigheid.setText((int) kas.getLuchtvochtigheid() + "%");
                dagnummer.setText(String.valueOf(kas.getDagnummer()));
                Kas1.setText("Kas " + kas.getKasID());
                break;
            }
        }
    }

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
            tempButtonImage.setImage(new Image(getClass().getResource("/com/example/app/icons/Lebron.png").toExternalForm()));
            temperatuur.setVisible(false);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                try {
                    tempButtonImage.setImage(new Image(getClass().getResource("/com/example/app/icons/Temp2.png").toExternalForm()));
                    temperatuur.setVisible(true);
                    tempButtonClickCount = 0;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            pause.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    private void switchScene(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene newScene = new Scene(loader.load());

            Stage currentStage = (Stage) temperatuur.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle(title);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
