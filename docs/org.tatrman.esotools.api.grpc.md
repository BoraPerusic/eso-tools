# Package: org.tatrman.esotools.api.grpc

## Description
Contains the implementation of the gRPC services defined in the `shared` module.

## Key Classes

### `EsoServiceGrpcImpl`
- **Extends**: `EsoServiceGrpcKt.EsoServiceCoroutineImplBase` (Generated).
- **Responsibility**: Handles incoming gRPC requests by delegating to the appropriate Service layer classes.
- **Methods**:
  - `getProductStock`: Maps request -> `ProductService` call -> `ProductStockResponse`.
  - `getOrderStatus`: Maps request -> `OrderService` call -> `OrderStatusResponse`.
  - `getReturnStatus`: Maps request -> `ReturnService` call -> `ReturnStatusResponse`.
