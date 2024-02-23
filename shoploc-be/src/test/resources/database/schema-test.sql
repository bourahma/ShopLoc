-- Deleting tables :
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Merchant CASCADE;
DROP TABLE IF EXISTS Administrator CASCADE;

DROP TABLE IF EXISTS Role CASCADE;
DROP TABLE IF EXISTS Order_Product CASCADE;
DROP TABLE IF EXISTS Orders CASCADE;
DROP TABLE IF EXISTS Token CASCADE;
DROP TABLE IF EXISTS Product CASCADE;
DROP TABLE IF EXISTS Commerce CASCADE;
DROP TABLE IF EXISTS Commerce_Product CASCADE;
DROP TABLE IF EXISTS Order_Status CASCADE;
DROP TABLE IF EXISTS Fidelity_Card CASCADE;
DROP TABLE IF EXISTS Point_Transaction CASCADE;
DROP TABLE IF EXISTS Balance_Transaction CASCADE;
DROP TABLE IF EXISTS Benefit CASCADE;
DROP TABLE IF EXISTS Benefit_History CASCADE;
DROP TABLE IF EXISTS Gift_History CASCADE;
DROP TABLE IF EXISTS Promotion CASCADE;
DROP TABLE IF EXISTS VFP CASCADE;
DROP TABLE IF EXISTS Commerce_Type CASCADE;
DROP TABLE IF EXISTS Customer_Connection CASCADE;
DROP TABLE IF EXISTS Viewed_Product CASCADE;
DROP TABLE IF EXISTS VFP_History CASCADE;
DROP TABLE IF EXISTS Address CASCADE;
DROP TABLE IF EXISTS QR_Code_Payment CASCADE;

DROP SEQUENCE IF EXISTS order_sequence CASCADE;
DROP SEQUENCE IF EXISTS address_sequence CASCADE;
DROP SEQUENCE IF EXISTS utilisateur_sequence CASCADE;
DROP SEQUENCE IF EXISTS balance_transaction_sequence CASCADE;
DROP SEQUENCE IF EXISTS point_transaction_sequence CASCADE;
DROP SEQUENCE IF EXISTS commerce_sequence CASCADE;
DROP SEQUENCE IF EXISTS product_sequence CASCADE;
DROP SEQUENCE IF EXISTS benefit_sequence CASCADE;
DROP SEQUENCE IF EXISTS promotion_sequence CASCADE;
DROP SEQUENCE IF EXISTS gift_history_sequence CASCADE;
DROP SEQUENCE IF EXISTS benefit_history_sequence CASCADE;
DROP SEQUENCE IF EXISTS commerce_type_sequence CASCADE;

CREATE SEQUENCE address_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE commerce_type_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE gift_history_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE benefit_history_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE promotion_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE benefit_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

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

-- Create the Commerce_Type Table :
CREATE TABLE Commerce_Type (
                               commerce_type_id INT DEFAULT nextval('commerce_type_sequence') PRIMARY KEY,
                               label VARCHAR(255) NOT NULL,
                               description time NOT NULL
);

-- Create Address Table :
CREATE TABLE Address (
                         address_id INT DEFAULT nextval('address_sequence') PRIMARY KEY,
                         street VARCHAR(255) NOT NULL,
                         postal_code INT NOT NULL,
                         city VARCHAR(255) NOT NULL,
                         latitude NUMERIC NOT NULL,
                         longitude NUMERIC NOT NULL
);

-- Create the commerce Table :
CREATE TABLE Commerce (
                          commerce_id INT DEFAULT nextval('commerce_sequence') PRIMARY KEY,
                          commerce_name VARCHAR(255) NOT NULL,
                          opening_hour time NOT NULL,
                          closing_hour time NOT NULL,
                          image_url VARCHAR(255),
                          commerce_type_id INT,
                          address_id INT,

                          FOREIGN KEY (commerce_type_id) REFERENCES Commerce_Type (commerce_type_id),
                          FOREIGN KEY (address_id) REFERENCES Address (address_id)
);

-- Create the Product Table :
CREATE TABLE Product (
                         product_id INT DEFAULT nextval('product_sequence') PRIMARY KEY,
                         product_name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price NUMERIC(10,2) NOT NULL,
                         quantity integer NOT NULL,
                         reward_points_price NUMERIC(10,2),
                         is_gift BOOLEAN NOT NULL,
                         discount_id INT,
                         commerce_id INT,
                         view INT,

                         FOREIGN KEY (commerce_id) REFERENCES Commerce(commerce_id)
);

-- Create FidelityCard Table :
CREATE TABLE Fidelity_Card (
                               fidelity_card_id VARCHAR(50) PRIMARY KEY,
                               points NUMERIC(10,2) NOT NULL,
                               balance NUMERIC(10,2) NOT NULL
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
                          is_vfp_membership BOOLEAN DEFAULT false,
                          fidelity_card_id VARCHAR(255),

                          FOREIGN KEY (fidelity_card_id) REFERENCES Fidelity_Card (fidelity_card_id),
                          FOREIGN KEY (role) REFERENCES Role (role_id)
);


