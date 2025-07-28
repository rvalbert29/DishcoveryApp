package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NotFoundController {

    @FXML
    private Button closeButton1;

    @FXML
    private void initialize() {
        closeButton1.setOnAction(event -> {
            Stage stage = (Stage) closeButton1.getScene().getWindow();
            stage.close();
        });
    }
}
