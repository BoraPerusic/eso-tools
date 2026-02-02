# Implementation Plan: ESO Tools

## Strategy
We will follow a phased approach, building the core API Server first to establish the data access layer, and then building the MCP Server as a client to it. This ensures we have a stable foundation before adding the AI interface.

## Phases

### Phase 1: Project Initialization & Scaffolding
*   **Goal**: Set up the repository structure, build system (Gradle), and basic Docker environment.
*   **Tasks**:
    *   Initialize Gradle project with Kotlin DSL.
    *   Configure multi-module or separate directory structure for `api-server` and `mcp-server`.
    *   Set up `AGENTS.md` recommended stack: Kotest, Spotless/Ktlint, Detekt.
    *   Create `docker-compose.yml` for local development (MS SQL Server or Edge equivalent for testing).

### Phase 2: Design & Database Layer
*   **Goal**: Design Stored Procedures and Connect to MS SQL Server.
*   **Tasks**:
    *   **Design Stored Procedures**: Define signatures (Inputs/Outputs) in `database-design.md`. (Pre-requisite for implementation).
    *   Configure HikariCP and JDBC driver for MS SQL.
    *   Implement `Exposed` mappings / execution logic for the designed Stored Procedures.
    *   **Test**: Integration tests using Testcontainers or a local DB.

### Phase 3: API Server - Interface Layer
*   **Goal**: Expose data via REST, GraphQL, and gRPC with OAuth2 protection.
*   **Tasks**:
    *   **Security**: Implement OAuth2 Bearer Token validation in Ktor.
    *   **REST (Ktor)**: Implement routes for `productStock`, `orderStatus`, `returnStatus`.
    *   **gRPC**: Define Protobuf messages and services. Implement gRPC server in Ktor.
    *   **GraphQL**: Setup Ktor GraphQL plugin and schema.
    *   **Test**: Component tests with MockK/Wiremock.

### Phase 4: MCP Server Implementation
*   **Goal**: Build the MCP Server to expose tools to AI agents using Kotlin MCP SDK.
*   **Tasks**:
    *   Setup Ktor server with [Kotlin MCP SDK](https://github.com/modelcontextprotocol/kotlin-sdk).
    *   Implement Tool definitions: `productStock`, `orderStatus`, `returnStatus`.
    *   **Security**: Implement OAuth2 validation.
    *   Implement API Client: The MCP server will call the API Server (via gRPC preferred).

### Phase 5: Containerization & Integration
*   **Goal**: Dockerize both applications and verify end-to-end integration.
*   **Tasks**:
    *   Create `Dockerfile` for API Server.
    *   Create `Dockerfile` for MCP Server.
    *   Update `docker-compose.yml` to run both services + DB.
    *   Verify communication.

---

## Open Questions

### 1. Database & Testing
*   Do we have a dev/test database instance available, or should I rely solely on mocking and Testcontainers?
*   (Self-Answered): I will design the SPs now, and you will implement them.

### 2. OAuth2 Details
*   What is the Identity Provider (IdP)? (e.g., Keycloak, Azure AD).
*   For local dev, I will assume a mocked token or a local Keycloak container if needed.

