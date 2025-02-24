module dk.haarmonika.haarmonika {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.haarmonika.haarmonika to javafx.fxml;
    exports dk.haarmonika.haarmonika;
}