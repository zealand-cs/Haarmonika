package dk.haarmonika.haarmonika.controllers.forms;


import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.exceptions.EmployeeValidationException;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.controllers.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;

@Controller
public class EmployeeFormController extends BaseController {
    @FXML private Button saveButton;
    @FXML private Button createButton;


    private final IEmployeeService employeeService;
    private Employee selectedEmployee;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField passwordField;

    @Autowired
    public EmployeeFormController(IEmployeeService employeeService){
        this.employeeService = employeeService;
    }

    public void setEmployee(Employee employee) {
        this.selectedEmployee = employee;
        if (employee != null) {
            populateFields(employee);
            saveButton.setVisible(true);
            createButton.setVisible(false);
        } else {
            clearFields();
            saveButton.setVisible(false);
            createButton.setVisible(true);
        }
    }

    private void populateFields(Employee employee) {
        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        emailField.setText(employee.getEmail());
        phoneField.setText(employee.getPhone());
        passwordField.setText(employee.getPassword());
    }

    @FXML
    private void handleSaveButton(ActionEvent actionEvent) {
        try {
            validateAndProcessEmployee();
        } catch (EmployeeValidationException | SQLException e) {
            showError(e.getMessage());
        }
    }


    @FXML
    private void handleCreateButton(ActionEvent actionEvent) {
        handleSaveButton(actionEvent);
    }

    private void validateAndProcessEmployee() throws SQLException, EmployeeValidationException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();

        if (fieldsAreEmpty(firstName, lastName, email, phone, password)) {
            showError("All fields must be filled out!");
            return;
        }

        if (selectedEmployee == null) {
            createEmployee(firstName, lastName, email, phone, password);
        } else {
            updateEmployee(firstName, lastName, email, phone, password);
        }
    }

    private boolean fieldsAreEmpty(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    private void createEmployee(String firstName, String lastName, String email, String phone, String password) throws SQLException, EmployeeValidationException {
        Employee newEmployee = new Employee(firstName, lastName, email, phone, password);
        employeeService.createEmployee(newEmployee);
        showSuccess("Employee created successfully!");
    }
    private void updateEmployee(String firstName, String lastName, String email, String phone, String password) throws SQLException, EmployeeValidationException {
        selectedEmployee.setFirstName(firstName);
        selectedEmployee.setLastName(lastName);
        selectedEmployee.setEmail(email);
        selectedEmployee.setPhone(phone);
        selectedEmployee.setPassword(password);

        employeeService.updateEmployee(selectedEmployee);
        showSuccess("Employee updated successfully!");
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        passwordField.clear();
    }


}
