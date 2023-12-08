-- Role's insert.
INSERT INTO Role (role_id, role_name)
VALUES
    (1, 'USER'),
    (2, 'ADMIN'),
    (3, 'ORGA');

-- User's insert.
-- Decoded password : 12345678
INSERT INTO Utilisateur (username, lastname, firstname, password, email, enabled, phone_number)
VALUES
    ('Joe', 'John','user', '$2a$10$jV8P6OmZreOsoqq5p1vp8O8vrvzHriyJBhVHvyKi1mMr5b9fb8yfC', 'john.doe@gmail.com', TRUE, '06 54 71 03 11'),
    ('Jane', 'Smith','user', '$2a$10$jV8P6OmZreOsoqq5p1vp8O8vrvzHriyJBhVHvyKi1mMr5b9fb8yfC', 'jane.smith@gmail.com', TRUE, '06 51 61 83 61'),
    ('Loris', 'Johnson', 'user', '$2a$10$jV8P6OmZreOsoqq5p1vp8O8vrvzHriyJBhVHvyKi1mMr5b9fb8yfC', 'michael.j@gmail.com', TRUE, '06 21 21 84 31');

-- User role's insertion :
INSERT INTO Utilisateurs_Roles (utilisateur_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);
