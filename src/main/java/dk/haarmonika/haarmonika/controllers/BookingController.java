package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.services.*;
import dk.haarmonika.haarmonika.controllers.forms.BookingFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class BookingController extends BaseController{
    private static final Logger logger = LogManager.getLogger(BookingController.class);

    @Autowired
    private ApplicationContext applicationContext;
    private final IServiceService serviceService;
    private final IBookingService bookingService;
    private final ICustomerService customerService;
    private final IEmployeeService employeeService;
    private final SceneController sceneController;
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

    @Autowired
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


    @FXML
    public void initialize() {

        datePicker.setValue(LocalDate.now());
        updateCalendar();
    }

    public void initData(LocalDate day, LocalTime time) {
        // Use day and time to set values or initialize UI components
        System.out.println("Day: " + day + ", Time: " + time);
    }

    @FXML
    private void updateCalendar() {
        LocalDate selectedDate = datePicker.getValue();
        selectedMonday = selectedDate.with(DayOfWeek.MONDAY);

        calendarGrid.getChildren().clear();


        for (int i = 0; i < 5; i++) {
            LocalDate day = selectedMonday.plusDays(i);
            Label dayLabel = new Label(day.format(DateTimeFormatter.ofPattern("EEEE dd/MM")));
            calendarGrid.add(dayLabel, i + 1, 0);
        }


        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        int rowIndex = 1;

        while (startTime.isBefore(endTime)) {
            Label timeLabel = new Label(startTime.toString());
            calendarGrid.add(timeLabel, 0, rowIndex);

            for (int col = 1; col <= 5; col++) {
                LocalDate day = selectedMonday.plusDays(col - 1);
                Pane slot = createTimeSlot(day, startTime);
                calendarGrid.add(slot, col, rowIndex);
            }

            startTime = startTime.plusMinutes(30);
            rowIndex++;
        }
    }

    @FXML
    private Pane createTimeSlot(LocalDate day, LocalTime time) {
        Pane slot = new Pane();
        slot.setPrefSize(100, 30);
        slot.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

        slot.setOnMouseClicked(event -> {
            handleSlotSelection(day, time); // Send day og time til handleSlotSelection
        });

        return slot;
    }

    @FXML
    private void handleSlotSelection(LocalDate day, LocalTime time) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/BookingForm.fxml"));
            //loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            if (root == null) {
                logger.error("Failed to load BookingForm.fxml");
                return;
            }

            BookingFormController formController = (BookingFormController) controllerCache.get("BookingPage.fxml");



            formController.initData(day, time);



            Stage stage = new Stage();
            stage.setTitle("Create Booking");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnShown(event -> root.requestFocus());
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
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
                    logger.warn("Ugyldig valg i dropdown: " + selectedOption);
                    break;
            }
        }
    }

    public void updateAvailableTimes(ActionEvent actionEvent) {
    }

    public void handleAddBooking(ActionEvent actionEvent) {
        sceneController.switchScene("BookingFormController");
    }
}
