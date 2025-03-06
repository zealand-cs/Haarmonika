package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.StartScene;
import dk.haarmonika.haarmonika.backend.services.IBookingService;
import dk.haarmonika.haarmonika.backend.services.ICustomerService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.backend.services.IServiceService;
import dk.haarmonika.haarmonika.controllers.forms.BookingFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SceneController {
    private static final Logger logger = LogManager.getLogger(SceneController.class);
    private static final Map<String, Parent> sceneCache = new HashMap<>();
    private static final Map<String, Object> controllerCache = new HashMap<>();


    @Autowired
    private ApplicationContext context;

    public void switchScene(String fxmlFile) {
        switchScene(fxmlFile, null, null);  // Default case with null values for services and day/time
    }

    public void switchScene(String fxmlFile, LocalDate day, LocalTime time) {
        try {
            logger.info("Switching to scene: " + fxmlFile);
            Parent root;

            if (context == null) {
                logger.error("Application context is not initialized.");
                return;
            }

            if (!sceneCache.containsKey(fxmlFile)) {
                FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/dk/haarmonika/haarmonika/gui_fxml/" + fxmlFile));

                switch (fxmlFile) {
                    case "EmployeePage.fxml" -> {
                        IEmployeeService empService = context.getBean(IEmployeeService.class);
                        logger.info("Loading EmployeePage with EmployeeController.");
                        loader.setControllerFactory(param -> new EmployeeController(empService, this));
                        root = loader.load();
                    }
                    case "CustomerPage.fxml" -> {
                        ICustomerService custService = context.getBean(ICustomerService.class);
                        logger.info("Loading CustomerPage with CustomerController.");
                        loader.setControllerFactory(param -> new CustomerController(custService, this));
                        root = loader.load();
                    }
                    case "login.fxml" -> {
                        IEmployeeService empService = context.getBean(IEmployeeService.class);
                        logger.info("Loading login page with LoginController.");
                        loader.setControllerFactory(param -> new LoginController(empService, this));
                        root = loader.load();
                    }
                    case "ServicePage.fxml" -> {
                        IServiceService serviceSvc = context.getBean(IServiceService.class);
                        logger.info("Loading ServicesPage with ServiceController.");
                        loader.setControllerFactory(param -> new ServiceController(serviceSvc, this));
                        root = loader.load();
                    }
                    case "BookingPage.fxml" -> {
                        IBookingService bookService = context.getBean(IBookingService.class);
                        ICustomerService custService = context.getBean(ICustomerService.class);
                        IEmployeeService empService = context.getBean(IEmployeeService.class);
                        IServiceService svcService = context.getBean(IServiceService.class);
                        logger.info("Loading BookingPage with BookingController.");
                        root = loader.load();
                        sceneCache.put(fxmlFile, root);
                        controllerCache.put(fxmlFile, loader.getController());
                    }
                    default -> {
                        logger.warn("Scene " + fxmlFile + " is not recognized, skipping scene loading.");
                        return;
                    }
                }

                sceneCache.put(fxmlFile, root);
                controllerCache.put(fxmlFile, loader.getController());
            } else {
                root = sceneCache.get(fxmlFile);
            }

            // Flyttet sceneCache.remove og controllerCache.remove herind
            sceneCache.remove(fxmlFile);
            controllerCache.remove(fxmlFile);

            StartScene.getPrimaryStage().setScene(new Scene(root, 500, 500));
        } catch (IOException e) {
            logger.error("Error loading scene " + fxmlFile, e);
        }
    }
}
