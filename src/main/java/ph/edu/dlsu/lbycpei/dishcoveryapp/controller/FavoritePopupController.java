package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Recipe Selected");
            alert.setHeaderText(null);
            alert.setContentText("There is no recipe to view.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeDisplay.fxml"));
            Parent viewRoot = viewLoader.load();

            RecipeDisplayController viewController = viewLoader.getController();
            viewController.setRecipe(recipeToView);

            // Show in same window (close popup and show recipe)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(viewRoot));
            currentStage.setTitle("View Recipe");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}