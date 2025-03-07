package dk.haarmonika.haarmonika.controllers.forms;

import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.db.entities.Service;
import dk.haarmonika.haarmonika.backend.services.IBookingService;
import dk.haarmonika.haarmonika.backend.services.ICustomerService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.backend.services.IServiceService;
import dk.haarmonika.haarmonika.controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class BookingFormController  extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BookingFormController.class);

    @FXML private Label dateLabel;
    @FXML private Label timeLabel;

    @FXML public Label getDateLabel() {
        return dateLabel;
    }

    @FXML public Label getTimeLabel() {
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

    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private ObservableList<Service> services = FXCollections.observableArrayList();



    public BookingFormController(ICustomerService customerService,
                                 IServiceService serviceService,
                                 IEmployeeService employeeService,
                                 IBookingService bookingService) {
        this.customerService = customerService;
        this.serviceService = serviceService;
        this.employeeService = employeeService;
        this.bookingService = bookingService;
    }
   /* public void setCustomerService (ICustomerService customerService) {
        this.customerService = customerService;
    }
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setServiceService(IServiceService serviceService) {
        this.serviceService = serviceService;
    }
    public void setBookingService(IBookingService bookingService) {
        this.bookingService = bookingService;
    }*/



    @FXML
    public void initialize() {
        logger.info("BookingFormController initialized");

        logger.info("Customer service: " + customerService);
        logger.info("Service service: " + serviceService);
        logger.info("Employee service: " + employeeService);
        logger.info("Booking service: " + bookingService);
        try {
            loadCustomers();
            loadEmployees();
            loadServices();
        } catch (SQLException e) {
            logger.error("Error loading booking form data", e);
        }

        setupCustomerComboBox();
        setupEmployeeComboBox();
        setupServiceComboBox();
    }

    private void loadEmployees() throws SQLException {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            employeeComboBox.setItems(FXCollections.observableArrayList(employees));
        } catch (SQLException e) {
            logger.error("Error loading employees", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Noget andet gik galt");
            e.printStackTrace();
        }
    }
    private void loadCustomers() throws SQLException {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            customerComboBox.setItems(FXCollections.observableArrayList(customers));
        } catch (SQLException e) {
            logger.error("Error loading customers", e);
            e.printStackTrace();
        } catch (Exception e){
            logger.error("Noget andet gik galt");
            e.printStackTrace();
        }
    }



    private void loadServices() throws SQLException {
        try {
            List<Service> services = serviceService.getAllServices();
            serviceComboBox.setItems(FXCollections.observableArrayList(services));
        } catch (SQLException e) {
            logger.error("Error loading services");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Noget andet gik galt");
            e.printStackTrace();
        }

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

    private void setupEmployeeComboBox() {
        employeeComboBox.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee employee) {
                if (employee == null) {
                    return null;
                }
                return employee.getFirstName() + " " + employee.getLastName(); // Or any other relevant field
            }

            @Override
            public Employee fromString(String string) {
                // Not needed for displaying, return null
                return null;
            }
        });
    }
    private void setupCustomerComboBox() {
        customerComboBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                if (customer == null) {
                    return null;
                }
                return customer.getFirstName() + " " + customer.getLastName(); // Or any other relevant field
            }

            @Override
            public Customer fromString(String string) {
                // Not needed for displaying, return null
                return null;
            }
        });
    }

    private void setupServiceComboBox() {
        serviceComboBox.setConverter(new StringConverter<Service>() {
            @Override
            public String toString(Service service) {
                if (service == null) {
                    return null;
                }
                return service.getName(); // Or any other relevant field
            }

            @Override
            public Service fromString(String string) {
                // Not needed for displaying, return null
                return null;
            }
        });
    }
}
