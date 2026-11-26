package controllers;

import com.example.finalproject_patterns.FinalApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginbuttonID;

    @FXML
    private Button signupbuttonID;

    @FXML
    private TextField emailID;

    @FXML
    private TextField passwordID;

    @FXML
    public void initialize() {
        Platform.runLater(() -> emailID.getParent().requestFocus());
    }

    @FXML
    private void loginClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FinalApplication.class.getResource("registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 770, 600);
        String css = FinalApplication.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Participant Dashboard");
        stage.setScene(scene);
        stage.show();
    }

}
