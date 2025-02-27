package dk.haarmonika.haarmonika.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LandingPageController implements ControllerInterface {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void refresh() {

    }
}