package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.db.entities.Service;
import dk.haarmonika.haarmonika.backend.services.*;
import dk.haarmonika.haarmonika.controllers.forms.BookingFormController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Controller for managing bookings and displaying the calendar view.
 */
public class BookingController extends BaseController{
    private static final Logger logger = LogManager.getLogger(BookingController.class);


    private ApplicationContext applicationContext;
    private final IServiceService serviceService;
    private final IBookingService bookingService;
    private final ICustomerService customerService;
    private final IEmployeeService employeeService;
    @Autowired
    private SceneController sceneController;
    @FXML private ComboBox<String> dropdownMenu;
    @FXML private Button addBookingButton;
    @FXML private DatePicker datePicker;
    @FXML private GridPane calendarGrid;
    @FXML private ComboBox<String> serviceComboBox;
    @FXML private ComboBox<String> customerComboBox;
    @FXML private ComboBox<String> employeeComboBox;

    // Track selected service duration (default 30 min)
    private final Map<String, Integer> serviceDurations = new HashMap<>();
    private LocalDate selectedMonday;


    /**
     * Constructor for BookingController.
     *
     * @param bookingService   The service for managing booking data.
     * @param customerService  The service for managing customer data.
     * @param employeeService  The service for managing employee data.
     * @param serviceService   The service for managing service data.
     * @param sceneController The controller for managing scene transitions.
     */
    public BookingController(IBookingService bookingService, ICustomerService customerService, IEmployeeService employeeService, IServiceService serviceService, SceneController sceneController) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.serviceService = serviceService;
        this.sceneController = sceneController;

        logger.info("BookingService: " + bookingService);
        logger.info("CustomerService: " + customerService);
        logger.info("EmployeeService: " + employeeService);
        logger.info("ServiceService: " + serviceService);
    }

    /**
     * Initializes the controller and sets up the calendar view.
     */
    @FXML
    public void initialize() {

        datePicker.setValue(LocalDate.now());
        updateCalendar();
    }
    /**
     * Initializes data for the booking form.
     *
     * @param day  The selected booking date.
     * @param time The selected booking time.
     */
    public void initData(LocalDate day, LocalTime time) {
        // Use day and time to set values or initialize UI components
        System.out.println("Day: " + day + ", Time: " + time);
    }
    /**
     * Updates the calendar view based on the selected date.
     */
    @FXML
    private void updateCalendar() {
        LocalDate selectedDate = datePicker.getValue();
        selectedMonday = selectedDate.with(DayOfWeek.MONDAY);

        calendarGrid.getChildren().clear();

        // Add day labels to the calendar grid
        for (int i = 0; i < 5; i++) {
            LocalDate day = selectedMonday.plusDays(i);
            Label dayLabel = new Label(day.format(DateTimeFormatter.ofPattern("EEEE dd/MM")));
            calendarGrid.add(dayLabel, i + 1, 0);
        }

        List<Booking> bookings = fetchBookingsForWeek(selectedMonday);

        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        int rowIndex = 1;

        while (startTime.isBefore(endTime)) {
            Label timeLabel = new Label(startTime.toString());
            calendarGrid.add(timeLabel, 0, rowIndex);

            for (int col = 1; col <= 5; col++) {
                LocalDate day = selectedMonday.plusDays(col - 1);
                Pane slot = createTimeSlot(day, startTime, bookings);
                calendarGrid.add(slot, col, rowIndex);
            }

            startTime = startTime.plusMinutes(30);
            rowIndex++;
        }
    }
    /**
     * Creates a time slot pane for the calendar grid.
     *
     * @param day      The date for the time slot.
     * @param time     The time for the time slot.
     * @param bookings The list of bookings to check for conflicts.
     * @return The created time slot pane.
     */
    @FXML
    private Pane createTimeSlot(LocalDate day, LocalTime time, List<Booking> bookings) {
        Pane slot = new Pane();
        slot.setPrefSize(100, 30);
        slot.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");



        boolean isBooked = bookings.stream()
                .anyMatch(booking -> LocalDate.ofEpochDay(booking.getDate()).isEqual(day) /*&& booking.getBookingTime().equals(time)*/);

        if (isBooked) {
            slot.setStyle("-fx-border-color: black; -fx-background-color: red;");
        } else {
            slot.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");
        }

        slot.setOnMouseClicked(event -> {
            handleSlotSelection(day, time);
        });

        return slot;
    }
    /*@FXML
    private Pane createTimeSlot(LocalDate day, LocalTime time) {
        Pane slot = new Pane();
        slot.setPrefSize(100, 30);
        slot.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

        slot.setOnMouseClicked(event -> {
            handleSlotSelection(day, time); // Send day og time til handleSlotSelection
        });

        return slot;


    }*/
    /**
     * Handles the selection of a time slot in the calendar grid.
     * Opens a new window to create a booking for the selected slot.
     *
     * @param day  The selected booking date.
     * @param time The selected booking time.
     */
    @FXML
    private void handleSlotSelection(LocalDate day, LocalTime time) {
        try {
            logger.info("Entering handleSlotSelection()");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/BookingForm.fxml"));
            logger.info("FXML Loader created");

            // Manually create and wire the BookingFormController
            loader.setControllerFactory(param -> {
                BookingFormController formController = new BookingFormController(
                        customerService,
                        serviceService,
                        employeeService,
                        bookingService
                );
                return formController;
            });
            logger.info("Controller factory set to manual creation");

            Parent root = loader.load();
            if (root == null) {
                logger.error("Failed to load BookingForm.fxml");
                return;
            }
            logger.info("FXML loaded, root: {}", root);

            BookingFormController formController = loader.getController();
            if (formController == null) {
                logger.error("Failed to retrieve BookingFormController");
                return;
            }
            logger.info("Controller retrieved: {}", formController);

            formController.initData(day, time);
            logger.info("formController.initData() called");

            Stage stage = new Stage();
            stage.setTitle("Create Booking");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnShown(event -> root.requestFocus());
            stage.showAndWait();

            logger.info("Booking form stage shown and closed");

        } catch (IOException e) {
            logger.error("IOException occurred during handleSlotSelection", e);
            e.printStackTrace();
        } catch(Exception e){
            logger.error("Exception occured during handleSlotSelection", e);
            e.printStackTrace();
        }
    }

    /*logger.info("Entering handleSlotSelection()"); // Log entry
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/BookingForm.fxml"));
            logger.info("FXML Loader created"); // Log FXML Loader creation
            loader.setControllerFactory(applicationContext::getBean);
            logger.info("Controller factory set"); // Log controller factory setting
            Parent root = loader.load();
            logger.info("FXML loaded, root: " + root); // Log root value
            BookingFormController formController = loader.getController();
            logger.info("Controller retrieved: " + formController); // Log controller value*/




    /*formController.setCustomerService(customerService);
    formController.setServiceService(serviceService);
    formController.setEmployeeService(employeeService);
    formController.setBookingService(bookingService);*/
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
                    logger.warn("Ugyldig valg i dropdown: " + selectedOption);
                    break;
            }
        }
    }
    /**
     * Fetches bookings for the specified week.
     *
     * @param monday The Monday of the week to fetch bookings for.
     * @return The list of bookings for the week.
     */
    private List<Booking> fetchBookingsForWeek(LocalDate monday) {
        LocalDate sunday = monday.plusDays(6); // Calculate Sunday of the selected week
        try {
            return bookingService.getBookingsBetween(monday, sunday);
        } catch (SQLException e) {
            logger.error("Error fetching bookings", e);
            return Collections.emptyList(); // Return an empty list if fetching fails
        }
    }



}
