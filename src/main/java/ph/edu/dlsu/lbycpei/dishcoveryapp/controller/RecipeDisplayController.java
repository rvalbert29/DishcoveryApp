package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import ph.edu.dlsu.lbycpei.dishcoveryapp.data.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

public class RecipeDisplayController {

    public AnchorPane rootPane;
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
        this.recipe = recipe;
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

    @FXML
    private Button deleteButton;

    @FXML
    private void handleDeleteRecipe(ActionEvent event) {
        System.out.println("Delete action triggered");

        if (recipe != null) {
            System.out.println("Deleting: " + recipe.getName());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete " + recipe.getName() + "?");
            alert.setContentText("This cannot be undone!");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean deleted = RecipeRepository.deleteRecipe(recipe);
                System.out.println("Deletion " + (deleted ? "successful" : "failed"));

                showDeletedPopup(event);
            } else {
                System.out.println("User canceled deletion.");
            }
        } else {
            System.out.println("Error: No recipe to delete");
        }
    }

    private void showDeletedPopup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/Deleted.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Deleted");
            popupStage.setScene(new Scene(root));

            // Wait for user to close popup, then go back to main menu
            popupStage.showAndWait();
            handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml");

        } catch (IOException e) {
            System.out.println("Error loading Deleted.fxml");
            e.printStackTrace();
        }
    }



    public void handleBackToMainMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 600);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
