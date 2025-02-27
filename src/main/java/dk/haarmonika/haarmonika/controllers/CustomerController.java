package dk.haarmonika.haarmonika.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;


public class CustomerController{

    @FXML private ListView listofCustomers;

    private ObservableList customers = FXCollections.observableArrayList();


    public void initialize() {
        loadCustomers();
    }



    @FXML
    private void addCustomerButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/haarmonika/haarmonika/gui_fxml/CustomerForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Customer creation");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnShown(event -> root.requestFocus());


            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadCustomers() {
        customers.clear();
        //List<CustomerRead> customerList = Database.getInstance().customers.readAll();
        //customers.addAll(customerlist);
    }



}
