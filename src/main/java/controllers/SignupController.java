package controllers;

import com.example.finalproject_patterns.FinalApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
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
    private void signupClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FinalApplication.class.getResource("registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = FinalApplication.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Participant Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loginClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FinalApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
