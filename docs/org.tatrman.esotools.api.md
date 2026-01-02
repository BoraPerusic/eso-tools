# Package: org.tatrman.esotools.api

## Description
The root package for the API Server application. It contains the main entry point and global configurations.

## Key Classes

### `Application.kt`
- **Main Entry Point**: `main()` function starts the embedded Netty server.
- **Module Configuration**: `module()` extension function configures Ktor plugins:
  - **Serialization**: JSON support via kotlinx.serialization.
  - **Routing**: Sets up REST endpoints.
  - **gRPC**: Starts the gRPC server on a separate port (50051) using `ServerBuilder`.

### `Routing.kt`
- **Description**: Defines the HTTP routes for the REST API.
- **Routes**:
  - `GET /products/stock/{id}`: Returns stock for a specific product.
  - `GET /orders/status/{id}`: Returns status for a specific order.
  - `GET /returns/status/{customerId}`: Returns return requests for a customer.
