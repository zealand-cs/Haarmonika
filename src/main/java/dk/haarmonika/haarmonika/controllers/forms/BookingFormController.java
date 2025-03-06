package dk.haarmonika.haarmonika.controllers.forms;

import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.db.entities.Service;
import dk.haarmonika.haarmonika.backend.services.IBookingService;
import dk.haarmonika.haarmonika.backend.services.ICustomerService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.backend.services.IServiceService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class BookingFormController {
    private static final Logger logger = LoggerFactory.getLogger(BookingFormController.class);

    @FXML private Label dateLabel;
    @FXML private Label timeLabel;

    public Label getDateLabel() {
        return dateLabel;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    @FXML private ComboBox<Customer> customerComboBox;
    @FXML private ComboBox<Employee> employeeComboBox;
    @FXML private ComboBox<Service> serviceComboBox;
    @FXML private Button confirmButton;

    @Autowired
    private IBookingService bookingService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IServiceService serviceService;
    private LocalDate bookingDate;
    private LocalTime bookingTime;

    public BookingFormController(){}

    @FXML
    public void initialize() {
        logger.info("BookingFormController initialized");
        try {
            loadCustomers();
            loadEmployees();
            loadServices();
        } catch (SQLException e) {
            logger.error("Error loading booking form data", e);
        }
    }

    private void loadCustomers() throws SQLException {
        List<Customer> customers = customerService.getAllCustomers();
        customerComboBox.setItems(FXCollections.observableArrayList(customers));
    }

    private void loadEmployees() throws SQLException {
        List<Employee> employees = employeeService.getAllEmployees();
        employeeComboBox.setItems(FXCollections.observableArrayList(employees));
    }

    private void loadServices() throws SQLException {
        List<Service> services = serviceService.getAllServices();
        serviceComboBox.setItems(FXCollections.observableArrayList(services));
    }

    public void initData(LocalDate date, LocalTime time) {
        this.bookingDate = date;
        this.bookingTime = time;
        dateLabel.setText("Date: " + date.toString());
        timeLabel.setText("Time: " + time.toString());
    }

    @FXML
    private void handleConfirm() {
        try {
            Customer selectedCustomer = customerComboBox.getValue();
            Employee selectedEmployee = employeeComboBox.getValue();
            Service selectedService = serviceComboBox.getValue();

            if (selectedCustomer == null || selectedEmployee == null || selectedService == null) {
                logger.warn("Cannot confirm booking: Missing selection");
                return;
            }

            Booking newBooking = new Booking(
                    0,
                    selectedEmployee.getId(),
                    selectedCustomer.getId(),
                    (int) bookingDate.toEpochDay(),
                    false
            );
            newBooking.addService(selectedService);

            if (bookingService == null) {
                logger.error("BookingService is null!");
                return;
            }

            bookingService.createBooking(newBooking);
            logger.info("Booking successfully saved: {}", newBooking);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            logger.error("Error saving booking", e);
        }
    }
}
