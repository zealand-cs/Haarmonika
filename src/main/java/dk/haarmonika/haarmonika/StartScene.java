package dk.haarmonika.haarmonika;

import dk.haarmonika.haarmonika.Controllers.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScene extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        SceneController.switchScene("LandingPage.fxml");
        primaryStage.setTitle("Velkommen til Haarmonika's FrisørSalon");
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}