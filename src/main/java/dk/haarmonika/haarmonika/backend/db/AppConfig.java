package dk.haarmonika.haarmonika.backend.db;


import dk.haarmonika.haarmonika.backend.db.daos.booking.BookingDao;
import dk.haarmonika.haarmonika.backend.db.daos.booking.IBookingDao;
import dk.haarmonika.haarmonika.backend.db.daos.customer.CustomerDao;
import dk.haarmonika.haarmonika.backend.db.daos.customer.ICustomerDao;
import dk.haarmonika.haarmonika.backend.db.daos.employee.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.daos.employee.IEmployeeDao;
import dk.haarmonika.haarmonika.backend.db.validation.BookingValidator;
import dk.haarmonika.haarmonika.backend.db.validation.CustomerValidator;
import dk.haarmonika.haarmonika.backend.db.validation.EmployeeValidator;
import dk.haarmonika.haarmonika.backend.services.*;
import dk.haarmonika.haarmonika.controllers.SceneController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import dk.haarmonika.haarmonika.backend.db.daos.service.IServiceDao;
import dk.haarmonika.haarmonika.backend.db.daos.service.ServiceDao;
import dk.haarmonika.haarmonika.backend.db.validation.ServiceValidator;


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

    @Bean
    public IServiceDao serviceDao() {
        return new ServiceDao();
    }
    @Bean
    public ServiceValidator serviceValidator() {
        return new ServiceValidator();
    }

    @Bean
    public IServiceService serviceService(IServiceDao serviceDao, ServiceValidator serviceValidator) {
        return new ServiceService(serviceDao, serviceValidator);
    }

    @Bean
    public BookingValidator bookingValidator() {
        return new BookingValidator();
    }

    @Bean
    public IBookingService bookingService(IBookingDao bookingDao, BookingValidator bookingValidator) {
        return new BookingService(bookingDao, bookingValidator);
    }

    @Bean
    public IBookingDao bookingDao(){
        return new BookingDao();
    }

}