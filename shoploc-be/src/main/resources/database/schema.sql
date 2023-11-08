-- Deleting tables :
DROP TABLE IF EXISTS Utilisateur CASCADE;
DROP TABLE IF EXISTS Role CASCADE;
DROP TABLE IF EXISTS Token CASCADE;
DROP SEQUENCE IF EXISTS utilisateur_sequence;

CREATE SEQUENCE utilisateur_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Create the Role table :
CREATE TABLE Role (
    role_id INT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    role_description VARCHAR(250)
);

-- Create the User table :
CREATE TABLE Utilisateur (
    utilisateur_id INT DEFAULT nextval('utilisateur_sequence') PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    enabled BOOLEAN NOT NULL,
    phone_number VARCHAR(20),
    uuid VARCHAR(36)
);

-- Create the Role table :
CREATE TABLE Token (
    uuid VARCHAR PRIMARY KEY,
    utilisateur_id INT NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur (utilisateur_id)
);