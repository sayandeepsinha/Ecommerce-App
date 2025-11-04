-- Sample Products for E-Commerce Application
-- Run this in pgAdmin Query Tool to populate your database with sample data

-- Clear existing products (optional - uncomment if you want to start fresh)
-- DELETE FROM products;
-- ALTER SEQUENCE products_id_seq RESTART WITH 1;

-- Insert Electronics
INSERT INTO products (name, price, stock, image_url, category, description) VALUES
('MacBook Pro 14"', 1999.99, 15, 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400', 'Electronics', 'Powerful laptop with M3 chip, 16GB RAM, perfect for developers and creators'),
('iPhone 15 Pro', 999.99, 30, 'https://images.unsplash.com/photo-1510557880182-3d4d3cba35a5?w=400', 'Electronics', 'Latest iPhone with A17 Pro chip and titanium design'),
('Samsung 65" 4K TV', 1299.99, 8, 'https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=400', 'Electronics', 'Crystal clear 4K display with HDR support'),
('Sony WH-1000XM5', 349.99, 25, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400', 'Electronics', 'Industry-leading noise cancelling wireless headphones'),
('iPad Air', 599.99, 20, 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400', 'Electronics', 'Lightweight and powerful tablet with M1 chip'),
('Apple Watch Series 9', 429.99, 35, 'https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=400', 'Electronics', 'Advanced health and fitness tracking smartwatch');

-- Insert Home & Kitchen
INSERT INTO products (name, price, stock, image_url, category, description) VALUES
('Ninja Air Fryer', 129.99, 40, 'https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400', 'Home & Kitchen', 'Large capacity air fryer for healthy cooking'),
('Keurig Coffee Maker', 159.99, 30, 'https://images.unsplash.com/photo-1517668808822-9ebb02f2a0e6?w=400', 'Home & Kitchen', 'Single-serve coffee maker with multiple brew sizes'),
('Instant Pot Duo', 89.99, 50, 'https://images.unsplash.com/photo-1585515320310-259814833e62?w=400', 'Home & Kitchen', '7-in-1 electric pressure cooker'),
('Dyson V15 Vacuum', 649.99, 12, 'https://images.unsplash.com/photo-1558317374-067fb5f30001?w=400', 'Home & Kitchen', 'Powerful cordless vacuum with laser detection'),
('KitchenAid Mixer', 379.99, 18, 'https://images.unsplash.com/photo-1578849278619-e73505e9610f?w=400', 'Home & Kitchen', 'Professional stand mixer for baking');

-- Insert Sports & Fitness
INSERT INTO products (name, price, stock, image_url, category, description) VALUES
('Nike Air Zoom Pegasus', 130.00, 60, 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400', 'Sports', 'Responsive running shoes with Zoom Air cushioning'),
('Adidas Ultraboost', 180.00, 45, 'https://images.unsplash.com/photo-1608231387042-66d1773070a5?w=400', 'Sports', 'Premium running shoes with Boost technology'),
('Yoga Mat Pro', 49.99, 100, 'https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f?w=400', 'Sports', 'Extra thick, non-slip yoga mat'),
('Dumbbells Set', 199.99, 25, 'https://images.unsplash.com/photo-1638536532686-d610adfc8e5c?w=400', 'Sports', 'Adjustable dumbbell set 5-50 lbs'),
('Resistance Bands', 24.99, 80, 'https://images.unsplash.com/photo-1598289431512-b97b0917affc?w=400', 'Sports', 'Set of 5 resistance bands for home workouts');

-- Insert Fashion & Accessories
INSERT INTO products (name, price, stock, image_url, category, description) VALUES
('Leather Backpack', 89.99, 35, 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400', 'Accessories', 'Premium leather backpack for work and travel'),
('Aviator Sunglasses', 159.99, 50, 'https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=400', 'Accessories', 'Classic polarized sunglasses'),
('Canvas Tote Bag', 39.99, 70, 'https://images.unsplash.com/photo-1590874103328-eac38a683ce7?w=400', 'Accessories', 'Durable canvas tote for everyday use'),
('Leather Wallet', 49.99, 60, 'https://images.unsplash.com/photo-1627123424574-724758594e93?w=400', 'Accessories', 'Genuine leather bifold wallet'),
('Winter Jacket', 199.99, 30, 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=400', 'Fashion', 'Insulated waterproof winter jacket');

-- Insert Books & Stationery
INSERT INTO products (name, price, stock, image_url, category, description) VALUES
('Moleskine Notebook', 24.99, 100, 'https://images.unsplash.com/photo-1531346878377-a5be20888e57?w=400', 'Books', 'Classic hardcover notebook, ruled pages'),
('Fountain Pen Set', 79.99, 40, 'https://images.unsplash.com/photo-1583485088034-697b5bc54ccd?w=400', 'Books', 'Premium fountain pen with ink set'),
('Desk Organizer', 34.99, 55, 'https://images.unsplash.com/photo-1611532736590-c4ca5ed37f7c?w=400', 'Books', 'Bamboo desk organizer with multiple compartments'),
('LED Desk Lamp', 45.99, 45, 'https://images.unsplash.com/photo-1513506003901-1e6a229e2d15?w=400', 'Home & Kitchen', 'Adjustable LED desk lamp with USB charging');

-- Verify the data was inserted
SELECT COUNT(*) as total_products FROM products;

-- View products by category
SELECT category, COUNT(*) as count 
FROM products 
GROUP BY category 
ORDER BY count DESC;

-- Display all products with basic info
SELECT id, name, price, stock, category 
FROM products 
ORDER BY id;
