package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.Database;
import dk.haarmonika.haarmonika.backend.db.daos.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.services.EmployeeService;
import dk.haarmonika.haarmonika.controllers.forms.EmployeeFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeeController implements ControllerInterface{

    public ListView employeeListView;
    private final EmployeeService employeeService;
    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    public EmployeeController(){
        this.employeeService = new EmployeeService(new EmployeeDao(Database.getInstance().getConnection()));
    }

    public void initialize() {
        loadEmployees();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadEmployees() {
        try {
            employees.clear();
            employees.addAll(employeeService.getAllEmployess());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        //TO DO
    }
}
