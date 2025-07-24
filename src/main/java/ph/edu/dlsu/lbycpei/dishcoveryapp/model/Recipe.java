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

    public void setName(String name) { this.name = name; }
    public void setIngredients(List<String> ingredients) { this.parsedIngredients = ingredients; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
