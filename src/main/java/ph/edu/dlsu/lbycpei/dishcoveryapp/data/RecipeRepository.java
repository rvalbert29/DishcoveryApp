package ph.edu.dlsu.lbycpei.dishcoveryapp.data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import ph.edu.dlsu.lbycpei.dishcoveryapp.model.Recipe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    private static final String FILE_PATH = "data/recipes.json";
    private static  List<Recipe> recipes = new ArrayList<>();

    static {
        loadRecipesFromFile(); // Load when class is first used
    }

    public static boolean addRecipe(Recipe recipe) {
        recipes.add(recipe);
        saveRecipesToFile();
        return true;
    }

    public static List<Recipe> getRecipes() {
        return new ArrayList<>(recipes); // Return a copy
    }

    public static boolean deleteRecipe(Recipe recipe) {
        boolean removed = recipes.remove(recipe);
        if (removed) {
            saveRecipesToFile();
        }
        return removed;
    }

    private static boolean saveRecipesToFile() {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();

        try (Writer writer = new FileWriter(FILE_PATH)) {
            new Gson().toJson(recipes, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void loadRecipesFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (Reader reader = new FileReader(file)) {
            if (file.length() == 0) return;

            recipes = new Gson().fromJson(reader, new TypeToken<List<Recipe>>() {}.getType());

            // Ensure null fallback
            if (recipes == null) {
                recipes = new ArrayList<>();
            }

        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            recipes = new ArrayList<>(); // fallback to prevent crash
        }
    }

    private static List<Recipe> favoriteRecipes = new ArrayList<>();

    public static void addFavorite(Recipe recipe) {
        if (!favoriteRecipes.contains(recipe)) {
            favoriteRecipes.add(recipe);
        }
    }

    public static List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }
}

