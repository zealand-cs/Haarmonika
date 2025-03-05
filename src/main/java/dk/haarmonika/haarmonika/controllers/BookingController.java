package dk.haarmonika.haarmonika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


public class BookingController {
    private static final Logger logger = LogManager.getLogger(BookingController.class);

    @Autowired
    private SceneController sceneController;
@FXML private ComboBox<String> dropdownMenu;

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