-- Create Customer_Connection Table :
CREATE TABLE Customer_Connection (
                                     connection_id VARCHAR(255) PRIMARY KEY,
                                     connect_time TIME,
                                     disconnect_time TIME,
                                     customer_id INT,

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

-- Create Orders Table
CREATE TABLE Orders (
                        order_id INT DEFAULT nextval('order_sequence') PRIMARY KEY,
                        customer_id INT NOT NULL,
                        commerce_id INT NOT NULL,
                        order_date DATE,
                        order_status VARCHAR(250),

                        FOREIGN KEY (customer_id) REFERENCES Customer(id),
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

-- Create the Token table :
CREATE TABLE Token (
                       uuid VARCHAR(255) PRIMARY KEY,
                       customer_id INT,

                       FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create OrderProduct Table :
CREATE TABLE Order_Product (
                               order_product_id INT NOT NULL,
                               order_id INT NOT NULL,
                               quantity INT NOT NULL CHECK (quantity > 0),

                               PRIMARY KEY (order_product_id, order_id),
                               FOREIGN KEY (order_id) REFERENCES Orders(order_id),
                               FOREIGN KEY (order_product_id) REFERENCES Product(product_id)
);

-- Create Benefits Table :
CREATE TABLE Benefit (
                         benefit_id INT DEFAULT nextval('benefit_sequence') PRIMARY KEY,
                         description VARCHAR(255)
);

-- Create BenefitHistory Table :
CREATE TABLE Benefit_History (
                                 benefit_history_id INT DEFAULT nextval('benefit_history_sequence') PRIMARY KEY,
                                 dateAcquisition DATE,
                                 customer_id INT,
                                 benefit_id INT,

                                 FOREIGN KEY (customer_id) REFERENCES Customer(id),
                                 FOREIGN KEY (benefit_id) REFERENCES Benefit(benefit_id)
);

-- Create GiftHistory Table :
CREATE TABLE Gift_History (
                              gift_history_id INT DEFAULT nextval('gift_history_sequence') PRIMARY KEY,
                              purchase_date DATE,
                              customer_id INT,
                              product_id INT,

                              FOREIGN KEY (customer_id) REFERENCES Customer(id),
                              FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

-- Create Promotion Table :
CREATE TABLE Promotion (
                           promotion_id INT DEFAULT nextval('promotion_sequence') PRIMARY KEY,
                           start_date DATE NOT NULL,
                           end_date DATE NOT NULL,
                           product_id INT NULL,
                           description VARCHAR(255) NOT NULL,
                           type VARCHAR(255) NOT NULL,

    -- For discount promotion type :
                           discount_percent INT,

    -- For Offer promotion type :
                           required_items INT,
                           offered_items INT,

                           FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

-- Create VFP_History Table
CREATE TABLE VFP_History (
                             vfp_update_id INT DEFAULT nextval('promotion_sequence') PRIMARY KEY,
                             customer_id INT UNIQUE NOT NULL,
                             granted_date DATE,
                             validity_date DATE,

                             FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create QR_Code_Payment Table
CREATE TABLE QR_Code_Payment (
                                 qr_code_payment_id VARCHAR(255) PRIMARY KEY,
                                 customer_id INT,
                                 order_id INT,
                                 duration TIME,

                                 FOREIGN KEY (customer_id) REFERENCES Customer(id),
                                 FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

-- Function to update VFP status after each purchase
CREATE OR REPLACE FUNCTION update_vfp_status()
RETURNS TRIGGER AS '
    BEGIN
        IF (SELECT COUNT(*) FROM Orders
            WHERE customer_id = NEW.customer_id
            AND order_date >= CURRENT_DATE - INTERVAL ''15 days'') >= 10 THEN
            IF EXISTS (SELECT 1 FROM VFP_Status WHERE customer_id = NEW.customer_id) THEN
                UPDATE Customer
                SET is_vfp_membership = TRUE
                WHERE id = NEW.customer_id;
            ELSE
                INSERT INTO VFP_Status (customer_id, is_vfp, last_evaluation)
                VALUES (NEW.customer_id, TRUE, CURRENT_DATE);
            END IF;
        END IF;

        RETURN NEW;
    END;
' LANGUAGE plpgsql;

-- Function to update VFP status nightly
CREATE OR REPLACE FUNCTION update_vfp_status_nightly()
RETURNS void AS '
BEGIN
    -- Logic to mark customers as non-VFP if they don''t meet the criteria
    UPDATE VFP_Status
    SET enabled = FALSE, last_evaluation = CURRENT_DATE
    WHERE customer_id IN (
        SELECT v.customer_id
        FROM VFP v
                 LEFT JOIN (
            SELECT customer_id, COUNT(*) as recent_orders
            FROM Orders
            WHERE order_date >= CURRENT_DATE - INTERVAL ''15 days''
            GROUP BY customer_id
        ) as recent ON v.customer_id = recent.customer_id
        WHERE v.enabled = TRUE AND (recent.recent_orders IS NULL OR recent.recent_orders < 10)
    );
END;
' LANGUAGE plpgsql;

-- Trigger to execute the update_vfp_status function after each new order insertion
CREATE TRIGGER after_order_insert
    AFTER INSERT ON Orders
    FOR EACH ROW
    EXECUTE FUNCTION update_vfp_status();
