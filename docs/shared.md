# Module: shared

## Description
This module contains the Protobuf definitions for the gRPC services and messages. It serves as the contract between the `api-server` (server) and `mcp-server` (client).

## Key Files

### `src/main/proto/eso-tools.proto`
- **Service**: `EsoService` defined with 3 RPC methods.
- **Messages**:
  - `ProductStockRequest` / `ProductStockResponse`
  - `OrderStatusRequest` / `OrderStatusResponse`
  - `ReturnStatusRequest` / `ReturnStatusResponse`
- **Generated Code**:
  - The build process (via `com.google.protobuf` Gradle plugin) generates Java/Kotlin classes in the `org.tatrman.esotools.grpc` package.
  - These generated classes are exported to consumer modules via `api` dependency configuration.
