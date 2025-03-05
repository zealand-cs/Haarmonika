package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.backend.services.IBookingService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BookingController extends BaseController{
    private static final Logger logger = LogManager.getLogger(BookingController.class);
    @FXML private ComboBox<String> bookingMonday1;
    @FXML private ComboBox<String> bookingMonday2;
    @FXML private ComboBox<String> bookingMonday3;
    @FXML private ComboBox<String> bookingMonday4;
    @FXML private ComboBox<String> bookingMonday5;
    @FXML private ComboBox<String> bookingMonday6;
    @FXML private ComboBox<String> bookingMonday7;
    @FXML private ComboBox<String> bookingMonday8;
    @FXML private ComboBox<String> bookingTuesday1;
    @FXML private ComboBox<String> bookingTuesday2;
    @FXML private ComboBox<String> bookingTuesday3;
    @FXML private ComboBox<String> bookingTuesday4;
    @FXML private ComboBox<String> bookingTuesday5;
    @FXML private ComboBox<String> bookingTuesday6;
    @FXML private ComboBox<String> bookingTuesday7;
    @FXML private ComboBox<String> bookingTuesday8;
    @FXML private ComboBox<String> bookingWednesday1;
    @FXML private ComboBox<String> bookingWednesday2;
    @FXML private ComboBox<String> bookingWednesday3;
    @FXML private ComboBox<String> bookingWednesday4;
    @FXML private ComboBox<String> bookingWednesday5;
    @FXML private ComboBox<String> bookingWednesday6;
    @FXML private ComboBox<String> bookingWednesday7;
    @FXML private ComboBox<String> bookingWednesday8;
    @FXML private ComboBox<String> bookingThursday1;
    @FXML private ComboBox<String> bookingThursday2;
    @FXML private ComboBox<String> bookingThursday3;
    @FXML private ComboBox<String> bookingThursday4;
    @FXML private ComboBox<String> bookingThursday5;
    @FXML private ComboBox<String> bookingThursday6;
    @FXML private ComboBox<String> bookingThursday7;
    @FXML private ComboBox<String> bookingThursday8;
    @FXML private ComboBox<String> bookingFriday1;
    @FXML private ComboBox<String> bookingFriday2;
    @FXML private ComboBox<String> bookingFriday3;
    @FXML private ComboBox<String> bookingFriday4;
    @FXML private ComboBox<String> bookingFriday5;
    @FXML private ComboBox<String> bookingFriday6;
    @FXML private ComboBox<String> bookingFriday7;
    @FXML private ComboBox<String> bookingFriday8;


    @FXML    private ComboBox<String> dropdownMenu;
    @FXML    private Button btnPreviousWeek;
    @FXML    private Button btnNextWeek;
    @FXML    private Label lblWeekRange;


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

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");

    public void initialize() {
        // Set the initial week
        updateCalendar();

        // Set button actions for navigating weeks
        btnPreviousWeek.setOnAction(e -> {
            calendar.add(Calendar.WEEK_OF_YEAR, -1); // Go back one week
            updateCalendar();
        });
        btnNextWeek.setOnAction(e -> {
            calendar.add(Calendar.WEEK_OF_YEAR, 1); // Go forward one week
            updateCalendar();
        });


        String[] options = {"Available", "Booked"};
        setComboBoxItems(bookingMonday1, options);
        setComboBoxItems(bookingMonday2, options);
        setComboBoxItems(bookingMonday3, options);
        setComboBoxItems(bookingMonday4, options);
        setComboBoxItems(bookingMonday5, options);
        setComboBoxItems(bookingMonday6, options);
        setComboBoxItems(bookingMonday7, options);
        setComboBoxItems(bookingMonday8, options);

        setComboBoxItems(bookingTuesday1, options);
        setComboBoxItems(bookingTuesday2, options);
        setComboBoxItems(bookingTuesday3, options);
        setComboBoxItems(bookingTuesday4, options);
        setComboBoxItems(bookingTuesday5, options);
        setComboBoxItems(bookingTuesday6, options);
        setComboBoxItems(bookingTuesday7, options);
        setComboBoxItems(bookingTuesday8, options);

        setComboBoxItems(bookingWednesday1, options);
        setComboBoxItems(bookingWednesday2, options);
        setComboBoxItems(bookingWednesday3, options);
        setComboBoxItems(bookingWednesday4, options);
        setComboBoxItems(bookingWednesday5, options);
        setComboBoxItems(bookingWednesday6, options);
        setComboBoxItems(bookingWednesday7, options);
        setComboBoxItems(bookingWednesday8, options);

        setComboBoxItems(bookingThursday1, options);
        setComboBoxItems(bookingThursday2, options);
        setComboBoxItems(bookingThursday3, options);
        setComboBoxItems(bookingThursday4, options);
        setComboBoxItems(bookingThursday5, options);
        setComboBoxItems(bookingThursday6, options);
        setComboBoxItems(bookingThursday7, options);
        setComboBoxItems(bookingThursday8, options);

        setComboBoxItems(bookingFriday1, options);
        setComboBoxItems(bookingFriday2, options);
        setComboBoxItems(bookingFriday3, options);
        setComboBoxItems(bookingFriday4, options);
        setComboBoxItems(bookingFriday5, options);
        setComboBoxItems(bookingFriday6, options);
        setComboBoxItems(bookingFriday7, options);
        setComboBoxItems(bookingFriday8, options);
    }

    private void setComboBoxItems(ComboBox<String> comboBox, String[] options) {
        comboBox.getItems().setAll(options);
    }


    private void updateCalendar() {
        // Reset calendar to the current date to avoid unintentional shifts
        calendar = Calendar.getInstance(); // Get the current calendar instance again

        // Set to the start of the current week (Monday)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = calendar.getTime();

        // Add 6 days to get to Sunday of the same week
        calendar.add(Calendar.DATE, 6);
        Date sunday = calendar.getTime();

        // Format and set the week range on the label
        lblWeekRange.setText("Week of: " + dateFormat.format(monday) + " - " + dateFormat.format(sunday));

        // Reset ComboBox items (clear previous selections, if any)
        String[] options = {"Available", "Booked"};
        bookingMonday1.getItems().setAll(options);
        bookingMonday2.getItems().setAll(options);
        bookingMonday3.getItems().setAll(options);
        bookingMonday4.getItems().setAll(options);
        bookingMonday5.getItems().setAll(options);
        bookingMonday6.getItems().setAll(options);
        bookingMonday7.getItems().setAll(options);
        bookingMonday8.getItems().setAll(options);

        bookingTuesday1.getItems().setAll(options);
        bookingTuesday2.getItems().setAll(options);
        bookingTuesday3.getItems().setAll(options);
        bookingTuesday4.getItems().setAll(options);
        bookingTuesday5.getItems().setAll(options);
        bookingTuesday6.getItems().setAll(options);
        bookingTuesday7.getItems().setAll(options);
        bookingTuesday8.getItems().setAll(options);

        bookingWednesday1.getItems().setAll(options);
        bookingWednesday2.getItems().setAll(options);
        bookingWednesday3.getItems().setAll(options);
        bookingWednesday4.getItems().setAll(options);
        bookingWednesday5.getItems().setAll(options);
        bookingWednesday6.getItems().setAll(options);
        bookingWednesday7.getItems().setAll(options);
        bookingWednesday8.getItems().setAll(options);

        bookingThursday1.getItems().setAll(options);
        bookingThursday2.getItems().setAll(options);
        bookingThursday3.getItems().setAll(options);
        bookingThursday4.getItems().setAll(options);
        bookingThursday5.getItems().setAll(options);
        bookingThursday6.getItems().setAll(options);
        bookingThursday7.getItems().setAll(options);
        bookingThursday8.getItems().setAll(options);

        bookingFriday1.getItems().setAll(options);
        bookingFriday2.getItems().setAll(options);
        bookingFriday3.getItems().setAll(options);
        bookingFriday4.getItems().setAll(options);
        bookingFriday5.getItems().setAll(options);
        bookingFriday6.getItems().setAll(options);
        bookingFriday7.getItems().setAll(options);
        bookingFriday8.getItems().setAll(options);
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
