package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.exceptions.EmployeeValidationException;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.controllers.forms.EmployeeFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller for managing employee data and interactions in the application.
 */
public class EmployeeController extends BaseController {
    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

    private SceneController sceneController;
    private final IEmployeeService employeeService;

    @FXML
    private ComboBox<String> dropdownMenu;
    @FXML
    private TableColumn<Employee, String> colHasEmail;
    @FXML
    private TableColumn<Employee, String> colHasPhone;
    @FXML
    private TableColumn<Employee, String> colHasPassword;
    @FXML
    private TableColumn<Employee, String> colFirstName;
    @FXML
    private TableColumn<Employee, String> colLastName;
    @FXML
    private TableColumn<Employee, String> colRole;
    @FXML
    private TableView<Employee> tableEmployees;

    //binding
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    /**
     * Constructor for the EmployeeController.
     * * @param employeeService The service for managing employee data.
     * @param sceneController The controller for managing scene transitions.
     */
    public EmployeeController(IEmployeeService employeeService, SceneController sceneController) {
        this.employeeService = employeeService;
        this.sceneController = sceneController;
        if (employeeService != null) {
            logger.info("EmployeeService injected successfully");
        } else {
            logger.error("Failed to inject EmployeeService");
        }
    }

    /**
     * Calls methods for toString for the tableview, so it displays correct values
     */

    public void initialize() {
        loadEmployees();
        FormatUtility.setTextCell(colFirstName, Employee::getFirstName);
        FormatUtility.setTextCell(colLastName, Employee::getLastName);
        FormatUtility.setTextCell(colRole, e -> "Employee");


        FormatUtility.setCheckmarkCell(colHasEmail, Employee::getEmail);
        FormatUtility.setCheckmarkCell(colHasPhone, Employee::getPhone);
        FormatUtility.setCheckmarkCell(colHasPassword, Employee::getPassword);


        tableEmployees.setItems(employees);
    }

    /**
     * Handles the "Add Employee" button action.
     * Opens a new window to add a new employee.
     * * @param actionEvent The action event triggered by the button.
     */
    @FXML
    private void addEmployeeButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/EmployeeForm.fxml"));
            Parent root = loader.load();

            EmployeeFormController formController = loader.getController();
            formController.setEmployeeService(employeeService);
            formController.setEmployee(null);

            Stage stage = new Stage();
            stage.setTitle("Add new Employee");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnShown(event -> root.requestFocus());
            stage.showAndWait();


            loadEmployees();
        } catch (EmployeeValidationException e) {
            showError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles the "Update Employee" button action.
     * Opens a new window to edit the selected employee.
     * * @param actionEvent The action event triggered by the button.
     */
    @FXML
    private void updateEmployeeButton(ActionEvent actionEvent) {
        Employee selectedEmployee = tableEmployees.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showError("Please select an employee to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/EmployeeForm.fxml"));

            Parent root = loader.load();

            EmployeeFormController formController = loader.getController();
            formController.setEmployeeService(employeeService);
            formController.setEmployee(selectedEmployee); // Pass selected employee to the form

            Stage stage = new Stage();
            stage.setTitle("Edit Employee");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnShown(event -> root.requestFocus());
            stage.showAndWait();

            loadEmployees(); // Refresh table after edit
        } catch (EmployeeValidationException e) {
            showError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load edit window: " + e.getMessage());
        }
    }
    /**
     * Loads employee data from the service and updates the table view.
     */
    private void loadEmployees() {
        logger.info("Loading Employees...");
        try {
            employees.setAll(employeeService.getAllEmployees());
            logger.info("Loaded Employees: {}", employees.size());
        } catch (EmployeeValidationException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles the "Delete Employee" button action.
     * Deletes the selected employee from the database and updates the table view.
     */
    @FXML
    private void deleteEmployeeButton() {
        Employee selectedEmployee = tableEmployees.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                employeeService.delete(selectedEmployee.getId());
                tableEmployees.getItems().remove(selectedEmployee);
            } catch (EmployeeValidationException e) {
                showError(e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Employee Chosen");
        }
    }


    /**
     * Handles the dropdown menu selection.
     * Switches to the selected scene.
     * * @param actionEvent The action event triggered by the dropdown menu.
     */
    @FXML
    public void handleComboBoxSelection(ActionEvent actionEvent) {
        try {
            String selectedOption = dropdownMenu.getValue();
            if (selectedOption != null) {
                switch (selectedOption) {
                    case "Booking Page":
                        sceneController.switchScene("BookingPage.fxml");
                        break;
                    case "Customer Page":
                        sceneController.switchScene("CustomerPage.fxml");
                        break;
                    case "Service Page":
                        sceneController.switchScene("ServicePage.fxml");
                        break;
                    case "Employee Page":
                        sceneController.switchScene("EmployeePage.fxml");
                        break;
                    default:
                        logger.warn("Invalid selection: " + selectedOption);
                        break;
                }
            }
        } catch (Exception e) {
            logger.error("Error in ComboBox selection handling: " + e.getMessage());
            showError("Error in ComboBox selection");
        }
    }

}
