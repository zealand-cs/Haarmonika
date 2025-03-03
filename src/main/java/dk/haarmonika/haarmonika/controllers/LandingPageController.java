package dk.haarmonika.haarmonika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class LandingPageController implements ControllerInterface {
    @FXML
    private Label welcomeText;



    @Override
    public void refresh() {

    }

    @FXML
    public void onEmployeeButtonClick(ActionEvent actionEvent) throws IOException {
        SceneController.switchScene("EmployeePage.fxml");
    }

    public void onCustomerButtonClick(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene("CustomerPage.fxml");
    }
}