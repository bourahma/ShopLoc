-- Deleting tables :
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Merchant CASCADE;
DROP TABLE IF EXISTS Administrator CASCADE;

DROP TABLE IF EXISTS Role CASCADE;
DROP TABLE IF EXISTS Order_Product CASCADE;
DROP TABLE IF EXISTS Commande CASCADE;
DROP TABLE IF EXISTS Token CASCADE;
DROP TABLE IF EXISTS Product CASCADE;
DROP TABLE IF EXISTS Commerce CASCADE;
DROP TABLE IF EXISTS Commerce_Product CASCADE;
DROP TABLE IF EXISTS Order_Status CASCADE;
DROP TABLE IF EXISTS Fidelity_Card CASCADE;
DROP TABLE IF EXISTS Point_Transaction CASCADE;
DROP TABLE IF EXISTS Balance_Transaction CASCADE;

DROP SEQUENCE IF EXISTS order_sequence CASCADE;
DROP SEQUENCE IF EXISTS utilisateur_sequence CASCADE;
DROP SEQUENCE IF EXISTS balance_transaction_sequence CASCADE;
DROP SEQUENCE IF EXISTS point_transaction_sequence CASCADE;
DROP SEQUENCE IF EXISTS commerce_sequence CASCADE;
DROP SEQUENCE IF EXISTS product_sequence CASCADE;

CREATE SEQUENCE utilisateur_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE commerce_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE product_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE point_transaction_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE balance_transaction_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE order_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Create the Role Table :
CREATE TABLE Role (
                      role_id INT PRIMARY KEY,
                      role_name VARCHAR(50) NOT NULL,
                      role_description VARCHAR(250)
);

-- Create the Product Table :
CREATE TABLE Product (
                         product_id INT DEFAULT nextval('product_sequence') PRIMARY KEY,
                         product_name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price NUMERIC(10,2) NOT NULL,
                         quantity integer NOT NULL
);

-- Create the commerce Table :
CREATE TABLE Commerce (
                          commerce_id INT DEFAULT nextval('commerce_sequence') PRIMARY KEY,
                          commerce_name VARCHAR(255) NOT NULL,
                          opening_hour time NOT NULL,
                          closing_hour time NOT NULL,
                          image_url VARCHAR(255)
);

-- Create Customer Table :
CREATE TABLE Customer (
                             id INT DEFAULT nextval('utilisateur_sequence') PRIMARY KEY,
                             username VARCHAR(255) UNIQUE NOT NULL,
                             lastname VARCHAR(255) NOT NULL,
                             firstname VARCHAR(255) NOT NULL,
                             password VARCHAR(255) NOT NULL,
                             email VARCHAR(255) UNIQUE NOT NULL,
                             enabled BOOLEAN NOT NULL,
                             phone_number VARCHAR(20),
                             role INT NOT NULL,

                             FOREIGN KEY (role) REFERENCES Role (role_id)
);

-- Create FidelityCard Table :
CREATE TABLE Fidelity_Card (
                               fidelity_card_id VARCHAR(50) PRIMARY KEY,
                               customer_id INT NOT NULL,
                               points NUMERIC(10,2) NOT NULL,
                               balance NUMERIC(10,2) NOT NULL,

                               FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create the User table :
CREATE TABLE Merchant (
                             id INT DEFAULT nextval('utilisateur_sequence') PRIMARY KEY,
                             username VARCHAR(255) UNIQUE NOT NULL,
                             lastname VARCHAR(255) NOT NULL,
                             firstname VARCHAR(255) NOT NULL,
                             password VARCHAR(255) NOT NULL,
                             email VARCHAR(255) UNIQUE NOT NULL,
                             enabled BOOLEAN NOT NULL,
                             phone_number VARCHAR(20),
                             role INT NOT NULL,

                             -- Merchant attributes
                             subscription_date DATE,
                             commerce_id INT,

                             FOREIGN KEY (role) REFERENCES Role (role_id),
                             FOREIGN KEY (commerce_id) REFERENCES Commerce (commerce_id)
);

-- Create Administrator table :
CREATE TABLE Administrator (
                          id INT DEFAULT nextval('utilisateur_sequence') PRIMARY KEY,
                          username VARCHAR(255) UNIQUE NOT NULL,
                          lastname VARCHAR(255) NOT NULL,
                          firstname VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          enabled BOOLEAN NOT NULL,
                          phone_number VARCHAR(20),
                          role INT NOT NULL,

                          FOREIGN KEY (role) REFERENCES Role (role_id)
);

-- Create OrderStatus Table
CREATE TABLE Order_Status (
                                 order_status_id INT NOT NULL,
                                 label VARCHAR(255),
                                 description VARCHAR(250),
                                 PRIMARY KEY (order_status_id)
);

-- Create Commande Table
CREATE TABLE Commande (
                          order_id INT DEFAULT nextval('order_sequence') PRIMARY KEY,
                          customer_id INT NOT NULL,
                          commerce_id INT NOT NULL,
                          order_date DATE,
                          order_status_id INT NOT NULL,

                          FOREIGN KEY (customer_id) REFERENCES Customer(id),
                          FOREIGN KEY (order_status_id) REFERENCES Order_Status (order_status_id),
                          FOREIGN KEY (commerce_id) REFERENCES Commerce(commerce_id)
);

-- Create BalanceTransaction Table
CREATE TABLE Balance_Transaction (
                                    balance_transaction_id INT DEFAULT nextval('balance_transaction_sequence') PRIMARY KEY,
                                    transaction_date DATE NOT NULL,
                                    type VARCHAR(255) NOT NULL,
                                    amount NUMERIC(10,2) NOT NULL,
                                    commerce_id INT,
                                    fidelity_card_id VARCHAR(50) NOT NULL,

                                    FOREIGN KEY (commerce_id) REFERENCES Commerce (commerce_id),
                                    FOREIGN KEY (fidelity_card_id) REFERENCES Fidelity_Card (fidelity_card_id)
);

-- Create PointTransaction Table
CREATE TABLE Point_Transaction (
                                    point_transaction_id INT DEFAULT nextval('point_transaction_sequence') PRIMARY KEY,
                                    fidelity_card_id VARCHAR(50) NOT NULL,
                                    transaction_date DATE NOT NULL,
                                    type VARCHAR(255) NOT NULL,
                                    amount NUMERIC(10,2) NOT NULL,
                                    commerce_id INT NOT NULL,

                                    FOREIGN KEY (fidelity_card_id) REFERENCES Fidelity_Card (fidelity_card_id),
                                    FOREIGN KEY (commerce_id) REFERENCES Commerce (commerce_id)
);

-- Create the CommerceProduct table :
CREATE TABLE Commerce_Product (
                                  commerce_id INT NOT NULL,
                                  product_id INT NOT NULL,
                                  quantity INT NOT NULL,

                                  PRIMARY KEY (commerce_id, product_id),

                                  FOREIGN KEY (commerce_id) REFERENCES Commerce (commerce_id),
                                  FOREIGN KEY (product_id) REFERENCES Product (product_id)
);

-- Create the Token table :
CREATE TABLE Token (
                            uuid VARCHAR(255) PRIMARY KEY,
                            customer_id INT,

                            FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create OrderProduct Table
CREATE TABLE Order_Product (
                                  order_product_id INT NOT NULL,
                                  order_id INT NOT NULL,
                                  quantity INT NOT NULL CHECK (quantity > 0),

                                  PRIMARY KEY (order_product_id, order_id),
                                  FOREIGN KEY (order_id) REFERENCES Commande(order_id),
                                  FOREIGN KEY (order_product_id) REFERENCES Product(product_id)
);