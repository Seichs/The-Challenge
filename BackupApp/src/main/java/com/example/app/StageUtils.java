package com.example.app;

import javafx.stage.Stage;

public class StageUtils {
    public static void maximizeAndLock(Stage stage) {
        stage.setMaximized(true);  // Maximaliseer het venster
        stage.setResizable(false); // Zorg ervoor dat het venster niet resizebaar is
    }
}
