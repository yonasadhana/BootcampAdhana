DROP DATABASE IF EXISTS car_dealership;

CREATE DATABASE car_dealership;

USE car_dealership;

CREATE TABLE dealerships (
    dealership_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    phone VARCHAR(12) NOT NULL
);

CREATE TABLE vehicles (
    VIN VARCHAR(17) PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    color VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    sold BOOLEAN DEFAULT FALSE
);

CREATE TABLE inventory (
    dealership_id INT,
    VIN VARCHAR(17),
    PRIMARY KEY (dealership_id, VIN),
    FOREIGN KEY (dealership_id) REFERENCES dealerships(dealership_id),
    FOREIGN KEY (VIN) REFERENCES vehicles(VIN)
);

CREATE TABLE sales_contracts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    VIN VARCHAR(17),
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100),
    customer_phone VARCHAR(12),
    sale_date DATE NOT NULL,
    sale_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (VIN) REFERENCES vehicles(VIN)
);

CREATE TABLE lease_contracts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    VIN VARCHAR(17),
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100),
    customer_phone VARCHAR(12),
    lease_start_date DATE NOT NULL,
    lease_end_date DATE NOT NULL,
    monthly_payment DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (VIN) REFERENCES vehicles(VIN)
);

INSERT INTO dealerships (name, address, phone) VALUES
('Premier Auto Sales', '123 Main St, Houston, TX', '713-555-0101'),
('Luxury Motors', '456 Oak Ave, Dallas, TX', '214-555-0202'),
('City Cars', '789 Elm Dr, Austin, TX', '512-555-0303'),
('Highway Auto', '321 Pine Rd, San Antonio, TX', '210-555-0404');

INSERT INTO vehicles (VIN, make, model, year, color, type, price, sold) VALUES
('1HGCM82633A123456', 'Honda', 'Accord', 2023, 'Silver', 'Sedan', 28500.00, FALSE),
('1FMCU9GD2KUB12345', 'Ford', 'Escape', 2023, 'Blue', 'SUV', 32000.00, FALSE),
('1N4AL3AP4DC270894', 'Nissan', 'Altima', 2022, 'Black', 'Sedan', 26000.00, TRUE),
('JM1CW2BL3E0152487', 'Mazda', 'CX-5', 2023, 'Red', 'SUV', 30500.00, FALSE),
('3VW2B7AJ2HM321123', 'Volkswagen', 'Jetta', 2023, 'White', 'Sedan', 24500.00, FALSE),
('WBAJB9C51KB625482', 'BMW', 'X3', 2023, 'Gray', 'SUV', 45000.00, FALSE),
('2T1BURHE3KC186543', 'Toyota', 'Corolla', 2023, 'Blue', 'Sedan', 23000.00, TRUE),
('5NPD84LF7KH448123', 'Hyundai', 'Elantra', 2023, 'Silver', 'Sedan', 22000.00, FALSE),
('1FA6P8CF3K5123456', 'Ford', 'Mustang', 2023, 'Red', 'Coupe', 38000.00, FALSE),
('KM8J3CA46KU892341', 'Hyundai', 'Tucson', 2023, 'Black', 'SUV', 29500.00, FALSE),
('3MZBM1U74HM161234', 'Mazda', '3', 2022, 'White', 'Sedan', 24000.00, TRUE),
('5YJ3E1EB5KF321098', 'Tesla', 'Model 3', 2023, 'White', 'Sedan', 42000.00, FALSE),
('1G1ZB5ST7HF198765', 'Chevrolet', 'Malibu', 2022, 'Gray', 'Sedan', 25500.00, FALSE),
('3FA6P0HD2KR223344', 'Ford', 'Fusion', 2022, 'Blue', 'Sedan', 24800.00, TRUE),
('WAUFFAFL5CN123789', 'Audi', 'A4', 2023, 'Black', 'Sedan', 41000.00, FALSE);

