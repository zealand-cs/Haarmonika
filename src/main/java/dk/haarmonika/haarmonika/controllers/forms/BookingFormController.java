package dk.haarmonika.haarmonika.controllers.forms;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.daos.booking.BookingDao;
import dk.haarmonika.haarmonika.backend.db.daos.customer.CustomerDao;
import dk.haarmonika.haarmonika.backend.db.daos.employee.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.daos.service.ServiceDao;
import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.db.entities.Service;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BookingFormController {
    private static final Logger logger = LoggerFactory.getLogger(BookingFormController.class);

    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private ComboBox<Customer> customerComboBox;
    @FXML private ComboBox<Employee> employeeComboBox;
    @FXML private ComboBox<Service> serviceComboBox;
    @FXML private Button confirmButton;

    private final CustomerDao customerDao;
    private final EmployeeDao employeeDao;
    private final ServiceDao serviceDao;
    private final BookingDao bookingDao;
    private static final Pagination DEFAULT_PAGINATION = new Pagination(0, 50);
    private LocalDate bookingDate;
    private LocalTime bookingTime;

    public BookingFormController(CustomerDao customerDao, EmployeeDao employeeDao, ServiceDao serviceDao, BookingDao bookingDao) {
        this.customerDao = customerDao;
        this.employeeDao = employeeDao;
        this.serviceDao = serviceDao;
        this.bookingDao = bookingDao;
    }

    @FXML
    public void initialize() {
        try {
            loadCustomers();
            loadEmployees();
            loadServices();
        } catch (SQLException e) {
            logger.error("Error loading booking form data", e);
        }
    }

    private void loadCustomers() throws SQLException {
        List<Customer> customers = customerDao.getAll(DEFAULT_PAGINATION);
        customerComboBox.setItems(FXCollections.observableArrayList(customers));
    }

    private void loadEmployees() throws SQLException {
        List<Employee> employees = employeeDao.getAll(DEFAULT_PAGINATION);
        employeeComboBox.setItems(FXCollections.observableArrayList(employees));
    }

    private void loadServices() throws SQLException {
        List<Service> services = serviceDao.getAll(DEFAULT_PAGINATION);
        serviceComboBox.setItems(FXCollections.observableArrayList(services));
    }

    public void initData(LocalDate date, LocalTime time) {
        this.bookingDate = date;
        this.bookingTime = time;
        dateLabel.setText("Dato: " + date.toString());
        timeLabel.setText("Tidspunkt: " + time.toString());
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
                    0, // ID (auto-generated)
                    selectedEmployee.getId(),
                    selectedCustomer.getId(),
                    (int) bookingDate.toEpochDay(), // Assuming the DB stores date as an integer
                    false
            );
            newBooking.addService(selectedService);

            bookingDao.save(newBooking);
            logger.info("Booking successfully saved: {}", newBooking);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            logger.error("Error saving booking", e);
        }
    }
}
