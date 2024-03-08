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
DROP TABLE IF EXISTS Promotion_History CASCADE;

DROP TABLE IF EXISTS VFP CASCADE;
DROP TABLE IF EXISTS Commerce_Type CASCADE;
DROP TABLE IF EXISTS Customer_Connection CASCADE;
DROP TABLE IF EXISTS Viewed_Product CASCADE;
DROP TABLE IF EXISTS VFP_History CASCADE;
DROP TABLE IF EXISTS Address CASCADE;
DROP TABLE IF EXISTS QR_Code_Payment CASCADE;
DROP TABLE IF EXISTS Product_Category CASCADE;

DROP SEQUENCE IF EXISTS product_category_sequence CASCADE;
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
CREATE TABLE Role
(
    role_id INT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    role_description VARCHAR(250)
);

-- Create the Commerce_Type Table :
CREATE TABLE Commerce_Type
(
    commerce_type_id INT DEFAULT nextval('commerce_type_sequence') PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

-- Create Address Table :
CREATE TABLE Address
(
    address_id INT DEFAULT nextval('address_sequence') PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    postal_code INT NOT NULL,
    city VARCHAR(255) NOT NULL,
    latitude NUMERIC NOT NULL,
    longitude NUMERIC NOT NULL
);

-- Create the commerce Table :
CREATE TABLE Commerce
(
    commerce_id INT DEFAULT nextval('commerce_sequence') PRIMARY KEY,
    commerce_name VARCHAR(255) NOT NULL,
    opening_hour time NOT NULL,
    closing_hour time NOT NULL,
    image_url VARCHAR(255),
    commerce_type_id INT NOT NULL,
    address_id INT,
    disabled BOOLEAN NOT NULL,

    FOREIGN KEY (commerce_type_id) REFERENCES Commerce_Type (commerce_type_id),
    FOREIGN KEY (address_id) REFERENCES Address (address_id)
);

-- Create the Category Table :
CREATE TABLE Product_Category
(
    product_category_id INT PRIMARY KEY,
    commerce_id INT NOT NULL,
    label VARCHAR(100) NOT NULL,
    description VARCHAR(255),

    FOREIGN KEY (commerce_id) REFERENCES Commerce (commerce_id)
);

-- Create the Product Table :
CREATE TABLE Product
(
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
    image_url VARCHAR(255),
    product_category_id INT NOT NULL,

    FOREIGN KEY (product_category_id) REFERENCES Product_Category(product_category_id),
    FOREIGN KEY (commerce_id) REFERENCES Commerce(commerce_id)
);

-- Create Promotion Table :
CREATE TABLE Promotion
(
    promotion_id INT DEFAULT nextval('promotion_sequence') PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    commerce_id INT NOT NULL,
    product_id INT UNIQUE,

    -- For discount promotion type :
    discount_percent INT,

    -- For Offer promotion type :
    required_items INT,
    offered_items INT,

    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (commerce_id) REFERENCES Commerce (commerce_id)
);

-- Create Promotion_History Table :
CREATE TABLE Promotion_History
(
    promotion_history_id INT PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    commerce_id INT,
    product_id INT NOT NULL,

    -- For discount promotion type :
    discount_percent INT,

    -- For Offer promotion type :
    required_items INT,
    offered_items INT,

    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (commerce_id) REFERENCES Commerce (commerce_id)
);

-- Create FidelityCard Table :
CREATE TABLE Fidelity_Card
(
    fidelity_card_id VARCHAR(50) PRIMARY KEY,
    points NUMERIC(10,2) NOT NULL,
    balance NUMERIC(10,2) NOT NULL
);

-- Create Customer Table :
CREATE TABLE Customer
(
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
    subscription_date DATE,

    FOREIGN KEY (fidelity_card_id) REFERENCES Fidelity_Card (fidelity_card_id),
    FOREIGN KEY (role) REFERENCES Role (role_id)
);

-- Create Customer_Connection Table :
CREATE TABLE Customer_Connection
(
    connection_id VARCHAR(255) PRIMARY KEY,
    connect_time TIME,
    disconnect_time TIME,
    customer_id INT,

    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create the User table :
CREATE TABLE Merchant
(
    id INT DEFAULT nextval('utilisateur_sequence') PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    enabled BOOLEAN NOT NULL,
    phone_number VARCHAR(20),
    role INT NOT NULL,
    subscription_date DATE,
    commerce_id INT,

    FOREIGN KEY (role) REFERENCES Role (role_id),
    FOREIGN KEY (commerce_id) REFERENCES Commerce (commerce_id)
);

-- Create Administrator table :
CREATE TABLE Administrator
(
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
CREATE TABLE Order_Status
(
    order_status_id INT NOT NULL,
    label VARCHAR(255),
    description VARCHAR(250),
    PRIMARY KEY (order_status_id)
);

-- Create Orders Table
CREATE TABLE Orders
(
    order_id INT DEFAULT nextval('order_sequence') PRIMARY KEY,
    customer_id INT NOT NULL,
    commerce_id INT NOT NULL,
    order_date DATE,
    order_status VARCHAR(250),

    FOREIGN KEY (customer_id) REFERENCES Customer(id),
    FOREIGN KEY (commerce_id) REFERENCES Commerce(commerce_id)
);

-- Create BalanceTransaction Table
CREATE TABLE Balance_Transaction
(
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
CREATE TABLE Point_Transaction
(
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
CREATE TABLE Token
(
    uuid VARCHAR(255) PRIMARY KEY,
    customer_id INT,

    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create OrderProduct Table :
CREATE TABLE Order_Product
(
    order_product_id INT NOT NULL,
    order_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    promotion_id INT,
    purchase_price NUMERIC(4,2),

    PRIMARY KEY (order_product_id, order_id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (promotion_id) REFERENCES Promotion(promotion_id),
    FOREIGN KEY (order_product_id) REFERENCES Product(product_id)
);

-- Create Benefit Table :
CREATE TABLE Benefit
(
    benefit_id INT DEFAULT nextval('benefit_sequence') PRIMARY KEY,
    benefit_available BOOLEAN,
    image_url VARCHAR(255),
    description VARCHAR(255)
);

-- Create Benefit_History Table :
CREATE TABLE Benefit_History
(
    benefit_history_id INT DEFAULT nextval('benefit_history_sequence') PRIMARY KEY,
    qr_code VARCHAR(255) NOT NULL,
    acquisition_date DATE,
    acquisition_time TIME,
    customer_id INT,
    benefit_id INT,

    FOREIGN KEY (customer_id) REFERENCES Customer(id),
    FOREIGN KEY (benefit_id) REFERENCES Benefit(benefit_id)
);


-- Create GiftHistory Table :
CREATE TABLE Gift_History
(
    gift_history_id INT DEFAULT nextval('gift_history_sequence') PRIMARY KEY,
    purchase_date DATE,
    customer_id INT,
    product_id INT,

    FOREIGN KEY (customer_id) REFERENCES Customer(id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

-- Create VFP_History Table
CREATE TABLE VFP_History
(
    vfp_update_id INT DEFAULT nextval('promotion_sequence') PRIMARY KEY,
    customer_id INT UNIQUE NOT NULL,
    granted_date DATE,
    validity_date DATE,

    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Create QR_Code_Payment Table
CREATE TABLE QR_Code_Payment
(
    qr_code_payment_id VARCHAR(255) PRIMARY KEY,
    customer_id INT,
    order_id INT,
    duration TIME,

    FOREIGN KEY (customer_id) REFERENCES Customer(id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

-- Batch tables :
DROP SEQUENCE IF EXISTS BATCH_STEP_EXECUTION_SEQ CASCADE;
DROP SEQUENCE IF EXISTS BATCH_JOB_EXECUTION_SEQ CASCADE;
DROP SEQUENCE IF EXISTS BATCH_JOB_SEQ CASCADE;

CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE BATCH_JOB_SEQ MAXVALUE 9223372036854775807 NO CYCLE;

DROP TABLE IF EXISTS BATCH_JOB_INSTANCE CASCADE;
CREATE TABLE BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT ,
    JOB_NAME VARCHAR(100) NOT NULL,
    JOB_KEY VARCHAR(32) NOT NULL,
    constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;

DROP TABLE IF EXISTS BATCH_JOB_EXECUTION CASCADE;
CREATE TABLE BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT  ,
    JOB_INSTANCE_ID BIGINT NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL,
    START_TIME TIMESTAMP DEFAULT NULL ,
    END_TIME TIMESTAMP DEFAULT NULL ,
    STATUS VARCHAR(10) ,
    EXIT_CODE VARCHAR(2500) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED TIMESTAMP,
    constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
        references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;

DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS CASCADE;
CREATE TABLE BATCH_JOB_EXECUTION_PARAMS
(
    JOB_EXECUTION_ID BIGINT NOT NULL ,
    PARAMETER_NAME VARCHAR(100) NOT NULL ,
    PARAMETER_TYPE VARCHAR(100) NOT NULL ,
    PARAMETER_VALUE VARCHAR(2500) ,
    IDENTIFYING CHAR(1) NOT NULL ,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

DROP TABLE IF EXISTS BATCH_STEP_EXECUTION CASCADE;
CREATE TABLE BATCH_STEP_EXECUTION
(
    STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
    VERSION BIGINT NOT NULL,
    STEP_NAME VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID BIGINT NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL,
    START_TIME TIMESTAMP DEFAULT NULL ,
    END_TIME TIMESTAMP DEFAULT NULL ,
    STATUS VARCHAR(10) ,
    COMMIT_COUNT BIGINT ,
    READ_COUNT BIGINT ,
    FILTER_COUNT BIGINT ,
    WRITE_COUNT BIGINT ,
    READ_SKIP_COUNT BIGINT ,
    WRITE_SKIP_COUNT BIGINT ,
    PROCESS_SKIP_COUNT BIGINT ,
    ROLLBACK_COUNT BIGINT ,
    EXIT_CODE VARCHAR(2500) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED TIMESTAMP,
    constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT CASCADE;
CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT
(
    STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT ,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
        references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT CASCADE;
CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT
(
    JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT ,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;