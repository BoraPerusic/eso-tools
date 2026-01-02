# Architecture Documentation

## Overview
ESO Tools is a system designed to manage Enterprise Service Operations, providing functionalities for tracking product stock, order status, and customer returns.

The system is built using a microservices architecture managed by a Gradle multi-module project.

## Components

### 1. API Server (`api-server`)
- **Role**: The backend core service.
- **Tech Stack**: Kotlin, Ktor (Netty), Exposed (ORM), MS SQL Server.
- **Responsibilities**:
  - Exposes REST API endpoints for general consumption.
  - Exposes gRPC services (Port 50051) for internal communication.
  - Connects to the MS SQL Server database.
  - Executes stored procedures via JDBC and maps results to domain objects.

### 2. MCP Server (`mcp-server`)
- **Role**: A Model Context Protocol (MCP) server that acts as a gateway for LLM agents.
- **Tech Stack**: Kotlin, Ktor (Client), MCP Kotlin SDK.
- **Responsibilities**:
  - Exposes "Tools" to LLMs (e.g., `get_product_stock`, `check_order_status`).
  - Connects to the `api-server` via gRPC to fetch real-time data.
  - Communicates with the host (LLM) via Stdio (Standard Input/Output) transport.

### 3. Shared Module (`shared`)
- **Role**: Shared library for common code.
- **Tech Stack**: Kotlin, Protobuf, gRPC.
- **Responsibilities**:
  - Defines the gRPC service contracts (`eso-tools.proto`).
  - Generates Java/Kotlin stubs for gRPC services and messages.
  - Shared as a dependency by both `api-server` and `mcp-server`.

## Data Flow
1. **User/LLM** asks a question (e.g., "Where is my order?").
2. **MCP Server** receives the request via Stdio and maps it to the `check_order_status` tool.
3. **MCP Server** uses `EsoApiClient` to send a gRPC request to `api-server`.
4. **API Server** receives the gRPC request in `EsoServiceGrpcImpl`.
5. **API Server** calls the `OrderService`, which uses `OrderStatusDAO`.
6. **DAO** executes the `sp_GetOrderStatus` stored procedure in **MS SQL Server**.
7. Data flows back up the chain to the MCP Server, which formats it for the LLM.

## Project Structure
```
eso-tools/
├── api-server/         # Ktor Application (REST + gRPC)
├── mcp-server/         # MCP Server (Stdio)
├── shared/             # Protobuf definitions & generated code
├── gradle/             # Logic for dependency management
├── docker-compose.yml  # Local deployment configuration
└── docs/               # Documentation
```
