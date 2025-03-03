package dk.haarmonika.haarmonika.backend.db;


import dk.haarmonika.haarmonika.backend.db.daos.*;
import dk.haarmonika.haarmonika.backend.db.validation.CustomerValidator;
import dk.haarmonika.haarmonika.backend.db.validation.EmployeeValidator;
import dk.haarmonika.haarmonika.backend.services.*;
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

    @Bean
    public EmployeeValidator employeeValidator() {
        return new EmployeeValidator();
    }

    @Bean
    public CustomerValidator customerValidator() {
        return new CustomerValidator();
    }
    @Bean
    public ICustomerService customerService(ICustomerDao customerDao, CustomerValidator customerValidator) {
        return new CustomerService(customerDao, customerValidator);
    }
    @Bean
    public ICustomerDao customerDao() {
        return new CustomerDao();
    }
    /*@Bean
    public IServiceService serviceDao() {
        return new ServiceDao();
    }

    @Bean
    public IServiceService serviceService(IServiceDao serviceDao, ServiceValidator serviceValidator) {
        return new ServiceService(serviceDao, serviceValidator);
    }*/

}