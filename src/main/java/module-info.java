module com.example.finalproject_patterns {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalproject_patterns to javafx.fxml;
    exports com.example.finalproject_patterns;
}