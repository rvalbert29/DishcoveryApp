package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PantryManagerController {

    @FXML private TextField ingredientSearchField;
    @FXML private Button matchRecipesButton;
    @FXML private Button backButton;

    @FXML
    private void initialize() {
        backButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
        // Add event logic here
    }

    @FXML
    private void handleBackToMainMenu(ActionEvent event, String s) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
