-- Role's insert.
INSERT INTO Role (role_id, role_name)
VALUES
    (1, 'USER'),
    (2, 'ADMIN'),
    (3, 'ORGA');

-- User's insert.
-- Decoded password : secret
INSERT INTO Utilisateur (utilisateur_id, username, lastname, firstname, password, email, enabled, phone_number, uuid)
VALUES
    (1, 'Doe', 'John','user', '$2a$10$yw13I1lbEVER0UkPfwYsH.aQoRDPHnBi2QWdZzkFxm1iOg/0Xzija', 'john.doe@gmail.com', TRUE, '06 54 71 03 11', NULL),
    (2, 'Smith', 'Jane','user', '$2a$10$yw13I1lbEVER0UkPfwYsH.aQoRDPHnBi2QWdZzkFxm1iOg/0Xzija', 'jane.smith@gmail.com', TRUE, '06 51 61 83 61', NULL),
    (3, 'Johnson', 'Michael','user', '$2a$10$yw13I1lbEVER0UkPfwYsH.aQoRDPHnBi2QWdZzkFxm1iOg/0Xzija', 'michael.j@gmail.com', TRUE, '06 21 21 84 31', NULL),
    (4, 'Brown', 'Emma','user', '$2a$10$yw13I1lbEVER0UkPfwYsH.aQoRDPHnBi2QWdZzkFxm1iOg/0Xzija', 'emma.brown@gmail.com', TRUE,  NULL, NULL),
    (5, 'Wilson', 'David','user', '$2a$10$yw13I1lbEVER0UkPfwYsH.aQoRDPHnBi2QWdZzkFxm1iOg/0Xzija', 'david.wilson@gmail.com', TRUE,  '06 61 61 66 61', NULL);

-- User role's insertion :
INSERT INTO Utilisateurs_Roles (utilisateur_id, role_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 1),
    (4, 3),
    (5, 2);