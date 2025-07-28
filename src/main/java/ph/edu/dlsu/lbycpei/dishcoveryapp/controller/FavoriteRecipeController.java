package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpei.dishcoveryapp.data.RecipeRepository;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FavoriteRecipeController {

    @FXML private GridPane recipesGridPane;
    @FXML private Button returnHomeButton;

    @FXML
    private void initialize() {
        returnHomeButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
        loadFavoriteRecipes();
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

    private void loadFavoriteRecipes() {
        List<Recipe> favorites = RecipeRepository.getFavoriteRecipes();
        recipesGridPane.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Recipe recipe : favorites) {
            VBox recipeBox = createRecipeBox(recipe);
            recipesGridPane.add(recipeBox, column, row);

            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    private VBox createRecipeBox(Recipe recipe) {
        VBox box = new VBox(10);
        box.setPrefSize(220, 220); // FIXED SIZE
        box.setMaxSize(220, 220);
        box.setMinSize(220, 220);
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-border-color: lightgray; -fx-padding: 10; -fx-alignment: center;");
        box.setPadding(new Insets(10));

        ImageView imageView = new ImageView();
        imageView.setFitWidth(180);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        if (recipe.getImagePath() != null && new File(recipe.getImagePath()).exists()) {
            imageView.setImage(new Image(new File(recipe.getImagePath()).toURI().toString()));
        }

        Text nameText = new Text(recipe.getName());
        nameText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Trashcan button
        ImageView trashIcon = new ImageView(new Image(getClass().getResourceAsStream("/ph/edu/dlsu/lbycpei/dishcoveryapp/images/trash.jpg")));
        trashIcon.setFitWidth(20);
        trashIcon.setFitHeight(20);
        Button removeButton = new Button();
        removeButton.setGraphic(trashIcon);
        removeButton.setStyle("-fx-background-color: transparent;");
        removeButton.setOnAction(e -> showConfirmRemovePopup(recipe));

        //  trashcan in top-right using a StackPane
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(220, 220);
        stackPane.getChildren().addAll(box, removeButton);
        StackPane.setAlignment(removeButton, Pos.TOP_RIGHT);
        StackPane.setMargin(removeButton, new Insets(5, 5, 0, 0)); // top, right, bottom, left

        // Add content to VBox
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().addAll(imageView, nameText);

        // Make recipe clickable
        box.setOnMouseClicked(event -> openRecipe(recipe));

        return new VBox(stackPane);
    }


    private void openRecipe(Recipe recipe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeDisplay.fxml"));
            Parent root = loader.load();

            RecipeDisplayController controller = loader.getController();
            controller.setRecipe(recipe);

            Stage stage = (Stage) returnHomeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showConfirmRemovePopup(Recipe recipe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/ConfirmRemoveFave.fxml"));
            Parent root = loader.load();

            Button closeButton = (Button) loader.getNamespace().get("closeButton");
            Button confirmButton = (Button) loader.getNamespace().get("confirmButton");

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));

            closeButton.setOnAction(e -> popupStage.close());

            confirmButton.setOnAction(e -> {
                RecipeRepository.removeFavoriteRecipe(recipe);
                RecipeRepository.saveFavoritesToJson();
                popupStage.close();
                showRemovedPopup();
                loadFavoriteRecipes(); // refresh the grid
            });

            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRemovedPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/FaveRemoved.fxml"));
            Parent root = loader.load();

            Button closeButton = (Button) loader.getNamespace().get("closeButton");

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));

            closeButton.setOnAction(e -> popupStage.close());

            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
