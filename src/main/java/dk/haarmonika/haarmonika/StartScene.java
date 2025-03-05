package dk.haarmonika.haarmonika;

import dk.haarmonika.haarmonika.controllers.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import dk.haarmonika.haarmonika.backend.db.AppConfig;

import java.io.IOException;

public class StartScene extends Application {
    @Autowired
    private SceneController sceneController;
    private static Stage primaryStage;
    private static AnnotationConfigApplicationContext context;

    @Override
    public void start(Stage stage) throws IOException {

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        SceneController sceneController = context.getBean(SceneController.class);


        primaryStage = stage;
        sceneController.switchScene("EmployeePage.fxml");
        primaryStage.setTitle("Velkommen til Haarmonika's FrisørSalon");
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        launch();
    }
}