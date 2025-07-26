package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.IOException;

public class SavedPopupController {

    @FXML private Button closeButton;
    @FXML private Button viewButton;

    private Recipe savedRecipe;
    private String result = "close";


    public String getResult() {
        return result;
    }

    public void setRecipe(Recipe recipe) {
        this.savedRecipe = recipe;
    }

    @FXML
    private void initialize() {
        closeButton.setOnAction(this::handleClose);
        viewButton.setOnAction(this::handleView);
    }

    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close(); // Just close the popup — AddRecipeController will go to main menu
    }

    private void handleView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeDisplay.fxml"));
            Parent root = loader.load();

            RecipeDisplayController controller = loader.getController();
            controller.setRecipe(savedRecipe);

            Stage stage = (Stage) viewButton.getScene().getWindow();
            stage.close();

            Stage mainStage = new Stage();
            mainStage.setScene(new Scene(root));
            mainStage.setTitle("Recipe Viewer");
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
