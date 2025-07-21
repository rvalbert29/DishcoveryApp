package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class AddRecipeController {

    @FXML
    private TextField recipeNameField;

    @FXML
    private TextArea ingredientsArea;

    @FXML
    private TextArea instructionsArea;

    @FXML
    private ImageView recipeImageView;

    @FXML
    private Button uploadImageButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button backButton;

    private File selectedImageFile;

    @FXML
    private void initialize() {
        uploadImageButton.setOnAction(e -> handleUploadImage());
        saveButton.setOnAction(e -> handleSaveRecipe());
        clearButton.setOnAction(e -> handleClear());
        backButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
    }

    @FXML
    private Text placeholderText; // bind this to the <Text> inside the StackPane

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

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Recipe Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            recipeImageView.setImage(image);
            selectedImageFile = file;

            // Hide the placeholder text
            placeholderText.setVisible(false);  // Fix this line too (see below)

            recipeImageView.setImage(image);
            recipeImageView.setFitWidth(200); // or your desired width
            recipeImageView.setFitHeight(200); // or your desired height
            recipeImageView.setPreserveRatio(true); // maintain image proportions
            recipeImageView.setSmooth(true); // better scaling quality
        }
    }

    private void handleSaveRecipe() {
        String name = recipeNameField.getText();
        String ingredients = ingredientsArea.getText();
        String instructions = instructionsArea.getText();

        if (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        System.out.println("Recipe saved:");
        System.out.println("Name: " + name);
        System.out.println("Ingredients: " + ingredients);
        System.out.println("Instructions: " + instructions);
        System.out.println("Image: " + (selectedImageFile != null ? selectedImageFile.getAbsolutePath() : "None"));


        // Saving to memory or file will be added later.
    }

    private void handleClear() {
        recipeNameField.clear();
        ingredientsArea.clear();
        instructionsArea.clear();
        recipeImageView.setImage(null);
        selectedImageFile = null;
    }


    private Stage getCurrentStage() {
        return (Stage) recipeNameField.getScene().getWindow();
    }
}
