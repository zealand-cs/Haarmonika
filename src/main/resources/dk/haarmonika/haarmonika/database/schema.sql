CREATE DATABASE Haarmonika;

CREATE TABLE userRole (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

INSERT INTO userRole (name) VALUES ("employee", "customer");

CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    roleId INT NOT NULL
);

CREATE TABLE service (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    duration INT NOT NULL
);

CREATE TABLE booking (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employeeId INT NOT NULL,
    customerId INT NOT NULL,
    date INT NOT NULL,
    cancelled BOOL DEFAULT false
);

CREATE TABLE bookingService (
    bookingId INT,
    serviceId INT,
    PRIMARY KEY(bookingId, serviceId)
);