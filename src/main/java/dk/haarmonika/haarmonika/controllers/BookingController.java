package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.services.IBookingService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BookingController extends BaseController{
    private static final Logger logger = LogManager.getLogger(BookingController.class);
    @FXML private ComboBox<String> dropdownMenu;

    @FXML    private DatePicker datePicker;
    @FXML    private TableView<Booking> bookingTable;
    @FXML    private TableColumn<Booking, String> customerColumn, serviceColumn, employeeColumn, timeColumn;
    @FXML    private ComboBox<String> customerComboBox, serviceComboBox, employeeComboBox;
    @FXML    private TextField timeField;
    @FXML    private Button addBookingButton;

    private final ObservableList<Booking> bookings = FXCollections.observableArrayList();


    private SceneController sceneController;
    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService, SceneController sceneController) {
        this.bookingService = bookingService;
        this.sceneController = sceneController;
        if (bookingService != null) {
            logger.info("BookingService injected successfully");
        } else {
            logger.error("Failed to inject BookingService");
        }
    }
    @FXML
    public void initialize() {

    }


    private void setComboBoxItems(ComboBox<String> comboBox, String[] options) {
        comboBox.getItems().setAll(options);
    }


    @FXML
    private void handleAddBooking() {
        String customer = customerComboBox.getValue();
        String service = serviceComboBox.getValue();
        String employee = employeeComboBox.getValue();
        String time = timeField.getText();
        LocalDate date = datePicker.getValue();

        if (customer == null || service == null || employee == null || time.isEmpty() || date == null) {
            showError("Error, all fields must be filled out");
            return;
        }

        Booking newBooking = new Booking(customer, service, employee, date.toString() + " " + time);
        bookings.add(newBooking);
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
