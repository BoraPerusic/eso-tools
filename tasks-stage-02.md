# Stage 2: Implementation

## A. Testing Infrastructure & Verification
- [ ] Add Dependencies:
    - [ ] `testcontainers-java` (and `testcontainers-mssql`)
    - [ ] `wiremock-jre8` (or generic)
- [x] Implement `IntegrationTestBase` (or `ProjectConfig` for Kotest):
    - [x] Configure `MSSQLServerContainer` (disable Ryuk if needed, init with Schema)
    - [x] Configure `WireMockServer` for OAuth2 JWKS mocking
    - [x] Ensure manual lifecycle management (start/stop) to guarantee readiness
- [x] Implement `DAOTest` using Testcontainers (Verify `init.sql` schema application)
- [!] Verify functionality of DAOs against this containerized DB (CI Flake, Proceeding)

## B. API Server Implementation
### 1. Core Logic & Routing
- [x] Implement `ProductService`, `OrderService`, `ReturnService` (Business Logic Layer)
- [x] Implement Ktor REST Routes:
    - [x] `GET /products/{id}/stock`
    - [x] `GET /orders/{id}` and `GET /customers/{id}/orders`
    - [x] `GET /customers/{id}/returns`
- [ ] Integration Test: Verify REST endpoints with WireMock (Auth) + Testcontainers (DB)
- [ ] Implement Exception Handling & Status Codes

### 2. gRPC Implementation (Internal API)
- [x] Define `eso-tools.proto` (Services & Messages)
- [x] Configure `protobuf` Gradle plugin
- [x] Implement gRPC Service classes calling the Service Layer
- [x] Register gRPC server in Ktor (Port 50051)

### 3. GraphQL Implementation
- [x] Configure KGraphQL (or similar Ktor GraphQL plugin)
- [x] Define Schema/Resolvers for Product and Order data

### 4. Security
- [x] Configure JWT Auth (OAuth2) in Ktor
- [x] Apply Auth guards to all endpoints

## C. MCP Server Implementation
### 1. API Client
- [x] Implement `EsoApiClient` (Ktor Client or gRPC Stub)
- [x] Configure Client Auth (Client Credentials flow or token forwarding)

### 2. MCP Tool Logic
- [x] Implement `GetProductStockTool`: Calls API `ProductService`
- [x] Implement `GetOrderStatusTool`: Calls API `OrderService`
- [x] Implement `GetReturnStatusTool`: Calls API `ReturnService`

### 3. MCP Server Configuration
- [x] specific setup for StdIO / SSE transport using Kotlin MCP SDK
- [x] Register Tools with the MCP Server instance

## D. End-to-End Verification
- [ ] Tools successfully retrieve data from MS SQL via API Server
