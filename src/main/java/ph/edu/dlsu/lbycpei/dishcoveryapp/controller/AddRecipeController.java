package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddRecipeController {

    @FXML private TextField recipeNameField;
    @FXML private TextArea ingredientsArea;
    @FXML private TextArea instructionsArea;
    @FXML private Button uploadImageButton;
    @FXML private ImageView recipeImageView;
    @FXML private Button backButton;
    @FXML private Button clearButton;
    @FXML private Button saveButton;

    @FXML
    private void initialize() {
        backButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
        // Add logic for saving, clearing, image upload etc.
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
