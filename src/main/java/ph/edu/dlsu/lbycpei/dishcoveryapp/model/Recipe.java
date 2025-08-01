package ph.edu.dlsu.lbycpei.dishcoveryapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Recipe {
    private String name;
    private Map<String, String> ingredientsWithQuantities;
    private String instructions;
    private String imagePath;

    public Recipe(String name, Map<String, String> ingredientsWithQuantities, String instructions, String imagePath) {
        this.name = name;
        this.ingredientsWithQuantities = ingredientsWithQuantities;
        this.instructions = instructions;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public Map<String, String> getIngredientsWithQuantities() { return ingredientsWithQuantities; }
    public String getInstructions() { return instructions; }
    public String getImagePath() { return imagePath; }

    // Helper method to get just ingredient names (for searching)
    public List<String> getIngredientNames() {
        return new ArrayList<>(ingredientsWithQuantities.keySet());
    }

    //used so the entries in the favorite will not be duplicated
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Recipe other = (Recipe) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}