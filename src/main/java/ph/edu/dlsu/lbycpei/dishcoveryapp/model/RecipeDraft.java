// RecipeDraft.java
package ph.edu.dlsu.lbycpei.dishcoveryapp.model;

import javafx.scene.image.Image;
import java.util.List;

public class RecipeDraft {
    private String name;
    private List<String> ingredients;
    private String instructions;
    private Image image;

    public RecipeDraft(String name, List<String> ingredients, String instructions, Image image) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.image = image;
    }

    public String getName() { return name; }
    public List<String> getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    public Image getImage() { return image; }

    public void setName(String name) { this.name = name; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public void setImage(Image image) { this.image = image; }
}
