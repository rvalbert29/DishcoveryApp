package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpei.dishcoveryapp.data.RecipeRepository;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.IOException;
import java.util.List;

public class FindRecipeController {

    @FXML private TextField ingredientSearchField;
    @FXML private Button matchRecipesButton;
    @FXML private Button backButton;

    @FXML
    private void initialize() {
        backButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
        matchRecipesButton.setOnAction(this::handleSearchRecipe);
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

    @FXML
    private void handleSearchRecipe(ActionEvent event) {
        String query = ingredientSearchField.getText().trim();
        Recipe result = getRecipeByName(query);

        if (result != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeDisplay.fxml"));
                Parent root = loader.load();

                RecipeDisplayController controller = loader.getController();
                controller.setRecipe(result);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Recipe not found.");
            showNotFoundPopup();
        }
    }




    private Recipe getRecipeByName(String recipeName) {
        List<Recipe> allRecipes = RecipeRepository.getRecipes();

        for (Recipe r : allRecipes) {
            if (r.getName().equalsIgnoreCase(recipeName)) {
                return r;
            }
        }

        return null;
    }


    private void showNotFoundPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/NotFound.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            popupStage.setTitle("Recipe Not Found");
            popupStage.setScene(new Scene(loader.load()));

            // Optional: Get the button and handle close directly here
            Button closeBtn = (Button) loader.getNamespace().get("closeButton1");
            closeBtn.setOnAction(e -> popupStage.close());

            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
