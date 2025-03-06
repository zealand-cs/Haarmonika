package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.services.IBookingService;
import dk.haarmonika.haarmonika.backend.services.ICustomerService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.backend.services.IServiceService;
import dk.haarmonika.haarmonika.controllers.forms.BookingFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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

    private IBookingService bookingService;
    private ICustomerService customerService;
    private IEmployeeService employeeService;
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


    public BookingController(IBookingService bookingService, ICustomerService customerService, IEmployeeService employeeService, IServiceService serviceService, SceneController sceneController) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.sceneController = sceneController;
    }


    @FXML
    public void initialize() {

        datePicker.setValue(LocalDate.now());
        updateCalendar();
    }

    @FXML
    private void updateCalendar() {
        LocalDate selectedDate = datePicker.getValue();
        selectedMonday = selectedDate.with(DayOfWeek.MONDAY);

        calendarGrid.getChildren().clear();

        // Add headers (Days)
        for (int i = 0; i < 5; i++) {
            LocalDate day = selectedMonday.plusDays(i);
            Label dayLabel = new Label(day.format(DateTimeFormatter.ofPattern("EEEE dd/MM")));
            calendarGrid.add(dayLabel, i + 1, 0);
        }


        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        int rowIndex = 1;

        while (startTime.isBefore(endTime)) {
            Label timeLabel = new Label(startTime.toString()); // Time Column
            calendarGrid.add(timeLabel, 0, rowIndex);

            for (int col = 1; col <= 5; col++) {  // Monday-Friday (Columns 1-5)
                LocalDate day = selectedMonday.plusDays(col - 1);
                Pane slot = createTimeSlot(day, startTime);
                calendarGrid.add(slot, col, rowIndex);
            }

            startTime = startTime.plusMinutes(30); // Next Time Slot
            rowIndex++;
        }
    }

    private Pane createTimeSlot(LocalDate day, LocalTime time) {
        Pane slot = new Pane();
        slot.setPrefSize(100, 30);
        slot.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

        slot.setOnMouseClicked(event -> {
            handleSlotSelection(day, time);
        });

        return slot;
    }

    private void handleSlotSelection(LocalDate day, LocalTime time) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/views/BookingForm.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            BookingFormController controller = loader.getController();
            controller.initData(day, time);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void updateAvailableTimes(ActionEvent actionEvent) {
    }

    public void handleAddBooking(ActionEvent actionEvent) {
        sceneController.switchScene("BookingFormController");
    }
}
