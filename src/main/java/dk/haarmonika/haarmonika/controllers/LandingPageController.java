package dk.haarmonika.haarmonika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;


public class LandingPageController implements ControllerInterface {

    private SceneController sceneController;
    @FXML
    private Label welcomeText;


    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @Override
    public void refresh() {

    }

    @FXML
    public void onEmployeeButtonClick(ActionEvent actionEvent) throws IOException {
        sceneController.switchScene("EmployeePage.fxml");
    }

    @FXML
    public void onCustomerButtonClick(ActionEvent actionEvent) throws IOException{
        sceneController.switchScene("CustomerPage.fxml");
    }
}