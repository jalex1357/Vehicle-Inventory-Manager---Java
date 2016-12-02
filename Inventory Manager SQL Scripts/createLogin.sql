create database if not exists login;
use login;
drop table if exists users;

CREATE TABLE users(
	employeeId int(11) NOT NULL AUTO_INCREMENT,
	username varchar(64) DEFAULT NULL,
	passcode varchar(64) DEFAULT NULL,
	firstName varchar(64) DEFAULT NULL,
	lastName varchar(64) DEFAULT NULL,
	statusLevel varchar(64) DEFAULT NULL,
	isPasswordDefault varchar(64) DEFAULT NULL,
	PRIMARY KEY (employeeId)
	)ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = latin1;

INSERT INTO users (employeeId, username, passcode, firstName, lastName, statusLevel, accountStatus, employeeStatus, dept)
	VALUES (1, 'test', 'testaccount', 'Test', 'Account', 'Admin', 'Normal', 'Normal', 'Technology', 'Default');
INSERT INTO users (employeeId, username, passcode, firstName, lastName, statusLevel, accountStatus, employeeStatus, dept)
	VALUES (2, 'johnathan', 'password', 'Johnathan', 'Alexander', 'New Hire', 'Normal', 'Normal', 'Technology', 'Default');
    

use login;
drop table if exists dept;

CREATE TABLE dept (
	Department varchar(64) DEFAULT NULL
    );
    
use login;
INSERT INTO dept (Department) VALUES ('Human Resources');
INSERT INTO dept (Department) VALUES ('Technology');
INSERT INTO dept (Department) VALUES ('Sales');
