// RecipeRepository.java
package ph.edu.dlsu.lbycpei.dishcoveryapp.data;

import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private static final List<Recipe> recipes = new ArrayList<>();

    public static void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public static List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }
}
