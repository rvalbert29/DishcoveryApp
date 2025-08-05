package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpei.dishcoveryapp.data.RecipeRepository;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PantryManagerController extends BaseController {

    @FXML private TextField ingredientSearchField;
    @FXML private GridPane recipesGridPane;
    @FXML private Button backButton;
    @FXML private Button matchRecipeButton;

    @FXML
    private void initialize() {
        backButton.setOnAction(this::handleBackToMainMenu);
        matchRecipeButton.setOnAction(event -> handleMatchRecipes());
        // Initially, the grid pane should be empty.
    }

    private void handleMatchRecipes() {
        String input = ingredientSearchField.getText().trim().toLowerCase();
        recipesGridPane.getChildren().clear();

        if (input.isEmpty()) {
            Text instructionText = new Text("Enter your ingredients separated by commas and click 'Match Recipe'.");
            recipesGridPane.add(instructionText, 0, 0);
            return;
        }

        List<String> pantryIngredients = Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::normalizeIngredient)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (pantryIngredients.isEmpty()) {
            Text instructionText = new Text("Enter your ingredients separated by commas and click 'Match Recipe'.");
            recipesGridPane.add(instructionText, 0, 0);
            return;
        }

        List<Recipe> allRecipes = RecipeRepository.getRecipes();
        int row = 0;
        int col = 0;
        boolean hasMatches = false;

        for (Recipe recipe : allRecipes) {
            List<String> normalizedRecipeIngredients = recipe.getIngredientNames().stream()
                    .map(this::normalizeIngredient)
                    .collect(Collectors.toList());

            boolean containsAny = pantryIngredients.stream()
                    .anyMatch(pantryIng ->
                            normalizedRecipeIngredients.stream()
                                    .anyMatch(recipeIng -> recipeIng.contains(pantryIng))
                    );

            if (containsAny) {
                VBox card = createRecipeCard(recipe, pantryIngredients);
                recipesGridPane.add(card, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
                hasMatches = true;
            }
        }

        if (!hasMatches) {
            Text noMatchText = new Text("No recipes matched your ingredients.");
            recipesGridPane.add(noMatchText, 0, 0);
        }
    }

    private VBox createRecipeCard(Recipe recipe, List<String> pantryIngredients) {
        VBox card = new VBox(5);

        card.setMaxWidth(250);
        card.setMaxHeight(200);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
        card.setOnMouseClicked(event -> openRecipeDisplay(recipe));

        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(200);
        card.setAlignment(Pos.CENTER);

        String imagePath = recipe.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            File imgFile = new File(System.getProperty("user.dir"), imagePath.replace("\\", "/"));
            if (imgFile.exists()) {
                imageView.setImage(new Image(imgFile.toURI().toString()));
            } else {
                imageView.setImage(new Image(getClass().getResourceAsStream("/ph/edu/dlsu/lbycpei/dishcoveryapp/images/default_recipe.jpg")));
            }
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/ph/edu/dlsu/lbycpei/dishcoveryapp/images/default_recipe.jpg")));
        }

        Text name = new Text(recipe.getName());
        name.setWrappingWidth(220);
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        List<String> normalizedRecipeIngredients = recipe.getIngredientNames().stream()
                .map(this::normalizeIngredient)
                .collect(Collectors.toList());

        List<String> missingIngredients = normalizedRecipeIngredients.stream()
                .filter(recipeIng ->
                        pantryIngredients.stream().noneMatch(pantryIng -> recipeIng.contains(pantryIng))
                )
                .collect(Collectors.toList());

        Text status = new Text(missingIngredients.isEmpty()
                ? "✅ Complete"
                : "❌ Missing: " + String.join(", ", missingIngredients));

        status.setStyle("-fx-font-size: 12px;");
        status.setWrappingWidth(220);
        card.getChildren().addAll(imageView, name, status);
        return card;
    }

    private void openRecipeDisplay(Recipe recipe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/RecipeDisplay.fxml"));
            Parent root = loader.load();

            RecipeDisplayController controller = loader.getController();
            controller.setRecipe(recipe);

            Stage stage = (Stage) recipesGridPane.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String normalizeIngredient(String ingredient) {
        return ingredient.toLowerCase()
                .replaceAll("\\d+", "")
                .replaceAll("[^a-zA-Z ]", "")
                .replaceAll("\\b(of|pcs|pieces|piece|cups|tablespoons|teaspoons|strands|slices|grams|ml|g|kg|l)\\b", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}