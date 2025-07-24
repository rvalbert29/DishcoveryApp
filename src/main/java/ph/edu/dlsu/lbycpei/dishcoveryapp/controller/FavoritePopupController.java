package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.IOException;
import java.util.List;

public class FavoritePopupController {

    @FXML private Button viewButton;
    @FXML private Button closeButton;

    private Stage popupStage;  // this is set externally when popup is created

    public void setPopupStage(Stage stage) {
        this.popupStage = stage;
    }
    @FXML
    private void initialize() {
        viewButton.setOnAction(e -> {
            try {
                // Load the FavoriteRecipe scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/FavoriteRecipe.fxml"));
                Parent root = loader.load();

                Stage newStage = new Stage();
                newStage.setTitle("My Favorite Recipes");
                newStage.setScene(new Scene(root, 1000, 600));
                newStage.show();

                // Close the popup
                if (popupStage != null) {
                    popupStage.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        closeButton.setOnAction(e -> {
            if (popupStage != null) {
                popupStage.close();
            }
        });
    }

    @FXML
    private ListView<Recipe> favoritesListView;

    public void setFavoriteRecipes(List<Recipe> recipes) {
        favoritesListView.getItems().setAll(recipes);
    }


    private Recipe recipeToView;

    public void setRecipeToView(Recipe recipe) {
        this.recipeToView = recipe;
    }

    @FXML
    private void handleViewRecipe(ActionEvent event) {
        if (recipeToView == null) {
            System.out.println("No recipe to view.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeDisplay.fxml"));
            Parent root = loader.load();

            // Pass the recipe to the display controller
            RecipeDisplayController controller = loader.getController();
            controller.setRecipe(recipeToView); // this method must exist in RecipeDisplayController

            Stage stage = new Stage();
            stage.setTitle(recipeToView.getName());
            stage.setScene(new Scene(root));
            stage.show();

            // Close the popup
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}