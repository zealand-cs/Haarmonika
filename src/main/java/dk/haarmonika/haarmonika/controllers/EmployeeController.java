package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.Database.DatabaseConnectionPool;
import dk.haarmonika.haarmonika.backend.db.daos.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.services.EmployeeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeeController implements ControllerInterface{
    private final EmployeeService employeeService;

    @FXML private TableColumn<Employee, String> colHasEmail;
    @FXML private TableColumn<Employee, String> colHasPhone;
    @FXML private TableColumn<Employee, String> colHasPassword;
    @FXML private TableColumn<Employee, String> colFirstName;
    @FXML private TableColumn<Employee, String> colLastName;
    @FXML private TableColumn<Employee, String> colRole;
    @FXML private TableView<Employee> tableEmployees;



    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    public EmployeeController(){
        this.employeeService = new EmployeeService(new EmployeeDao());
    }

    /**
     Calls methods for toString for the tableview, so it displays correct values
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


    @FXML
    private void addEmployeeButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/EmployeeForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add new Employee");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnShown(event -> root.requestFocus());

            stage.showAndWait();


            loadEmployees();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadEmployees() {
        try {
            employees.setAll(employeeService.getAllEmployees());
            System.out.println("Loaded Employees: " + employees.size()); // Debugging
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        //TO DO
    }

}
