-- FidelityCard data insertion :
INSERT INTO Fidelity_Card (fidelity_card_id, points, balance)
VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 54.50, 49.50),
    ('723a4867-e89b-16l3-a858-856918174111', 84.00, 19.50);


-- Role's insert.
INSERT INTO Role (role_id, role_name)
VALUES
    (1, 'CUSTOMER'),
    (2, 'ADMINISTRATOR'),
    (3, 'MERCHANT');

-- User's insert.
-- Decoded password : 12345678
INSERT INTO Merchant (id, username, lastname, firstname, password, email, enabled, phone_number, role, subscription_date, commerce_id)
VALUES
    (3, 'Loris', 'Johnson', 'user', '$2a$10$jV8P6OmZreOsoqq5p1vp8O8vrvzHriyJBhVHvyKi1mMr5b9fb8yfC', 'michael.j@gmail.com', TRUE, '06 21 21 84 31', 3, '2024-01-24', NULL);

INSERT INTO Customer (id, username, lastname, firstname, password, email, enabled, phone_number, role, is_vfp_membership, fidelity_card_id)
VALUES
    (1, 'Joe', 'John','user', '$2a$10$jV8P6OmZreOsoqq5p1vp8O8vrvzHriyJBhVHvyKi1mMr5b9fb8yfC', 'az.az201221@gmail.com', TRUE, '06 54 71 03 11', 1, TRUE, '123e4567-e89b-12d3-a456-426614174000'),
    (2, 'Mohammed', 'El Khir','user', '$2a$10$jV8P6OmZreOsoqq5p1vp8O8vrvzHriyJBhVHvyKi1mMr5b9fb8yfC', 'mohammed@gmail.com', FALSE, '06 54 71 03 11', 1, TRUE, '723a4867-e89b-16l3-a858-856918174111');

-- Create the Token table :
INSERT INTO Token (uuid, customer_id)
VALUES
    ('123e4567-a456-426614174000-e89b-12d3', 2);

INSERT INTO Administrator (id, username, lastname, firstname, password, email, enabled, phone_number, role)
VALUES
    (2, 'Jane', 'Smith','user', '$2a$10$jV8P6OmZreOsoqq5p1vp8O8vrvzHriyJBhVHvyKi1mMr5b9fb8yfC', 'aziz.bourahma.etu@univ-lille.fr', TRUE, '06 51 61 83 61', 2);

-- Address's insertion :
INSERT INTO Address (address_id, street, postal_code, city, latitude, longitude)
VALUES
    (1, '1 Rue de la Clef', 59000, 'Lille', 50.6333, 3.0600),
    (2, '12 Rue Nationale', 59000, 'Lille', 50.6311, 3.0578),
    (3, '8 Rue de la Monnaie', 59000, 'Lille', 50.6356, 3.0611),
    (4, '5 Rue Esquermoise', 59000, 'Lille', 50.6375, 3.0631),
    (5, '23 Rue de Paris', 59000, 'Lille', 50.6300, 3.0556),
    (6, '18 Rue des Ponts de Comines', 59000, 'Lille', 50.6389, 3.0600),
    (7, '30 Rue de la Barre', 59000, 'Lille', 50.6378, 3.0572),
    (8, '10 Rue de Béthune', 59000, 'Lille', 50.6294, 3.0639),
    (9, 'NULL', 59000, 'NULL', 0.0, 0.0);

-- CommerceType's insertion :
INSERT INTO Commerce_Type (commerce_type_id, label, description)
VALUES
    (1, 'Boulangerie', 'Commerce spécialisé dans la vente de produits de boulangerie, tels que du pain, des viennoiseries et des pâtisseries.'),
    (2, 'Épicerie', 'Commerce de détail où l''on trouve divers produits alimentaires et parfois des produits ménagers et d''hygiène.'),
    (3, 'Boucherie', 'Commerce spécialisé dans la vente de viande et de produits carnés.'),
    (4, 'Pâtisserie', 'Commerce spécialisé dans la vente de gâteaux, desserts et sucreries.'),
    (5, 'Magasin de jouets', 'Commerce spécialisé dans la vente de jouets et de jeux pour enfants.'),
    (6, 'Pharmacie', 'Commerce spécialisé dans la vente de médicaments et de produits de santé.'),
    (7, 'Librairie', 'Commerce spécialisé dans la vente de livres et de produits culturels.'),
    (8, 'Fleuriste', 'Commerce spécialisé dans la vente de fleurs et de plantes.'),
    (9, 'Quincaillerie', 'Commerce spécialisé dans la vente d''outils, de matériaux de construction et d''articles de bricolage.'),
    (10, 'Boutique de vêtements', 'Commerce spécialisé dans la vente de vêtements, chaussures et accessoires.'),
    (11, 'Magasin de meubles', 'Commerce spécialisé dans la vente de mobilier et d''articles d''ameublement.'),
    (12, 'Caviste', 'Commerce spécialisé dans la vente de vins, spiritueux et produits liés à la dégustation.'),
    (13, 'Parfumerie', 'Commerce spécialisé dans la vente de parfums et de produits de beauté.'),
    (14, 'Cafétéria', 'Commerce proposant une variété de boissons, de snacks et de repas rapides.'),
    (15, 'Restaurant gastronomique', 'Restaurant offrant une cuisine de haute qualité, souvent créative et raffinée, avec un service attentif.'),
    (16, 'Pizzeria', 'Commerce spécialisé dans la préparation et la vente de pizzas, généralement cuites au four.');


