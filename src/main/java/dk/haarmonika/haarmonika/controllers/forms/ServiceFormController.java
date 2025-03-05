package dk.haarmonika.haarmonika.controllers.forms;

import dk.haarmonika.haarmonika.backend.db.entities.Service;
import dk.haarmonika.haarmonika.backend.exceptions.ServiceValidationException;
import dk.haarmonika.haarmonika.backend.services.IServiceService;
import dk.haarmonika.haarmonika.controllers.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ServiceFormController extends BaseController {
    @FXML private Button saveButton;
    @FXML private Button createButton;

    private IServiceService serviceService;
    private Service selectedService;

    @FXML private TextField nameField;
    @FXML private TextField durationField;

    public ServiceFormController() {}

    public void setServiceService(IServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @FXML
    public void setService(Service service) {
        this.selectedService = service;
        if (service != null) {
            populateFields(service);
            saveButton.setVisible(true);
            createButton.setVisible(false);
        } else {
            clearFields();
            saveButton.setVisible(false);
            createButton.setVisible(true);
        }
    }

    private void populateFields(Service service) {
        nameField.setText(service.getName());

        // Properly check if duration is null, and handle it accordingly
        int duration = service.getDuration();
        durationField.setText(Integer.toString(duration));
    }

    @FXML
    private void handleSaveButton(ActionEvent actionEvent) {
        try {
            validateAndProcessService();
        } catch (ServiceValidationException | SQLException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateButton(ActionEvent actionEvent) {
        handleSaveButton(actionEvent);
    }

    private void validateAndProcessService() throws SQLException, ServiceValidationException {
        String name = nameField.getText();
        String durationText = durationField.getText();

        if (fieldsAreEmpty(name, durationText)) {
            showError("All fields must be filled out!");
            return;
        }

        Integer duration = null;
        try {
            // Parse the duration as Integer instead of int to handle null values
            duration = durationText.isEmpty() ? null : Integer.parseInt(durationText);
        } catch (NumberFormatException e) {
            showError("Duration must be a valid number!");
            return;
        }

        if (selectedService == null) {
            createService(name, duration);
        } else {
            updateService(name, duration);
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

    private void createService(String name, Integer duration) throws SQLException, ServiceValidationException {
        Service newService = new Service(name, duration);
        serviceService.createService(newService);
        showSuccess("Service created successfully!");
    }

    private void updateService(String name, Integer duration) throws SQLException, ServiceValidationException {
        selectedService.setName(name);
        selectedService.setDuration(duration);

        serviceService.updateService(selectedService);
        showSuccess("Service updated successfully!");
    }

    private void clearFields() {
        nameField.clear();
        durationField.clear();
    }
}
