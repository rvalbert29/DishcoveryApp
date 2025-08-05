package ph.edu.dlsu.lbycpei.dishcoveryapp.model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Recipe {

    private String name;
    private Map<String, String> ingredientsWithQuantities; // quantity -> ingredient name
    private String instructions;
    private String imagePath;


    public Recipe(String name, Map<String, String> ingredientsWithQuantities, String instructions, String imagePath) {
        this.name = name;
        this.ingredientsWithQuantities = ingredientsWithQuantities;
        this.instructions = instructions;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getIngredientsWithQuantities() {
        return ingredientsWithQuantities;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImagePath() {
        return imagePath;
    }

    // This is what PantryManager uses for matching
    public List<String> getIngredientNames() {
        return new ArrayList<>(ingredientsWithQuantities.values());
    }


}
