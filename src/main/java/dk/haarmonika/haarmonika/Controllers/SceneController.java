package dk.haarmonika.haarmonika.Controllers;

import dk.haarmonika.haarmonika.StartScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneController {
    private static final Map<String, Parent> sceneCache = new HashMap<>();
    private static final Map<String, ControllerInterface> controllerCache = new HashMap<>();

    public static void switchScene(String fxmlFile) {
        try {
            Parent root;
            ControllerInterface controller;

            if (!sceneCache.containsKey(fxmlFile)) {
                FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/dk/haarmonika/haarmonika/gui_fxml/" + fxmlFile));
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


