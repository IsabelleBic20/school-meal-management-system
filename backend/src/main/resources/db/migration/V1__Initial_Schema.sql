-- V1__Initial_Schema.sql

CREATE TABLE schools (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL UNIQUE,
    address NVARCHAR(500) NOT NULL,
    student_count INT NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);

CREATE TABLE products (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL UNIQUE,
    unit NVARCHAR(50) NOT NULL,
    stock_quantity INT NOT NULL,
    expiration_date DATE,
    low_stock_threshold INT NOT NULL DEFAULT 10,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);

CREATE TABLE menus (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);

CREATE TABLE menu_products (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    menu_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT FK_MenuProducts_Menu FOREIGN KEY (menu_id) REFERENCES menus(id) ON DELETE CASCADE,
    CONSTRAINT FK_MenuProducts_Product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT UQ_MenuProducts UNIQUE (menu_id, product_id)
);

CREATE TABLE distributions (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    school_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    delivery_date DATE NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_Distributions_School FOREIGN KEY (school_id) REFERENCES schools(id) ON DELETE CASCADE,
    CONSTRAINT FK_Distributions_Product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE INDEX IDX_MenuProducts_MenuId ON menu_products(menu_id);
CREATE INDEX IDX_MenuProducts_ProductId ON menu_products(product_id);
CREATE INDEX IDX_Distributions_SchoolId ON distributions(school_id);
CREATE INDEX IDX_Distributions_ProductId ON distributions(product_id);
CREATE INDEX IDX_Distributions_DeliveryDate ON distributions(delivery_date);
CREATE INDEX IDX_Products_ExpirationDate ON products(expiration_date);
CREATE INDEX IDX_Products_StockThreshold ON products(stock_quantity, low_stock_threshold);
