
package dk.haarmonika.haarmonika.controllers;


import dk.haarmonika.haarmonika.backend.db.entities.Customer;

import dk.haarmonika.haarmonika.backend.exceptions.CustomerValidationException;
import dk.haarmonika.haarmonika.backend.services.ICustomerService;
import dk.haarmonika.haarmonika.controllers.forms.CustomerFormController;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;



public class CustomerController extends BaseController implements ControllerInterface{
    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    private final ICustomerService customerService;


    @FXML private TableColumn<Customer, String> colHasEmail;
    @FXML private TableColumn<Customer, String> colHasPhone;
    @FXML private TableColumn<Customer, String> colHasPassword;
    @FXML private TableColumn<Customer, String> colFirstName;
    @FXML private TableColumn<Customer, String> colLastName;
    @FXML private TableColumn<Customer, String> colRole;
    @FXML private TableView<Customer> tableCustomers;

    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public CustomerController(ICustomerService customerService){
        this.customerService = customerService;
        if (customerService != null) {
            logger.info("customerService injected successfully");
        } else {
            logger.error("Failed to inject customerService");
        }
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

            CustomerFormController formController = loader.getController();
            formController.setCustomerService(customerService);
            formController.setCustomer(null);

            Stage stage = new Stage();
            stage.setTitle("Customer creation");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnShown(event -> root.requestFocus());
            stage.showAndWait();

            loadCustomers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateCustomerButton(ActionEvent actionEvent) {
        Customer selectedCustomer = tableCustomers.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showError("Please select a Customer to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/CustomerForm.fxml"));
            Parent root = loader.load();

            CustomerFormController formController = loader.getController();
            formController.setCustomerService(customerService);
            formController.setCustomer(selectedCustomer);

            Stage stage = new Stage();
            stage.setTitle("Edit Customer");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnShown(event -> root.requestFocus());
            stage.showAndWait();

            loadCustomers();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load edit window: " + e.getMessage());
        }
    }

    private void loadCustomers() {
        logger.info("Loading Customers...");
        try {
            customers.setAll(customerService.getAllCustomers());
            logger.info("Loaded Customeers: " + customers.size());
        } catch (CustomerValidationException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteCustomerButton() {
        Customer selectedCustomer = tableCustomers.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            try {
                customerService.delete(selectedCustomer.getId());
                tableCustomers.getItems().remove(selectedCustomer);
            } catch (CustomerValidationException e) {
                showError(e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Customer Chosen");
        }
    }


}








