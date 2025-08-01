package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;
import ph.edu.dlsu.lbycpei.dishcoveryapp.data.RecipeRepository;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private void handleClear() {
        recipeNameField.clear();
        ingredientsArea.clear();
        instructionsArea.clear();
        recipeImageView.setImage(null);
        selectedImageFile = null;

        placeholderText.setVisible(true);
    }

    @FXML
    private Text placeholderText;

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
            placeholderText.setVisible(false);
            recipeImageView.setImage(image);
            recipeImageView.setFitWidth(200);
            recipeImageView.setFitHeight(200);
            recipeImageView.setPreserveRatio(true);
            recipeImageView.setSmooth(true);
        }
    }

    @FXML
    private TextArea ingredientsQuantityArea;

    private void handleSaveRecipe() {
        String name = recipeNameField.getText().trim();
        String ingredientsText = ingredientsArea.getText().trim();
        String instructions = instructionsArea.getText().trim();
        String ingredientsQuantityText = ingredientsQuantityArea.getText().trim();


        if (name.isEmpty() || ingredientsText.isEmpty() || instructions.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // Process ingredients and quantities
        String[] ingredientLines = ingredientsText.split("\\r?\\n");
        String[] quantityLines = ingredientsQuantityText.split("\\r?\\n");

        // Create map to store ingredients with quantities
        Map<String, String> ingredientsMap = new LinkedHashMap<>();
        for (int i = 0; i < ingredientLines.length; i++) {
            String ingredient = ingredientLines[i].trim();
            String quantity = i < quantityLines.length ? quantityLines[i].trim() : "";
            ingredientsMap.put(ingredient, quantity);
        }


        String imagePath = null;
        if (selectedImageFile != null) {
            try {
                File imageDir = new File("data/recipe_images");
                if (!imageDir.exists()) imageDir.mkdirs();

                File destFile = new File(imageDir, selectedImageFile.getName());
                try (InputStream in = new FileInputStream(selectedImageFile);
                     OutputStream out = new FileOutputStream(destFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }

                imagePath = destFile.getPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to copy image.");
            }
        }

        List<String> parsedIngredients = List.of(ingredientsText.split("\\r?\\n"));
        Recipe newRecipe = new Recipe(name, ingredientsMap, instructions, imagePath);
        RecipeRepository.addRecipe(newRecipe);

        System.out.println("Recipe saved to file!");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/Saved.fxml"));
            Parent root = loader.load();

            SavedPopupController controller = loader.getController();
            controller.setRecipe(newRecipe);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Saved Confirmation");
            popupStage.showAndWait();

            // Check what user chose in the popup
            String userChoice = controller.getResult();

            if (userChoice.equals("view")) {
                FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeDisplay.fxml"));
                Parent viewRoot = viewLoader.load();

                RecipeDisplayController viewController = viewLoader.getController();
                viewController.setRecipe(newRecipe);

                // Show in same window
                Stage currentStage = (Stage) recipeNameField.getScene().getWindow();
                currentStage.setScene(new Scene(viewRoot));
                currentStage.setTitle("View Recipe");

            } else {
                // user clicked Close
                handleBackToMainMenu(null, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
