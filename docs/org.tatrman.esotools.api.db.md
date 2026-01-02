# Package: org.tatrman.esotools.api.db

## Description
Handles database interactions, including connection management and Data Access Objects (DAOs). It uses the Exposed framework and direct JDBC execution for stored procedures.

## Key Classes

### `DatabaseFactory`
- **Responsibility**: Initializes the database connection pool (HikariCP) and connects to MS SQL Server.
- **Source**: `DatabaseFactory.kt`

### `StoredProcedureExecutor`
- **Responsibility**: Helper utility to execute SQL stored procedures.
- **Key Methods**:
  - `executeMulti()`: Handles procedures returning multiple result sets (e.g., `sp_GetProductStock`).
  - `executeSingle()`: Handles procedures returning a single result set.

### DAOs
- **`ProductStockDAO`**: Calls `sp_GetProductStock` and maps results to `ProductStock` objects.
- **`OrderStatusDAO`**: Calls `sp_GetOrderStatus` and maps results to `OrderStatus`.
- **`ReturnStatusDAO`**: Calls `sp_GetReturnStatus` and maps results to `ReturnStatus`.
