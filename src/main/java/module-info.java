module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.app.PageUIDesign to javafx.fxml;
    exports com.example.app.Page;
    opens com.example.app.Page to javafx.fxml;
    exports com.example.app.database;
    opens com.example.app.database to javafx.fxml;
}