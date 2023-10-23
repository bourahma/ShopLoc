-- Role's insert.
INSERT INTO Role (role_id, role_name)
VALUES
    (1, 'USER'),
    (2, 'ADMIN'),
    (3, 'ORGA');

-- User's insert.
INSERT INTO Utilisateur (utilisateur_id, username, lastname, firstname, password, email, enabled, phone_number, role_id)
VALUES
    (1, 'Doe', 'John','user', 'password123', 'john.doe@gmail.com', TRUE, '06 54 71 03 11', 1),
    (2, 'Smith', 'Jane','user', 'securepwd', 'jane.smith@gmail.com', TRUE, '06 51 61 83 61', 2);