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

-- Commerce's insertion :
INSERT INTO Commerce (commerce_id, commerce_name, opening_hour, closing_hour) VALUES
    (nextval('commerce_sequence'), 'Boulangerie du Coin', '08:00:00', '18:00:00'),
    (nextval('commerce_sequence'), 'Le Petit Café', '09:00:00', '20:00:00'),
    (nextval('commerce_sequence'), 'Pizzeria Bella Napoli', '10:00:00', '17:00:00'),
    (nextval('commerce_sequence'), 'Magasin Magique', '07:30:00', '16:30:00'),
    (nextval('commerce_sequence'), 'Café des Artistes', '11:00:00', '19:30:00'),
    --
    (nextval('commerce_sequence'), 'Fleuriste Parfumé', '09:30:00', '17:00:00'),
    (nextval('commerce_sequence'), 'Artisan du Bois', '10:00:00', '18:00:00'),
    (nextval('commerce_sequence'), 'Délice du Café', '07:00:00', '21:00:00');

-- Product's insertion :
INSERT INTO Product (product_id, product_name, description, price, quantity) VALUES
    (nextval('product_sequence'), 'Pain au levain', 'Délicieux pain croustillant', 3.50, 150),
    (nextval('product_sequence'), 'Croissant aux amandes', 'Feuilleté et garni d''amandes', 2.75, 100),
    (nextval('product_sequence'), 'Tarte aux fruits', 'Tarte aux fruits de saison', 12.99, 20),
    (nextval('product_sequence'), 'Baguette traditionnelle', 'Baguette française classique', 1.99, 200),
    (nextval('product_sequence'), 'Éclair au chocolat', 'Éclair garni de crème pâtissière au chocolat', 4.50, 50),
    --
    (nextval('product_sequence'), 'Bouquet de Roses', 'Un assortiment de roses', 19.99, 50),
    (nextval('product_sequence'), 'Lys en Pot', 'Lys blanc dans un pot élégant', 24.99, 30),

    (nextval('product_sequence'), 'Bougeoir en Bois', 'Bougeoir fait à la main en bois', 29.99, 40),
    (nextval('product_sequence'), 'Plateau en Bois', 'Plateau élégant pour servir', 39.99, 25),

    (nextval('product_sequence'), 'Espresso', 'Café fort et concentré', 2.50, 100),
    (nextval('product_sequence'), 'Cappuccino', 'Espresso avec du lait mousseux', 3.50, 80),
    (nextval('product_sequence'), 'Croissant', 'Pâtisserie feuilletée et beurrée', 1.99, 150),
    (nextval('product_sequence'), 'Grains de café (250g)', 'Grains de café premium pour la préparation à la maison', 12.99, 50);




-- Commerce_Product data insertion :
INSERT INTO Commerce_product (commerce_id, product_id, quantity) VALUES
    (1, 1, 10),
    (2, 2, 20),
    (3, 3, 15),
    (4, 4, 25),
    (5, 5, 30),

    ((SELECT commerce_id FROM Commerce WHERE commerce_name = 'Fleuriste Parfumé'),
     (SELECT product_id FROM Product WHERE product_name = 'Bouquet de Roses'), 15),
    ((SELECT commerce_id FROM Commerce WHERE commerce_name = 'Fleuriste Parfumé'),
     (SELECT product_id FROM Product WHERE product_name = 'Lys en Pot'), 10),

    ((SELECT commerce_id FROM Commerce WHERE commerce_name = 'Artisan du Bois'),
     (SELECT product_id FROM Product WHERE product_name = 'Bougeoir en Bois'), 20),
    ((SELECT commerce_id FROM Commerce WHERE commerce_name = 'Artisan du Bois'),
     (SELECT product_id FROM Product WHERE product_name = 'Plateau en Bois'), 15),

    ((SELECT commerce_id FROM Commerce WHERE commerce_name = 'Délice du Café'),
     (SELECT product_id FROM Product WHERE product_name = 'Espresso'), 30),
    ((SELECT commerce_id FROM Commerce WHERE commerce_name = 'Délice du Café'),
     (SELECT product_id FROM Product WHERE product_name = 'Cappuccino'), 20),
    ((SELECT commerce_id FROM Commerce WHERE commerce_name = 'Délice du Café'),
     (SELECT product_id FROM Product WHERE product_name = 'Croissant'), 50),
    ((SELECT commerce_id FROM Commerce WHERE commerce_name = 'Délice du Café'),
     (SELECT product_id FROM Product WHERE product_name = 'Grains de café (250g)'), 10);

