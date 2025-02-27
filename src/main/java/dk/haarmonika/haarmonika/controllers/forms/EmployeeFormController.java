package dk.haarmonika.haarmonika.controllers.forms;

import dk.haarmonika.haarmonika.backend.db.Database;
import dk.haarmonika.haarmonika.backend.db.daos.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.services.EmployeeService;
import dk.haarmonika.haarmonika.controllers.EmployeeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class EmployeeFormController {


    private final EmployeeService employeeService;


    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField passwordField;

    public EmployeeFormController(){
        this.employeeService = new EmployeeService(new EmployeeDao(Database.getInstance().getConnection()));
    }
    public void handleCreateButton(ActionEvent actionEvent) {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = passwordField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                showError("All fields must be filled out!");
                return;
        }
            Employee newEmployee = new Employee(firstName, lastName, email, phone, password);
            employeeService.createEmployee(newEmployee);


            showSuccess("Employee created successfully! " + firstName + " " + lastName);


    } catch (SQLException e) {
            e.printStackTrace();
            showError("Error creating employee: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }
