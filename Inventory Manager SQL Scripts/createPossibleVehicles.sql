create database if not exists possiblevehicles;
use possiblevehicles;
drop table if exists makes;

CREATE TABLE makes (
	Make varchar(64) DEFAULT NULL);
    
INSERT INTO makes (Make) VALUES ('Honda');


drop table if exists models;
CREATE TABLE models(
	Model varchar(64) DEFAULT NULL);
    
INSERT INTO models (Model) VALUES ('Honda:Odyssey');

drop table if exists vehicles;

CREATE TABLE vehicles (
	make varchar(64) NOT NULL,
    	model varchar(64) NOT NULL,
    	VIN varchar(64) NOT NULL);
