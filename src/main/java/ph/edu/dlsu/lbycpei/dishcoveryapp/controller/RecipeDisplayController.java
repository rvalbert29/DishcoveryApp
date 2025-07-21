package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.IOException;

public class RecipeDisplayController {

    @FXML private ImageView recipeImage;
    @FXML private Text recipeTitle;
    @FXML private ListView<String> ingredientsList;
    @FXML private TextArea instructionsText;
    @FXML private Button backButton;


    private Recipe recipe;

    @FXML
    private void initialize() {
        backButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));

        // Add search functionality here
    }

    public void setRecipe(Recipe recipe) {
        recipeTitle.setText(recipe.getName());
        recipeImage.setImage(recipe.getImage());
        ingredientsList.getItems().setAll(recipe.getIngredients());
        instructionsText.setText(recipe.getInstructions());
    }

    private void updateUI() {
        if (recipe != null) {
            recipeTitle.setText(recipe.getName());
            ingredientsList.getItems().setAll(recipe.getIngredients());
            instructionsText.setText(recipe.getInstructions());

            if (recipe.getImage() != null) {
                recipeImage.setImage(recipe.getImage());
            }
        }
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
