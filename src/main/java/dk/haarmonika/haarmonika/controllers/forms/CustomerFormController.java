/*
package dk.haarmonika.haarmonika.controllers.forms;

import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.exceptions.CustomerValidationException;
import dk.haarmonika.haarmonika.backend.exceptions.EmployeeValidationException;
import dk.haarmonika.haarmonika.backend.services.ICustomerService;
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
public class CustomerFormController extends BaseController {
    @FXML private Button createButton;
    @FXML private Button saveButton;


    private final ICustomerService customerService;
    private Customer selectedCustomer;


    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField passwordField;

    @Autowired
    public CustomerFormController(ICustomerService customerService){
        this.customerService = customerService;
    }

    public void setCustomer(Customer customer) {
        this.selectedCustomer = customer;
        if (customer != null) {
            populateFields(customer);
            saveButton.setVisible(true);
            createButton.setVisible(false);
        } else {
            clearFields();
            saveButton.setVisible(false);
            createButton.setVisible(true);
        }
    }

    private void populateFields(Customer customer) {
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        emailField.setText(customer.getEmail());
        phoneField.setText(customer.getPhone());
        passwordField.setText(customer.getPassword());
    }

    @FXML
    private void handleSaveButton(ActionEvent actionEvent) {
        try {
            validateAndProcessCustomer();
        } catch (CustomerValidationException | SQLException e) {
            showError(e.getMessage());
        }
    }
    @FXML
    private void handleCreateButton(ActionEvent actionEvent) {
        handleSaveButton(actionEvent);
    }

    private void validateAndProcessCustomer() throws SQLException, CustomerValidationException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();

        if (fieldsAreEmpty(firstName, lastName, email, phone, password)) {
            showError("All fields must be filled out!");
            return;
        }

        if (selectedCustomer == null) {
            createCustomer(firstName, lastName, email, phone, password);
        } else {
            updateCustomer(firstName, lastName, email, phone, password);
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
    private void createCustomer(String firstName, String lastName, String email, String phone, String password) throws SQLException, CustomerValidationException {
        Customer newCustomer = new Customer(firstName, lastName, email, phone, password);
        customerService.createCustomer(newCustomer);
        showSuccess("Customer created successfully!");
    }
    private void updateCustomer(String firstName, String lastName, String email, String phone, String password) throws SQLException, CustomerValidationException {
        selectedCustomer.setFirstName(firstName);
        selectedCustomer.setLastName(lastName);
        selectedCustomer.setEmail(email);
        selectedCustomer.setPhone(phone);
        selectedCustomer.setPassword(password);

        customerService.updateCustomer(selectedCustomer);
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
*/
