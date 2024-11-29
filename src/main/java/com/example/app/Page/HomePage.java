package com.example.app.Page;

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

public class HomePage {

    @FXML
    private Label welcomeText;  // Ensure this is linked to the Label in your FXML

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
        // Set images for buttons
        try {
            buttonImage1.setImage(new Image(getClass().getResource("/com/example/app/icons/Kassen.png").toExternalForm()));
            buttonImage2.setImage(new Image(getClass().getResource("/com/example/app/icons/Ons_plan.png").toExternalForm()));
            buttonImage3.setImage(new Image(getClass().getResource("/com/example/app/icons/Over_ons.png").toExternalForm()));
            buttonImage4.setImage(new Image(getClass().getResource("/com/example/app/icons/Groei_tips.png").toExternalForm()));
        } catch (Exception e) {
            e.printStackTrace(); // Catch errors related to missing resources
        }
    }

    // Helper function to switch scenes
    private void switchScene(ActionEvent event, String fxmlFile, String pageTitle) {
        try {
            // Load the new FXML file
            Parent newPage = FXMLLoader.load(getClass().getResource(fxmlFile));

            // Get the current stage from the button's event
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene and title
            Scene newScene = new Scene(newPage);
            currentStage.setScene(newScene);
            currentStage.setTitle(pageTitle);



            // Show the stage
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    protected void KlikKassen(ActionEvent event) {
        switchScene(event, "/com/example/app/PageUIDesign/KassenPage.fxml", "Kassen");
    }
    @FXML
    protected void KlikOnsPlan(ActionEvent event) {
        // Update the label text when a button is clicked
        switchScene(event, "/com/example/app/PageUIDesign/OnsPlanPage.fxml", "Ons plan");
    }
    @FXML
    protected void KlikOverOns(ActionEvent event) {
        // Update the label text when a button is clicked
        switchScene(event, "/com/example/app/PageUIDesign/OverOnsPage.fxml", "Over ons");
    }
    @FXML
    protected void KlikGroeiTips(ActionEvent event) {
        // Update the label text when a button is clicked
        switchScene(event, "/com/example/app/PageUIDesign/GroeiTipsPage.fxml", "Over ons");
    }
}
