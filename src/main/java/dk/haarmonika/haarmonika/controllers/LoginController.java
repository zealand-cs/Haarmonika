package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginController{
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    @FXML    private Button loginButton;
    @FXML    private TextField usernameField;
    @FXML    private PasswordField passwordField;
    @FXML    private Label errorLabel;
    private SceneController sceneController;
    private final IEmployeeService employeeService;

    public LoginController(IEmployeeService employeeService, SceneController sceneController){
        this.employeeService = employeeService;
        this.sceneController = sceneController;
        if (employeeService != null) {
            logger.info("EmployeeService injected successfully");
        } else {
            logger.error("Failed to inject EmployeeService");
        }
    }

    public void initialize() {
        Platform.runLater(() -> {
            usernameField.getScene().getRoot().requestFocus();
        });
    }

    @FXML
    private void handleLogin() {
        /*String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            errorLabel.setText("Login successful!");
            errorLabel.setStyle("-fx-text-fill: green;");
            // TODO: Navigate to the main application window
        } else {
            errorLabel.setText("Invalid username or password");
            errorLabel.setStyle("-fx-text-fill: red;");
        }*/
    }

    private boolean authenticate(String username, String password) {

        return "admin".equals(username) && "password".equals(password);
    }
}