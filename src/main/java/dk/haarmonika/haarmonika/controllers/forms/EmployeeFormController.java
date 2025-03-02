package dk.haarmonika.haarmonika.controllers.forms;


import dk.haarmonika.haarmonika.backend.db.daos.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.exceptions.EmployeeValidationException;
import dk.haarmonika.haarmonika.backend.services.EmployeeService;
import dk.haarmonika.haarmonika.controllers.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class EmployeeFormController extends BaseController {


    private final EmployeeService employeeService;


    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField passwordField;

    public EmployeeFormController(){
        this.employeeService = new EmployeeService(new EmployeeDao());
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

        } catch (EmployeeValidationException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error creating employee: " + e.getMessage());
        }
    }




    }
