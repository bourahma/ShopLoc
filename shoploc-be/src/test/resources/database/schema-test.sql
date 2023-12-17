-- Deleting tables :
DROP TABLE IF EXISTS Utilisateur CASCADE;
DROP TABLE IF EXISTS Role CASCADE;
DROP TABLE IF EXISTS Token CASCADE;
DROP TABLE IF EXISTS Utilisateurs_Roles CASCADE;
DROP TABLE IF EXISTS Product CASCADE;
DROP TABLE IF EXISTS Commerce CASCADE;
DROP TABLE IF EXISTS Commerce_Product CASCADE;
DROP SEQUENCE IF EXISTS utilisateur_sequence;
DROP SEQUENCE IF EXISTS commerce_sequence;
DROP SEQUENCE IF EXISTS product_sequence;

CREATE SEQUENCE utilisateur_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE commerce_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE product_sequence
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

-- Create the Product table :
CREATE TABLE Product (
                         product_id INT DEFAULT nextval('product_sequence') PRIMARY KEY,
                         product_name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price NUMERIC(10,2) NOT NULL,
                         quantity integer NOT NULL
);

-- Create the commerce table :
CREATE TABLE Commerce (
                          commerce_id INT DEFAULT nextval('commerce_sequence') PRIMARY KEY,
                          commerce_name VARCHAR(255) NOT NULL,
                          opening_hour time NOT NULL,
                          closing_hour time NOT NULL
);

-- Create the Commerce_Product table :
CREATE TABLE Commerce_Product (
                                  commerce_id INT NOT NULL,
                                  product_id INT NOT NULL,
                                  quantity INT NOT NULL,
                                  PRIMARY KEY (commerce_id, product_id),
                                  FOREIGN KEY (commerce_id) REFERENCES commerce (commerce_id),
                                  FOREIGN KEY (product_id) REFERENCES product (product_id)
);
