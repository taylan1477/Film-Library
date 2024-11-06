module com.example.filmlibrary {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.filmlibrary to javafx.fxml;
    exports com.example.filmlibrary;
}