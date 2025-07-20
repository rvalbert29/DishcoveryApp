package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
        // Add logic for saving, clearing, image upload etc.
    }
}
