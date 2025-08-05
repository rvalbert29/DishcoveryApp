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
import javafx.stage.Modality;
import ph.edu.dlsu.lbycpei.dishcoveryapp.data.RecipeRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RecipeDisplayController {

    @FXML private ImageView recipeImage;
    @FXML private Text recipeTitle;
    @FXML private ListView<String> ingredientsList;
    @FXML private TextArea instructionsText;
    @FXML private Button backButton;
    @FXML private Button favoriteButton;


    private Recipe recipe;

    @FXML
    private void initialize() {
        backButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
        favoriteButton.setOnAction(this::handleAddToFavorites);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        recipeTitle.setText(recipe.getName());

        // Convert the map to display strings "quantity ingredient"
        List<String> displayIngredients = new ArrayList<>();
        for (Map.Entry<String, String> entry : recipe.getIngredientsWithQuantities().entrySet()) {
            String display = entry.getValue().isEmpty() ?
                    entry.getKey() :
                    entry.getKey() + " " + entry.getValue();
            displayIngredients.add(display);
        }

        ingredientsList.getItems().setAll(displayIngredients);
        instructionsText.setText(recipe.getInstructions());

        if (recipe.getImagePath() != null) {
            try {
                File imageFile = new File(recipe.getImagePath());
                if (imageFile.exists()) {
                    recipeImage.setImage(new Image(imageFile.toURI().toString()));
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
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
            System.out.println("Attempting to delete: " + recipe.getName());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/ConfirmDelete.fxml"));
                Parent root = loader.load();

                // Access the controller to get button references
                ConfirmDeleteController controller = loader.getController();
                Button yesButton = controller.yesButton;
                Button cancelButton = controller.cancelButton;

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setTitle("Confirm Delete");
                popupStage.setScene(new Scene(root));

                // YES deletes the recipe
                yesButton.setOnAction(e -> {
                    boolean deleted = RecipeRepository.deleteRecipe(recipe);
                    System.out.println("Deletion " + (deleted ? "successful" : "failed"));
                    popupStage.close(); // close confirmation popup
                    showDeletedPopup(event); // show success
                });

                // CANCEL just closes the confirmation popup
                cancelButton.setOnAction(e -> {
                    System.out.println("User canceled deletion.");
                    popupStage.close();
                });

                popupStage.showAndWait();

            } catch (IOException e) {
                System.out.println("Error loading ConfirmDelete.fxml");
                e.printStackTrace();
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
            popupStage.showAndWait();
            handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml");

        } catch (IOException e) {
            System.out.println("Error loading Deleted.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddToFavorites(ActionEvent event) {
        if (recipe != null) {
            boolean added = RecipeRepository.addFavoriteRecipe(recipe);

            if (added) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/Favorited.fxml"));
                    Parent root = loader.load();

                    Stage popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.setTitle("Added to Favorites");
                    popupStage.setScene(new Scene(root));

                    // Access buttons
                    Button closeButton = (Button) loader.getNamespace().get("closeButton");
                    Button viewButton = (Button) loader.getNamespace().get("viewButton");

                    closeButton.setOnAction(e -> popupStage.close());
                    viewButton.setOnAction(e -> {
                        popupStage.close();
                        showFavoritesScene(event);
                    });

                    popupStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showInFavoritesPopup(); // If already in favorites, displays the popup
            }
        }
    }


    private void showFavoritesScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/FavoriteRecipe.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showInFavoritesPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/InFaves.fxml"));
            AnchorPane root = loader.load();

            // Access the Close button via fx:id
            Button closeButton = (Button) root.lookup("#closeButton");

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Already in Favorites");

            // Close action
            if (closeButton != null) {
                closeButton.setOnAction(e -> popupStage.close());
            }

            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
