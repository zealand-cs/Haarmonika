module dk.haarmonika.haarmonika {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.zaxxer.hikari;
    requires org.slf4j;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires org.apache.logging.log4j.core;


    opens dk.haarmonika.haarmonika.backend.db to spring.core, spring.beans, spring.context;


    opens dk.haarmonika.haarmonika to javafx.fxml;
    exports dk.haarmonika.haarmonika;
    exports dk.haarmonika.haarmonika.controllers;
    opens dk.haarmonika.haarmonika.controllers to javafx.fxml;
    exports dk.haarmonika.haarmonika.controllers.forms;
    opens dk.haarmonika.haarmonika.controllers.forms to javafx.fxml;



}
