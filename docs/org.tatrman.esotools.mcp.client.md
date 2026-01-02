# Package: org.tatrman.esotools.mcp.client

## Description
Contains the client-side logic for communicating with external services, specifically the `api-server`.

## Key Classes

### `EsoApiClient`
- **Role**: A gRPC client wrapper that simplifies communication with the `api-server`.
- **Implementation**:
  - Manages a gRPC `ManagedChannel` (connected to `localhost:50051`).
  - Uses the generated `EsoServiceGrpcKt.EsoServiceCoroutineStub`.
  - Provides suspend functions corresponding to MCP tools: `getProductStock`, `getOrderStatus`, `getReturnStatus`.
  - Handles the construction of Protobuf request objects (`ProductStockRequest`, etc.).
