# Database Design: Stored Procedure Signatures

This document defines the interface between the API Server and the SQL Server Database. These Stored Procedures (SPs) must be implemented in the database to support the API requirements.

## 1. Product Stock

### `sp_GetProductStock`
Retrieves stock quantity and restocking information for a specific product.

**Inputs:**
*   `@ProductId` (INT/VARCHAR) - The unique identifier of the product.

**Result Sets:**
Returns two result sets.

**Result Set 1: Stock Overview**
*   `CurrentStock` (INT) - Current available quantity.
*   `ProductType` (VARCHAR) - 'PURCHASED' or 'MANUFACTURED'.

**Result Set 2: Restock Schedule**
*   `Date` (DATE) - Expected arrival/completion date.
*   `Quantity` (INT) - Quantity arriving on this date.
*   `SourceId` (VARCHAR) - The Purchase Order ID (if purchased) or Production Order ID (if manufactured).
*   `Type` (VARCHAR) - 'PO' (Purchase Order) or 'MO' (Manufacturing Order).

---

## 2. Order Status

### `sp_GetOrderStatus`
Retrieves status(es) for a specific order or all open orders for a customer.

**Inputs:**
*   `@OrderId` (VARCHAR, Nullable) - Specific order number.
*   `@CustomerId` (VARCHAR, Nullable) - Customer identifier.

**Logic:**
*   If `@OrderId` is provided, return details for that order.
*   If `@OrderId` is NULL and `@CustomerId` is provided, return all **open** orders (including "Basket") for that customer.

**Result Set 1: Orders**
*   `OrderId` (VARCHAR) - Unique Order ID.
*   `CustomerId` (VARCHAR)
*   `Status` (VARCHAR) - 'OPEN', 'COMPLETED', 'BASKET', 'CANCELLED'.
*   `PaymentStatus` (VARCHAR) - 'PAID', 'PENDING', 'FAILED'.
*   `DeliveryDate` (DATE, Nullable) - Actual or Planned delivery date. (Populated if `Status` = 'COMPLETED' or confirmed).
*   `TotalAmount` (DECIMAL)

---

## 3. Return Status

### `sp_GetReturnStatus`
Retrieves return (complaint) status for a specific customer.

**Inputs:**
*   `@CustomerId` (VARCHAR) - Customer identifier.

**Result Set 1: Returns**
*   `ReturnId` (VARCHAR)
*   `OriginalOrderId` (VARCHAR)
*   `Status` (VARCHAR) - 'RECEIVED', 'PROCESSING', 'APPROVED', 'REJECTED', 'COMPLETED'.
*   `ResolutionType` (VARCHAR) - 'REFUND' or 'WITHDRAWAL'.
*   `PlannedActionDate` (DATE) - 
    *   If `ResolutionType` == 'REFUND', this uses the "Planned Refund Date".
    *   If `ResolutionType` == 'WITHDRAWAL', this uses the "Planned Withdrawal Date".
