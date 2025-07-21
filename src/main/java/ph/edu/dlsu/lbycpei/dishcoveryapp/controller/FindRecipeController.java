package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FindRecipeController {

    @FXML private TextField ingredientSearchField;
    @FXML private Button matchRecipesButton;
    @FXML private Button backButton;

    @FXML
    private void initialize() {
        backButton.setOnAction(event -> handleBackToMainMenu(event, "/ph/edu/dlsu/lbycpei/dishcoveryapp/MainMenu.fxml"));
        matchRecipesButton.setOnAction(this::handleSearchRecipe);

        // Add search functionality here
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
        }
    }


    private Recipe getRecipeByName(String recipeName) {
        if (recipeName.equalsIgnoreCase("Chicken Adobo")) {
            List<String> ingredients = Arrays.asList(
                    "2 lbs chicken", "3 pieces dried bay leaves", "4 tablespoons soy sauce",
                    "6 tablespoons white vinegar", "5 cloves garlic", "1 1/2 cups water",
                    "3 tablespoons cooking oil", "1 teaspoon sugar", "1/4 teaspoon salt",
                    "1 teaspoon whole peppercorn"
            );

            String instructions = "1. Combine chicken, soy sauce, and garlic in a large bowl. Mix well. "
                    + "Marinate the chicken for at least 1 hour.\n"
                    + "2. Heat a cooking pot. Pour cooking oil.\n"
                    + "3. Pan-fry the chicken. Add marinade, water, bay leaves, peppercorn.\n"
                    + "4. Simmer 30 mins. Add vinegar, sugar, salt. Cook 10 more mins.\n"
                    + "5. Serve hot.";

            Image recipeImage = new Image(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/images/adobongManacc.jpg").toExternalForm());

            return new Recipe("Chicken Adobo", ingredients, instructions, recipeImage);
        }

        else if (recipeName.equalsIgnoreCase("Banana Bread")) {
            List<String> ingredients = Arrays.asList(
                    "1 ½ cups all-purpose flour",
                    "1 cup sugar",
                    "1 teaspoon salt",
                    "1 teaspoon baking soda",
                    "2 raw eggs",
                    "1 cup mashed banana",
                    "½ cup cooking oil",
                    "1 teaspoon vanilla essence",
                    "½ cup chocolate chips (optional)",
                    "½ cup chopped walnuts (optional)"
            );

            String instructions = "1. Preheat your oven to 350°F (175°C).\n"
                    + "2. In a large bowl, whisk together the flour, sugar, salt, and baking soda until well combined.\n"
                    + "3. To the same bowl, add the eggs, mashed banana, cooking oil, and vanilla essence. Mix thoroughly with a spatula until you achieve a thick, smooth batter.\n"
                    + "4. If using the optional chocolate chips/chopped walnuts, gently fold them in.\n"
                    + "5. Grease a loaf pan evenly with cooking spray or oil. Pour the batter into the pan, spreading it evenly.\n"
                    + "6. Place the pan in the preheated oven and bake for about 1 hour, or until a toothpick inserted comes out clean.\n"
                    + "7. Remove the pan from the oven and let the banana bread cool inside it for 10–15 minutes. Then, transfer it to a wire rack to cool completely before slicing.\n"
                    + "8. Serve at room temperature or chilled (if preferred).\n"
                    + "9. Store leftovers in an airtight container at room temperature for 2–3 days or in the fridge for up to 5 days.";

            Image recipeImage = new Image(getClass().getResource("/ph/edu/dlsu/lbycpei/dishcoveryapp/images/bananaBread.jpg").toExternalForm());

            return new Recipe("Banana Bread", ingredients, instructions, recipeImage);
        }

        return null; // no match found
    }


}
