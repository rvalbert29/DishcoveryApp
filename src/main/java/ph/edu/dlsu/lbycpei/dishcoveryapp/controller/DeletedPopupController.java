package ph.edu.dlsu.lbycpei.dishcoveryapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeletedPopupController {

    @FXML
    private Button closeButton;

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }
}
