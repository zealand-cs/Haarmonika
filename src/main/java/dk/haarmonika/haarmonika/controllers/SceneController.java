package dk.haarmonika.haarmonika.controllers;

import dk.haarmonika.haarmonika.StartScene;
import dk.haarmonika.haarmonika.backend.db.AppConfig;
import dk.haarmonika.haarmonika.backend.db.daos.EmployeeDao;
import dk.haarmonika.haarmonika.backend.services.EmployeeService;
import dk.haarmonika.haarmonika.backend.services.ICustomerService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.backend.services.IServiceService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneController {
    private static final Map<String, Parent> sceneCache = new HashMap<>();
    private static final Map<String, ControllerInterface> controllerCache = new HashMap<>();

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


    public static void switchScene(String fxmlFile) {
        try {
            Parent root;
            ControllerInterface controller;

            if (!sceneCache.containsKey(fxmlFile)) {
                FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/dk/haarmonika/haarmonika/gui_fxml/" + fxmlFile));


                if (fxmlFile.equals("EmployeePage.fxml")) {

                    IEmployeeService employeeService = context.getBean(IEmployeeService.class);
                    loader.setControllerFactory(param -> new EmployeeController(employeeService));
                }
                if (fxmlFile.equals("CustomerPage.fxml")) {

                    ICustomerService customerService = context.getBean(ICustomerService.class);
                    loader.setControllerFactory(param -> new CustomerController(customerService));
                }
                if (fxmlFile.equals("login.fxml")) {

                    IEmployeeService employeeService = context.getBean(IEmployeeService.class);
                    loader.setControllerFactory(param -> new LoginController(employeeService));
                }

                /*if (fxmlFile.equals("ServicePage.fxml")) {
                    IServiceService serviceService = context.getBean(IServiceService.class);
                    loader.setControllerFactory(param -> new ServiceController(serviceService));
                }*/

                root = loader.load();
                controller = loader.getController();

                //Stores layout and controller
                sceneCache.put(fxmlFile, root);
                controllerCache.put(fxmlFile, controller);
            } else {
                root = sceneCache.get(fxmlFile);
                controller = controllerCache.get(fxmlFile);
            }


            if (controller != null) {
                controller.refresh();
            }

            StartScene.getPrimaryStage().setScene(new Scene(root, 500, 500));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}