-- Commerce's insertion :
INSERT INTO Commerce (commerce_id, commerce_name, opening_hour, closing_hour, commerce_type_id, image_url, address_id, disabled)
VALUES
    (1, 'Boulangerie du Coin', '08:00:00', '18:00:00', 1, 'https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/mae-mu-_h-2jrL9cMU-unsplash.jpg', 1, FALSE),
    (2, 'Le Petit Café', '09:00:00', '20:00:00', 14, 'https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/iulia-topan-rI4ccCl8VwQ-unsplash.jpg', 2, FALSE),
    (3, 'Pizzeria Bella Napoli', '10:00:00', '17:00:00', 16, 'https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/pablo-pacheco-D3Mag4BKqns-unsplash.jpg', 3, FALSE),
    (4, 'Magasin Magique', '07:30:00', '16:30:00', 11, 'https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/brooke-lark-Uto4sJ8e_5k-unsplash.jpg', 4, FALSE),
    (5, 'Café des Artistes', '11:00:00', '19:30:00', 14, 'https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/oscar-nord-6rgwUHMokWU-unsplash.jpg', 5, FALSE),
    (6, 'Fleuriste Parfumé', '09:30:00', '17:00:00', 8, 'https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/roman-kraft-_oH66az_yug-unsplash.jpg', 6, FALSE),
    (7, 'Artisan du Bois', '10:00:00', '18:00:00', 11, 'https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/bailey-alexander-DAd_Wn6Mj78-unsplash.jpg', 7, FALSE),
    (8, 'Délice du Café', '07:00:00', '21:00:00', 14, 'https://acdnocowtfyjmqeomcec.supabase.co/storage/v1/object/public/shoploc-bucket/milo-miloezger-rKYRJu0n06Y-unsplash.jpg', 8, FALSE),
    (9, 'NULL', '07:00:00', '21:00:00', 15, 'NULL', 9, TRUE);

--  ProductCategory's insertion :
INSERT INTO Product_Category (product_category_id, commerce_id, label, description)
VALUES
    (1, 1, 'Pain', 'Variété de pains frais et croustillants.'),
    (2, 1, 'Viennoiseries', 'Délicieux assortiment de viennoiseries fraîches, telles que des croissants et des pains au chocolat.'),
    (3, 1, 'Pâtisseries', 'Découvrez des pâtisseries comme jamais auparavant.'),
    (4, 8, 'Viennoiseries', 'Viennoiseries maison.'),
    (5, 5, 'Pâtisseries', 'Délicieuses pâtisseries françaises.'),
    (6, 6, 'Bouquets', 'Bouquets de fleurs magnifiques.'),
    (7, 4, 'Bougeoir', 'Exquis porte-bougies.'),
    (8, 4, 'Plateaux', 'Élégants plateaux.'),
    (9, 2, 'Cafés', 'Large sélection de cafés riches.'),
    (10, 2, 'Café au lait', 'Classique café au lait.'),
    (11, 5, 'Viennoiseries', 'Délicieuses viennoiseries.'),
    (12, 8, 'Graines', 'Sélection de grains de café.');

-- Product's insertion :
INSERT INTO Product (product_id, product_name, description, price, quantity, reward_points_price, is_gift, discount_id, commerce_id, product_category_id, view)
VALUES
    (1, 'Pain au levain', 'Délicieux pain croustillant', 3.50, 150, 3, TRUE, 0, 1, 1, 12),
    (2, 'Croissant aux amandes', 'Feuilleté et garni d''amandes', 2.75, 100, 2, TRUE, 2, 8, 4, 45),
    (3, 'Tarte aux fruits', 'Tarte aux fruits de saison', 12.99, 20, 12, TRUE, 1, 5, 5, 15),
    (4, 'Baguette traditionnelle', 'Baguette française classique', 1.99, 200, 1, TRUE, 1, 1, 1, 12),
    (5, 'Éclair au chocolat', 'Éclair garni de crème pâtissière au chocolat', 4.50, 50, 4, TRUE, 1, 5, 5, 35),
    (6, 'Bouquet de Roses', 'Un assortiment de roses', 19.99, 50, 19, TRUE, 1, 6, 6, 56),
    (7, 'Lys en Pot', 'Lys blanc dans un pot élégant', 24.99, 30, 24, TRUE, 1, 6, 7, 78),
    (8, 'Bougeoir en Bois', 'Bougeoir fait à la main en bois', 29.99, 40, 20, TRUE, 1, 4, 7, 89),
    (9, 'Plateau en Bois', 'Plateau élégant pour servir', 39.99, 25, 30, TRUE, 1, 7, 8, 78),
    (10, 'Espresso', 'Café fort et concentré', 2.50, 100, 2, TRUE, 1, 2, 9, 78),
    (11, 'Cappuccino', 'Espresso avec du lait mousseux', 3.50, 80, 3, TRUE, 1, 2, 10, 9),
    (12, 'Croissant', 'Pâtisserie feuilletée et beurrée', 1.99, 150, 1, TRUE, 1, 5, 11, 86),
    (13, 'Grains de café (250g)', 'Grains de café premium pour la préparation à la maison', 12.99, 50, 11, TRUE, 1, 8, 12, 12);

