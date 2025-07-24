package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import java.io.IOException;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;
import ph.edu.dlsu.lbycpei.dishcoveryapp.data.RecipeRepository;

public class FavoriteRecipeController {

    @FXML private GridPane recipesGridPane;
    @FXML private Button returnHomeButton;

    @FXML
    private void initialize() {
        returnHomeButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
        displayFavoriteRecipes();

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

    private void displayFavoriteRecipes() {
        recipesGridPane.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Recipe recipe : RecipeRepository.getFavoriteRecipes()) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeCard.fxml"));  // make sure path is correct
                Node recipeCard = loader.load();

                // Optional: pass recipe data to the card controller
                // RecipeCardController controller = loader.getController();
                // controller.setRecipe(recipe);

                recipesGridPane.add(recipeCard, column, row);

                column++;
                if (column == 3) {  // change this depending on how many cards per row
                    column = 0;
                    row++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void openRecipeDetails(Recipe recipe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeDetails.fxml"));
            Parent root = loader.load();

            // Assuming your RecipeDetailsController has setRecipe(Recipe) method
            RecipeDetailsController controller = loader.getController();
            controller.setRecipe(recipe);

            Stage stage = new Stage();
            stage.setTitle("Recipe Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class RecipeDetailsController {

        @FXML private Label nameLabel;
        @FXML private ImageView recipeImage;
        @FXML private TextArea instructionsText;

        public void setRecipe(Recipe recipe) {
            nameLabel.setText(recipe.getName());
            recipeImage.setImage(new Image(recipe.getImagePath()));
            instructionsText.setText(recipe.getInstructions());
        }
    }




}
