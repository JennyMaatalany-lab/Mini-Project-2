CREATE DATABASE cedarline_db;
USE cedarline_db;


CREATE TABLE Accounts (
    username      VARCHAR(30) PRIMARY KEY,
    password_hash CHAR(64) NOT NULL,
    department    ENUM('HR', 'CLIENTS', 'INVENTORY', 'ORDERS') NOT NULL
);

INSERT INTO Accounts (username, password_hash, department) VALUES
    ('HR2000',  SHA2('2000HR', 256),  'HR'),
    ('OD3000',  SHA2('3000OD', 256),  'ORDERS'),
    ('INV1000', SHA2('1000INV', 256), 'INVENTORY'),
    ('CL4000',  SHA2('4000CL', 256),  'CLIENTS');


CREATE TABLE Employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50)  NOT NULL,
    address     VARCHAR(150) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    phone       VARCHAR(20)  NOT NULL,
    department  VARCHAR(20)  NOT NULL,
    complaint   VARCHAR(255) NOT NULL DEFAULT ''
) AUTO_INCREMENT = 1001;

INSERT INTO Employees (first_name, last_name, address, email, phone, department, complaint) VALUES
    ('Karim',   'Nassar',      'Hamra St, Jounieh',    'karim.nassar@cedarline.example',    '+961 3 112 233',  'HR',        ''),
    ('Layal',   'Haddad',      'Main Rd, Byblos',      'layal.haddad@cedarline.example',    '+961 70 445 566', 'Finance',   'Late payslip in September'),
    ('Georges', 'Matar',       'Industrial Zone, Zouk','georges.matar@cedarline.example',   '+961 3 778 899',  'Warehouse', ''),
    ('Dana',    'Abou Rjeily', 'Mar Mikhael, Beirut',  'dana.abourjeily@cedarline.example', '+961 71 220 330', 'Sales',     'Office AC not working'),
    ('Elias',   'Sfeir',       'Sin el Fil, Beirut',   'elias.sfeir@cedarline.example',     '+961 76 990 001', 'Logistics', '');


CREATE TABLE Clients (
    client_id      INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    contact_person VARCHAR(100) NOT NULL,
    phone          VARCHAR(20)  NOT NULL,
    email          VARCHAR(100) NOT NULL,
    address        VARCHAR(150) NOT NULL,
    city           VARCHAR(60)  NOT NULL,
    client_type    VARCHAR(40)  NOT NULL
) AUTO_INCREMENT = 2001;

INSERT INTO Clients (name, contact_person, phone, email, address, city, client_type) VALUES
    ('Bustan Bakery Group', 'Rita Khoury',  '+961 9 640 112', 'orders@bustanbakery.example',          'Maameltein Rd',   'Jounieh', 'Wholesale'),
    ('Cedar Ridge Hotel',   'Fadi Aoun',    '+961 9 915 330', 'reservations@cedarridge.example',      'Old Souk Rd',     'Byblos',  'Hotel / Restaurant'),
    ('Mina Pharmacy',       'Nour Salameh', '+961 6 431 207', 'contact@minapharmacy.example',         'Mina Seafront',   'Tripoli', 'Pharmacy'),
    ('Tarazi Hardware',     'Hadi Tarazi',  '+961 7 720 845', 'sales@tarazihardware.example',         'Riad El Solh St', 'Saida',   'Retail'),
    ('Zahle Fresh Market',  'Maya Daher',   '+961 8 801 466', 'info@zahlefresh.example',              'Berdawni Rd',     'Zahle',   'Wholesale');


CREATE TABLE Products (
    product_id    INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)  NOT NULL,
    category      VARCHAR(60)   NOT NULL,
    supplier      VARCHAR(100)  NOT NULL,
    quantity      INT           NOT NULL,
    unit_price    DECIMAL(11,2) NOT NULL,
    reorder_level INT           NOT NULL,
    location      VARCHAR(100)  NOT NULL
) AUTO_INCREMENT = 3001;

INSERT INTO Products (name, category, supplier, quantity, unit_price, reorder_level, location) VALUES
    ('Olive Oil 5L Tin',                'Pantry',     'Koura Olive Co.',   120, 38.50,  30, 'Warehouse A / Shelf 1'),
    ('Cedar Wood Shelf Unit',           'Furniture',  'Bsharri Woodworks',  18, 145.00,  5, 'Warehouse B / Bay 2'),
    ('LED Panel Light 60W',             'Electrical', 'LumaTech Lebanon',  260, 12.75,  50, 'Warehouse A / Shelf 7'),
    ('Thermal Receipt Rolls (50 pack)', 'Stationery', 'PaperLine SARL',     90, 21.00,  20, 'Warehouse A / Shelf 4'),
    ('Steel Storage Crate',             'Warehouse',  'MetalWorks Zouk',    54, 27.90,  15, 'Yard / Row 2');


CREATE TABLE Orders (
    order_id     INT AUTO_INCREMENT PRIMARY KEY,
    client_name  VARCHAR(100)  NOT NULL,
    product_name VARCHAR(100)  NOT NULL,
    quantity     INT           NOT NULL,
    price        DECIMAL(11,2) NOT NULL,
    destination  VARCHAR(150)  NOT NULL,
    order_date   DATE          NOT NULL,
    status       VARCHAR(20)   NOT NULL
) AUTO_INCREMENT = 5001;

INSERT INTO Orders (client_name, product_name, quantity, price, destination, order_date, status) VALUES
    ('Bustan Bakery Group', 'Olive Oil 5L Tin',                 10, 385.00, 'Maameltein Rd, Jounieh',  '2026-10-02', 'Delivered'),
    ('Cedar Ridge Hotel',   'Cedar Wood Shelf Unit',             4, 580.00, 'Old Souk Rd, Byblos',     '2026-10-09', 'Shipped'),
    ('Mina Pharmacy',       'Thermal Receipt Rolls (50 pack)',  12, 252.00, 'Mina Seafront, Tripoli',  '2026-10-12', 'Pending'),
    ('Tarazi Hardware',     'LED Panel Light 60W',              40, 510.00, 'Riad El Solh St, Saida',  '2026-10-15', 'Pending');


SELECT * FROM employees