-- Deleting tables :
DROP TABLE IF EXISTS Utilisateur CASCADE;
DROP TABLE IF EXISTS Role CASCADE;
DROP TABLE IF EXISTS Token CASCADE;
DROP TABLE IF EXISTS Utilisateurs_Roles CASCADE;
DROP SEQUENCE IF EXISTS utilisateur_sequence;

CREATE SEQUENCE utilisateur_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the Role table :
CREATE TABLE Role (
    role_id INT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    role_description VARCHAR(250)
);

-- Create the User table :
CREATE TABLE Utilisateur (
    utilisateur_id INT DEFAULT NEXTVAL('utilisateur_sequence') PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    lastname VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    enabled BOOLEAN NOT NULL,
    phone_number VARCHAR(20)
);

CREATE TABLE Utilisateurs_Roles (
    utilisateur_id INT,
    role_id INT,
    PRIMARY KEY (utilisateur_id, role_id),
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(utilisateur_id),
    FOREIGN KEY (role_id) REFERENCES Role(role_id)
);

-- Create the Role table :
CREATE TABLE Token (
    token_id INT DEFAULT NEXTVAL('token_sequence') PRIMARY KEY,
    uuid VARCHAR UNIQUE,
    utilisateur_id INT NOT NULL UNIQUE,
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur (utilisateur_id)
);
