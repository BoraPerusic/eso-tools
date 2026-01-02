USE master;
GO

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'ESODB')
BEGIN
    CREATE DATABASE ESODB;
END
GO

USE ESODB;
GO

-- Tables
CREATE TABLE Products (
    ProductId VARCHAR(50) PRIMARY KEY,
    Name NVARCHAR(100),
    Type VARCHAR(20) -- 'PURCHASED' or 'MANUFACTURED'
);

CREATE TABLE Orders (
    OrderId VARCHAR(50) PRIMARY KEY,
    CustomerId VARCHAR(50),
    Status VARCHAR(20), -- 'OPEN', 'COMPLETED', 'BASKET', 'CANCELLED'
    PaymentStatus VARCHAR(20), -- 'PAID', 'PENDING', 'FAILED'
    DeliveryDate DATE,
    TotalAmount DECIMAL(18, 2)
);

CREATE TABLE Returns (
    ReturnId VARCHAR(50) PRIMARY KEY,
    OriginalOrderId VARCHAR(50),
    CustomerId VARCHAR(50),
    Status VARCHAR(20), -- 'RECEIVED', 'PROCESSING', 'APPROVED', 'REJECTED', 'COMPLETED'
    ResolutionType VARCHAR(20), -- 'REFUND' or 'WITHDRAWAL'
    PlannedActionDate DATE
);

-- Mock Data
INSERT INTO Products (ProductId, Name, Type) VALUES ('P001', 'Widget A', 'PURCHASED'), ('P002', 'Gadget B', 'MANUFACTURED');
INSERT INTO Orders (OrderId, CustomerId, Status, PaymentStatus, DeliveryDate, TotalAmount) VALUES 
('ORD-001', 'CUST-001', 'COMPLETED', 'PAID', '2023-10-01', 100.00),
('ORD-002', 'CUST-001', 'OPEN', 'PENDING', NULL, 50.00);
INSERT INTO Returns (ReturnId, OriginalOrderId, CustomerId, Status, ResolutionType, PlannedActionDate) VALUES 
('RET-001', 'ORD-001', 'CUST-001', 'PROCESSING', 'REFUND', '2023-11-01');

GO

-- Stored Procedures

CREATE OR ALTER PROCEDURE sp_GetProductStock
    @ProductId VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    -- ResultSet 1: Stock Overview
    SELECT 
        100 AS CurrentStock, -- Mock Value
        p.Type AS ProductType
    FROM Products p
    WHERE p.ProductId = @ProductId;

    -- ResultSet 2: Restock Schedule
    SELECT 
        CAST(GETDATE() + 7 AS DATE) AS Date,
        50 AS Quantity,
        'PO-123' AS SourceId,
        'PO' AS Type
    WHERE @ProductId = 'P001'
    UNION ALL
    SELECT 
        CAST(GETDATE() + 14 AS DATE) AS Date,
        20 AS Quantity,
        'MO-456' AS SourceId,
        'MO' AS Type
    WHERE @ProductId = 'P002';
END
GO

CREATE OR ALTER PROCEDURE sp_GetOrderStatus
    @OrderId VARCHAR(50) = NULL,
    @CustomerId VARCHAR(50) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    IF @OrderId IS NOT NULL
        SELECT 
            OrderId, CustomerId, Status, PaymentStatus, DeliveryDate, TotalAmount
        FROM Orders
        WHERE OrderId = @OrderId;
    ELSE IF @CustomerId IS NOT NULL
        SELECT 
            OrderId, CustomerId, Status, PaymentStatus, DeliveryDate, TotalAmount
        FROM Orders
        WHERE CustomerId = @CustomerId AND Status IN ('OPEN', 'BASKET');
END
GO

CREATE OR ALTER PROCEDURE sp_GetReturnStatus
    @CustomerId VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        ReturnId, OriginalOrderId, Status, ResolutionType, PlannedActionDate
    FROM Returns
    WHERE CustomerId = @CustomerId;
END
GO
