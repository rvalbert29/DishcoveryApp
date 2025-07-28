package ph.edu.dlsu.lbycpei.dishcoveryapp.model;

import java.util.List;

public class Recipe {
    private String name;
    private List<String> parsedIngredients;
    private String instructions;
    private String imagePath; // instead of javafx.scene.image.Image

    public Recipe(String name, List<String> ingredients, String instructions, String imagePath) {
        this.name = name;
        this.parsedIngredients = ingredients;
        this.instructions = instructions;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public List<String> getIngredients() { return parsedIngredients; }
    public String getInstructions() { return instructions; }
    public String getImagePath() { return imagePath; }


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