-- OrderStatus's data insertion :
INSERT INTO Order_Status (order_status_id, label, description)
VALUES
    (1, 'Pending', 'Waiting for payment'),
    (2, 'Approved', 'Merchant approval for the order');

-- Order data insertion :
INSERT INTO Orders (order_id, customer_id, commerce_id, order_date, order_status)
VALUES
    (100, 1, 1, '2024-03-01', 'PAID'),
    (101, 1, 1, '2024-03-01', 'PAID'),
    (102, 1, 1, '2024-03-01', 'PAID'),
    (103, 1, 1, '2024-03-01', 'PAID'),
    (104, 1, 1, '2024-03-01', 'PAID'),
    (105, 1, 1, '2024-03-01', 'PAID'),
    (106, 1, 1, '2024-03-01', 'PAID'),
    (107, 1, 1, '2024-03-01', 'PAID'),
    (108, 1, 1, '2024-03-01', 'PAID'),
    (109, 2, 1, '2024-03-01', 'PAID'),
    (110, 2, 1, '2024-03-01', 'PAID');

-- OrderProduct data insertion :
INSERT INTO Order_Product (order_product_id, order_id, quantity)
VALUES
    (1, 100, 10);

-- Insert PointTransaction for Joe
INSERT INTO Point_Transaction (point_transaction_id, fidelity_card_id, transaction_date, type, amount, commerce_id)
VALUES
    (nextval('point_transaction_sequence'),'123e4567-e89b-12d3-a456-426614174000', '2024-01-26', 'EARNED', 20.00, 1),
    (nextval('point_transaction_sequence'),'123e4567-e89b-12d3-a456-426614174000', '2024-01-27', 'SPENT', -15.50, 2),
    (nextval('point_transaction_sequence'),'123e4567-e89b-12d3-a456-426614174000', '2024-01-28', 'EARNED', 18.00, 3),
    (nextval('point_transaction_sequence'),'123e4567-e89b-12d3-a456-426614174000', '2024-01-29', 'EARNED', 50.00, 4),
    (nextval('point_transaction_sequence'),'123e4567-e89b-12d3-a456-426614174000', '2024-01-30', 'SPENT', -20.00, 5);

-- Insert BalanceTransaction for Joe
INSERT INTO Balance_Transaction (balance_transaction_id, fidelity_card_id, transaction_date, type, amount, commerce_id)
VALUES
    (nextval('balance_transaction_sequence'), '123e4567-e89b-12d3-a456-426614174000', '2024-01-26', 'CREDIT', 15.00, NULL),
    (nextval('balance_transaction_sequence'), '123e4567-e89b-12d3-a456-426614174000', '2024-01-27', 'DEBIT', -10.00, 2),
    (nextval('balance_transaction_sequence'), '123e4567-e89b-12d3-a456-426614174000', '2024-01-28', 'CREDIT', 20.00, NULL),
    (nextval('balance_transaction_sequence'), '123e4567-e89b-12d3-a456-426614174000', '2024-01-29', 'DEBIT', -5.50, 4),
    (nextval('balance_transaction_sequence'), '123e4567-e89b-12d3-a456-426614174000', '2024-01-30', 'CREDIT', 30.00, NULL);

-- Sample data for Promotion table
INSERT INTO Promotion (start_date, end_date, description, type, commerce_id, discount_percent, required_items, offered_items)
VALUES
    ('2024-03-01', '2024-05-15', 'Vente de printemps', 'Discount', 1, 20, NULL, NULL),
    ('2024-04-01', '2024-04-30', 'Achetez 3, obtenez-en 1 gratuitement', 'Offer', 8, NULL, 3, 1),
    ('2024-05-01', '2024-05-15', 'Réduction d été', 'Discount', 6, 15,  NULL, NULL),
    ('2023-05-01', '2023-05-15', 'Réduction d été', 'Discount', 6, 20, NULL, NULL);

-- Sample data for Product_Promotion table
INSERT INTO Product_Promotion (promotion_id, product_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 6);

-- Sample data for Product_Promotion_History table
INSERT INTO Product_Promotion_History (promotion_id, product_id)
VALUES
    (4, 6);

