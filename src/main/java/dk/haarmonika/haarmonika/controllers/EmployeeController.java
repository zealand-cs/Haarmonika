package dk.haarmonika.haarmonika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeController implements ControllerInterface{

    public ListView employeeListView;



    @FXML
    private void addEmployeeButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/EmployeeForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add new Employee");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnShown(event -> root.requestFocus());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void refresh() {
        //TO DO
    }
}
