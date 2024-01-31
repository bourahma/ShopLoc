-- Deleting tables :
DROP TABLE IF EXISTS Utilisateur CASCADE;
DROP TABLE IF EXISTS Role CASCADE;
DROP TABLE IF EXISTS Commande CASCADE;
DROP TABLE IF EXISTS Token CASCADE;
DROP TABLE IF EXISTS Product CASCADE;
DROP TABLE IF EXISTS Commerce CASCADE;
DROP TABLE IF EXISTS Commerce_Product CASCADE;

DROP SEQUENCE IF EXISTS order_sequence CASCADE;
DROP SEQUENCE IF EXISTS utilisateur_sequence;
DROP SEQUENCE IF EXISTS commerce_sequence;
DROP SEQUENCE IF EXISTS product_sequence;

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

CREATE SEQUENCE order_sequence
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
                          closing_hour time NOT NULL,
                          image_url VARCHAR(255)
);

-- Create the User table :
CREATE TABLE Utilisateur (
                             utilisateur_id INT DEFAULT nextval('utilisateur_sequence') PRIMARY KEY,
                             username VARCHAR(255) UNIQUE NOT NULL,
                             lastname VARCHAR(255) NOT NULL,
                             firstname VARCHAR(255) NOT NULL,
                             password VARCHAR(255) NOT NULL,
                             email VARCHAR(255) UNIQUE NOT NULL,
                             enabled BOOLEAN NOT NULL,
                             phone_number VARCHAR(20),
                             role_id INT,
                             utilisateur_type VARCHAR(30),

                             -- Merchant attributes
                             subscription_date DATE,
                             commerce_id INT,

                             FOREIGN KEY (role_id) REFERENCES Role(role_id),
                             FOREIGN KEY (commerce_id) REFERENCES Commerce(commerce_id)
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

CREATE TABLE Token (
                            uuid VARCHAR(255) PRIMARY KEY,
                            utilisateur_id INT,

                            FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(utilisateur_id)
);

CREATE TABLE Commande (
                          order_id INT DEFAULT nextval('order_sequence') PRIMARY KEY,
                          utilisateur_id INT NOT NULL,
                          commerce_id INT NOT NULL,
                          order_date DATE,
                          status VARCHAR(50) NOT NULL,
                          FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(utilisateur_id),
                          FOREIGN KEY (commerce_id) REFERENCES Commerce(commerce_id)
);

/*CREATE TABLE commande_product (
                                  commande_id BIGINT NOT NULL,
                                  product_id BIGINT NOT NULL,
                                  quantity INT NOT NULL CHECK (quantity > 0),
                                  PRIMARY KEY (commande_id, product_id),
                                  FOREIGN KEY (commande_id) REFERENCES Commande(commande_id),
                                  FOREIGN KEY (product_id) REFERENCES Product(product_id)
);*/