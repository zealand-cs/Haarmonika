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
    requires java.naming;


    opens dk.haarmonika.haarmonika.backend.db to spring.core;
    opens dk.haarmonika.haarmonika.controllers to spring.core, javafx.fxml;
    opens dk.haarmonika.haarmonika.controllers.forms to spring.core, javafx.fxml;
    exports dk.haarmonika.haarmonika.backend.db;
    exports dk.haarmonika.haarmonika;
    exports dk.haarmonika.haarmonika.controllers;
    exports dk.haarmonika.haarmonika.controllers.forms;



}
