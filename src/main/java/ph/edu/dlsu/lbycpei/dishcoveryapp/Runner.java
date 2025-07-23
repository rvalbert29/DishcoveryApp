package ph.edu.dlsu.lbycpei.dishcoveryapp;

//hello test

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Runner extends Application {
    // Variables to store initial click position
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddRecipeWithImage.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 600);
        stage.setTitle("Dishcovery App");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);

        // Make the window draggable
        scene.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}

