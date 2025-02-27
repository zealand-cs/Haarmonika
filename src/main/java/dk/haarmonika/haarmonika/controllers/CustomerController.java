/*
package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.Database;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;


public class CustomerController implements ControllerInterface{
    private final CustomerService CustomerService;


    @FXML private TableColumn<Customer, String> colHasEmail;
    @FXML private TableColumn<Customer, String> colHasPhone;
    @FXML private TableColumn<Customer, String> colHasPassword;
    @FXML private TableColumn<Customer, String> colFirstName;
    @FXML private TableColumn<Customer, String> colLastName;
    @FXML private TableColumn<Customer, String> colRole;
    @FXML private TableView<Customer> tableCustomers;

    private ObservableList customers = FXCollections.observableArrayList();

    public CustomerController(){
        this.CustomerService = new CustomerService(new CustomerDao(Database.getInstance().getConnection()));
    }

    public void initialize() {
            loadCustomers();
            FormatUtility.setTextCell(colFirstName, Customer::getFirstName);
            FormatUtility.setTextCell(colLastName, Customer::getLastName);
            FormatUtility.setTextCell(colRole, e -> "Customer");


            FormatUtility.setCheckmarkCell(colHasEmail, Customer::getEmail);
            FormatUtility.setCheckmarkCell(colHasPhone, Customer::getPhone);
            FormatUtility.setCheckmarkCell(colHasPassword, Customer::getPassword);


            tableCustomers.setItems(customers);
    }




    @FXML
    private void addCustomerButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/CustomerForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Customer creation");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnShown(event -> root.requestFocus());


            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadCustomers() {
        try {
            customers.setAll(customerService.getAllCustomers());
            System.out.println("Loaded Customeers: " + customers.size()); // Debugging
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }





*/
