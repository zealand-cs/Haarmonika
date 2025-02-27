package dk.haarmonika.haarmonika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LandingPageController implements ControllerInterface {
    @FXML
    private Label welcomeText;



    @Override
    public void refresh() {

    }

    @FXML
    public void onEmployeeButtonClick(ActionEvent actionEvent) {
        SceneController.switchScene("EmployeePage.fxml");
    }
}