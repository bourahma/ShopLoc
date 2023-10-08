-- Role's insert.
INSERT INTO Role (role_id, role_name)
VALUES
    (1, 'USER'),
    (2, 'ADMIN'),
    (3, 'ORGA');

-- User's insert.
INSERT INTO Utilisateur (utilisateur_id, lastname, firstname, password, email, phone_number, role_id)
VALUES
    (1, 'Doe', 'John', 'password123', 'john.doe@gmail.com', '06 54 71 03 11', 1),
    (2, 'Smith', 'Jane', 'securepwd', 'jane.smith@gmail.com', '06 51 61 83 61', 2),
    (3, 'Johnson', 'Michael', 'mysecret', 'michael.j@gmail.com', '06 21 21 84 31', 1),
    (4, 'Brown', 'Emma', 'pass123', 'emma.brown@gmail.com', NULL, 3),
    (5, 'Wilson', 'David', 'secret01', 'david.wilson@gmail.com', '06 61 61 66 61', 2);