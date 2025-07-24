package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        recipesGridPane.getChildren().clear(); // clear existing items if any

        int column = 0;
        int row = 0;

        for (Recipe recipe : RecipeRepository.getFavoriteRecipes()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeCard.fxml")); // or whatever you use to show a recipe card
                Node recipeCard = loader.load();

                // Optional: If you have a controller for RecipeCard to pass data
                // RecipeCardController controller = loader.getController();
                // controller.setRecipe(recipe);

                recipesGridPane.add(recipeCard, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
