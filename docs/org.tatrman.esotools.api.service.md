# Package: org.tatrman.esotools.api.service

## Description
The Service Layer containing business logic. It acts as an intermediary between the Controllers/gRPC handlers and the DAOs.

## Key Classes

### `ProductService`
- **Role**: Manages product-related operations.
- **Methods**: `getProductStock(id: String)` - Retrieves stock info via `ProductStockDAO`.

### `OrderService`
- **Role**: Manages order-related operations.
- **Methods**: `getOrder(id: String)` - Retrieves order status via `OrderStatusDAO`.

### `ReturnService`
- **Role**: Manages return-related operations.
- **Methods**: `getCustomerReturns(customerId: String)` - Retrieves returns via `ReturnStatusDAO`.