INSERT INTO inventory (dealership_id, VIN) VALUES
(1, '1HGCM82633A123456'),
(1, '1FMCU9GD2KUB12345'),
(1, '3VW2B7AJ2HM321123'),
(1, '5NPD84LF7KH448123'),
(2, 'WBAJB9C51KB625482'),
(2, '5YJ3E1EB5KF321098'),
(2, 'WAUFFAFL5CN123789'),
(2, '1FA6P8CF3K5123456'),
(3, 'JM1CW2BL3E0152487'),
(3, 'KM8J3CA46KU892341'),
(3, '1G1ZB5ST7HF198765'),
(4, '1N4AL3AP4DC270894'),
(4, '2T1BURHE3KC186543'),
(4, '3MZBM1U74HM161234'),
(4, '3FA6P0HD2KR223344');

INSERT INTO sales_contracts (VIN, customer_name, customer_email, customer_phone, sale_date, sale_price) VALUES
('1N4AL3AP4DC270894', 'John Smith', 'john.smith@email.com', '555-1234', '2024-01-15', 25500.00),
('2T1BURHE3KC186543', 'Sarah Johnson', 'sarah.j@email.com', '555-2345', '2024-02-20', 22500.00),
('3MZBM1U74HM161234', 'Mike Davis', 'mdavis@email.com', '555-3456', '2024-03-10', 23500.00),
('3FA6P0HD2KR223344', 'Emily Brown', 'emily.brown@email.com', '555-4567', '2024-03-25', 24000.00);

INSERT INTO lease_contracts (VIN, customer_name, customer_email, customer_phone, lease_start_date, lease_end_date, monthly_payment) VALUES
('1HGCM82633A123456', 'Robert Wilson', 'rwilson@email.com', '555-5678', '2024-01-01', '2027-01-01', 350.00),
('WBAJB9C51KB625482', 'Lisa Anderson', 'lisa.a@email.com', '555-6789', '2024-02-01', '2027-02-01', 550.00);


USE car_dealership;

-- Query to retrieve all dealerships
-- This will return all columns for every dealership in the database
SELECT * FROM dealerships;

USE car_dealership;

-- Query to find all vehicles for a specific dealership
-- This uses dealership_id = 1 (Premier Auto Sales)
-- The query joins vehicles and inventory tables to get all vehicles at the specified dealership
SELECT v.* 
FROM vehicles v
INNER JOIN inventory i ON v.VIN = i.VIN
WHERE i.dealership_id = 1;

USE car_dealership;

-- Query to find a specific car by its VIN
-- This searches for VIN '1HGCM82633A123456' (Honda Accord)
-- Returns all vehicle details for the specified VIN
SELECT * 
FROM vehicles 
WHERE VIN = '1HGCM82633A123456';

USE car_dealership;

-- Query to find which dealership has a specific vehicle by VIN
-- This searches for the dealership that has VIN '1HGCM82633A123456'
-- The query joins dealerships and inventory tables to find the location
SELECT d.*
FROM dealerships d
INNER JOIN inventory i ON d.dealership_id = i.dealership_id
WHERE i.VIN = '1HGCM82633A123456';

USE car_dealership;

-- Query to find all dealerships that have a specific type of car
-- This searches for dealerships with Red Ford Mustangs
-- Uses DISTINCT to avoid duplicate dealership entries if they have multiple matching vehicles
SELECT DISTINCT d.*
FROM dealerships d
INNER JOIN inventory i ON d.dealership_id = i.dealership_id
INNER JOIN vehicles v ON i.VIN = v.VIN
WHERE v.color = 'Red' 
AND v.make = 'Ford' 
AND v.model = 'Mustang';

USE car_dealership;

-- Query to get all sales information for a specific dealer within a date range
-- This retrieves sales for dealership_id = 4 (Highway Auto) for the year 2024
-- The query joins multiple tables to include vehicle details and dealership name with each sale
SELECT sc.*, v.make, v.model, v.year, v.color, d.name AS dealership_name
FROM sales_contracts sc
INNER JOIN vehicles v ON sc.VIN = v.VIN
INNER JOIN inventory i ON v.VIN = i.VIN
INNER JOIN dealerships d ON i.dealership_id = d.dealership_id
WHERE d.dealership_id = 4
AND sc.sale_date BETWEEN '2024-01-01' AND '2024-12-31';