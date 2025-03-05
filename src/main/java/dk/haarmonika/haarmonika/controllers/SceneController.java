package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.StartScene;
import dk.haarmonika.haarmonika.backend.db.AppConfig;
import dk.haarmonika.haarmonika.backend.services.IBookingService;
import dk.haarmonika.haarmonika.backend.services.ICustomerService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.backend.services.IServiceService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SceneController {
    private static final Logger logger = LogManager.getLogger(SceneController.class);
    private static final Map<String, Parent> sceneCache = new HashMap<>();
    private static final Map<String, ControllerInterface> controllerCache = new HashMap<>();


    @Autowired
    private ApplicationContext context;

    public void switchScene(String fxmlFile) {
        try {
            logger.info("Switching to scene" + fxmlFile);
            Parent root;
            ControllerInterface controller;

            if (context == null) {
                logger.error("Application context is not initialized.");
                return;
            }
            if (sceneCache.containsKey(fxmlFile)) {
                sceneCache.remove(fxmlFile);
                controllerCache.remove(fxmlFile);
            }

            if (!sceneCache.containsKey(fxmlFile)) {
                FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/dk/haarmonika/haarmonika/gui_fxml/" + fxmlFile));

                switch (fxmlFile) {
                    case "EmployeePage.fxml" -> {
                        IEmployeeService employeeService = context.getBean(IEmployeeService.class);
                        logger.info("Loading EmployeePage with EmployeeController.");
                        loader.setControllerFactory(param -> new EmployeeController(employeeService, this));
                    }
                    case "CustomerPage.fxml" -> {
                        ICustomerService customerService = context.getBean(ICustomerService.class);
                        logger.info("Loading CustomerPage with CustomerController.");
                        loader.setControllerFactory(param -> new CustomerController(customerService, this));
                    }
                    case "login.fxml" -> {
                        IEmployeeService employeeService = context.getBean(IEmployeeService.class);
                        logger.info("Loading login page with LoginController.");
                        loader.setControllerFactory(param -> new LoginController(employeeService, this));
                    }
                    case "ServicesPage.fxml" -> {
                        IServiceService serviceService = context.getBean(IServiceService.class);
                        logger.info("Loading ServicesPage with ServiceController.");
                        loader.setControllerFactory(param -> new ServiceController(serviceService, this));
                    }
                    case "BookingPage.fxml" -> {
                        IBookingService bookingService = context.getBean(IBookingService.class);
                        logger.info("Loading BookingPage with BookingController.");
                        loader.setControllerFactory(param -> new BookingController(bookingService, this));
                    }
                    default -> {
                        logger.warn("Scene " + fxmlFile + " is not recognized, skipping scene loading.");
                        return;
                    }
                }

                root = loader.load();
                controller = loader.getController();

                //Stores layout and controller
                sceneCache.put(fxmlFile, root);
                controllerCache.put(fxmlFile, controller);
            } else {
                root = sceneCache.get(fxmlFile);
                controller = controllerCache.get(fxmlFile);
            }



            StartScene.getPrimaryStage().setScene(new Scene(root, 500, 500));
        } catch (IOException e) {
            logger.error("Error loading scene " + fxmlFile, e);
        }
    }
}