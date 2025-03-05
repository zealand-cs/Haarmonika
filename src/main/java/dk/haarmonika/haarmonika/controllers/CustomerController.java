
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class CustomerController extends BaseController implements ControllerInterface{
    private static final Logger logger = LogManager.getLogger(CustomerController.class);


    private final ICustomerService customerService;


    private SceneController sceneController;

    @FXML private ComboBox<String> dropdownMenu;
    @FXML private TableColumn<Customer, String> colHasEmail;
    @FXML private TableColumn<Customer, String> colHasPhone;
    @FXML private TableColumn<Customer, String> colHasPassword;
    @FXML private TableColumn<Customer, String> colFirstName;
    @FXML private TableColumn<Customer, String> colLastName;
    @FXML private TableColumn<Customer, String> colRole;
    @FXML private TableView<Customer> tableCustomers;

    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public CustomerController(ICustomerService customerService, SceneController sceneController){
        this.customerService = customerService;
        this.sceneController = sceneController;
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
        logger.info("Loading customers...");

        try {
            // Fetch all customers from the service
            List<Customer> fetchedCustomers = customerService.getAllCustomers();

            // Ensure 'customers' is initialized before modifying it
            if (customers == null) {
                customers = FXCollections.observableArrayList();
            }

            // Populate the TableView with new data
            customers.setAll(fetchedCustomers);

            // Log the number of customers loaded
            logger.info("Loaded " + customers.size() + " customers.");

            // Optional: Check if no customers were loaded
            if (customers.isEmpty()) {
                logger.warn("No customers were loaded.");
            }

        } catch (CustomerValidationException e) {
            // Log validation error and show error message
            logger.error("Customer validation error: " + e.getMessage(), e);
            showError("Customer validation error: " + e.getMessage());

        } catch (SQLException e) {
            // Log SQL error and print stack trace for debugging
            logger.error("Database error while loading customers", e);
            e.printStackTrace();
            showError("An error occurred while loading customer data. Please try again.");
        } catch (Exception e) {
            // Catch any unexpected exceptions and log them
            logger.error("Unexpected error occurred while loading customers", e);
            showError("An unexpected error occurred. Please try again.");
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


    public void handleComboBoxSelection(ActionEvent actionEvent) {
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
                    logger.warn("Ugyldig valg i dropdown: " + selectedOption);
                    break;
            }
        }
    }
}








