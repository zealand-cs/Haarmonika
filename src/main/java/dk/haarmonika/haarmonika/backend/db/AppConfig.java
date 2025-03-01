package dk.haarmonika.haarmonika.backend.db;


import dk.haarmonika.haarmonika.backend.db.daos.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.daos.IEmployeeDao;
import dk.haarmonika.haarmonika.backend.db.validation.EmployeeValidator;
import dk.haarmonika.haarmonika.backend.services.EmployeeService;
import dk.haarmonika.haarmonika.backend.services.IEmployeeService;
import dk.haarmonika.haarmonika.controllers.SceneController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Spring framework for IoC, Inversion of Control

@Configuration
public class AppConfig {

    @Bean
    public IEmployeeDao employeeDao() {
        return new EmployeeDao();
    }

    @Bean
    public IEmployeeService employeeService(IEmployeeDao employeeDao, EmployeeValidator employeeValidator) {
        return new EmployeeService(employeeDao, employeeValidator);
    }

    @Bean
    public SceneController sceneController() {
        return new SceneController();
    }
}