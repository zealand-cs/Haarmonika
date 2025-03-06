package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.entities.Service;
import dk.haarmonika.haarmonika.backend.exceptions.ServiceValidationException;
import dk.haarmonika.haarmonika.backend.services.IServiceService;
import dk.haarmonika.haarmonika.controllers.forms.ServiceFormController;
import javafx.beans.property.SimpleIntegerProperty;
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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.sql.SQLException;

public class ServiceController extends BaseController{
    private static final Logger logger = LogManager.getLogger(ServiceController.class);

    private final IServiceService serviceService;
    private SceneController sceneController;


    @FXML
    private ComboBox<String> dropdownMenu;
    @FXML
    private TableColumn<Service, String> colName;
    @FXML
    private TableColumn<Service, Integer> colDuration;
    @FXML
    private TableView<Service> tableServices;

    private ObservableList<Service> services = FXCollections.observableArrayList();

    public ServiceController(IServiceService serviceService, SceneController sceneController) {
        this.serviceService = serviceService;
        this.sceneController = sceneController;
        if (serviceService != null) {
            logger.info("serviceService injected successfully");
        } else {
            logger.error("Failed to inject ServiceService");
        }
    }

    public void initialize() {
        loadServices();

        // Set the cell factory for the duration column
        FormatUtility.setTextCell(colName, Service::getName); // Name column setup
        colDuration.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getDuration()).asObject()); // Duration column setup

        tableServices.setItems(services);
    }

    private void loadServices() {
        logger.info("Loading services...");
        try {
            services.setAll(serviceService.getAllServices());
            logger.info("Loaded Services: {}", services.size());
        } catch (ServiceValidationException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addServiceButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/ServiceForm.fxml"));
            Parent root = loader.load();

            ServiceFormController formController = loader.getController();
            formController.setServiceService(serviceService);
            formController.setService(null);

            Stage stage = new Stage();
            stage.setTitle("Add new Service");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnShown(event -> root.requestFocus());
            stage.showAndWait();

            loadServices();
        } catch (ServiceValidationException e) {
            showError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateServiceButton(ActionEvent actionEvent) {
        Service selectedService = tableServices.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showError("Please select a service to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/ServiceForm.fxml"));
            Parent root = loader.load();

            ServiceFormController formController = loader.getController();
            formController.setServiceService(serviceService);
            formController.setService(selectedService);

            Stage stage = new Stage();
            stage.setTitle("Edit Service");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadServices();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteServiceButton() {
        Service selectedService = tableServices.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            try {
                serviceService.delete(selectedService.getId());
                tableServices.getItems().remove(selectedService);
            } catch (ServiceValidationException e) {
                showError(e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showError("No service selected.");
        }
    }


    @FXML
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
                    logger.warn("Invalid selection in dropdown: " + selectedOption);
                    break;
            }
        }
    }
}
