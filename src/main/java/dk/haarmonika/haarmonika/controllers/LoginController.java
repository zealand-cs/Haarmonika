package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;


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
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (employeeService.validateEmployee(username, password)) {
                errorLabel.setText("Login successful!");
                errorLabel.setStyle("-fx-text-fill: green;");
                sceneController.switchScene("BookingPage.fxml");
            } else {
                errorLabel.setText("Invalid username or password");
                errorLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (SQLException e) {
            logger.error("Error during login: {}", e.getMessage());
            errorLabel.setText("An error occurred during login.");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private boolean authenticate(String username, String password) {

        return "admin".equals(username) && "password".equals(password);
    }
}