package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import ph.edu.dlsu.lbycpei.dishcoveryapp.data.RecipeRepository;

import java.io.IOException;

public class MainMenuController {

    @FXML private Button pantryManagerButton;
    @FXML private Button addRecipesButton;
    @FXML private Button findRecipesButton;
    @FXML private Button favoritesButton;

    @FXML
    private void initialize() {
        // Button handlers
        pantryManagerButton.setOnAction(event -> switchScene(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/PantryManager2.fxml"));
        addRecipesButton.setOnAction(event -> switchScene(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/AddRecipe.fxml"));
        findRecipesButton.setOnAction(event -> switchScene(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/FindRecipe.fxml"));
        favoritesButton.setOnAction(event -> switchScene(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/FavoriteRecipe.fxml"));
        RecipeRepository.loadFavoritesFromJson();

    }

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene newScene = new Scene(loader.load(), 1000, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
