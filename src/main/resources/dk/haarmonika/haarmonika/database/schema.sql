CREATE DATABASE Haarmonika;

CREATE TABLE UserRole (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

INSERT INTO UserRole (name) VALUES ("employee", "customer");

CREATE TABLE User (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(255) UNIQUE,
    roleId INT NOT NULL
);

CREATE TABLE Service (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    time INT NOT NULL
);

CREATE TABLE Booking (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employeeId INT NOT NULL,
    customerId INT NOT NULL,
    date INT NOT NULL,
    cancelled BOOL DEFAULT false
);

CREATE TABLE BookingService (
    bookingId INT,
    serviceId INT,
    PRIMARY KEY(bookingId, serviceId)
);