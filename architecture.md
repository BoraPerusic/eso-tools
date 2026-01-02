# Architecture: ESO Tools

## System Overview

The ESO Tools project aims to expose transactional data from the ESO ERP system (MS SQL Server) via multiple interfaces (REST, GraphQL, gRPC, and MCP). The system consists of two main deployable artifacts: an API Server and an MCP Server.

## Technology Stack

Based on `requirements.md` and `AGENTS.md`:

*   **Language**: Kotlin
*   **Web Framework**: Ktor
*   **Database Access**: JetBrains Exposed (SQL DSL)
*   **Database**: MS SQL Server (Existing ERP Database)
*   **Configuration**: HOCON (`application.conf`)
*   **Build Tool**: Gradle
*   **Testing**: Kotest, Wiremock
*   **Observability**: OpenTelemetry, Prometheus, slf4j

### Constraints
*   **NO Spring Framework**: Strictly forbidden by `requirements.md` and `AGENTS.md`.
*   **Stored Procedures Only**: The application must access data via Stored Procedures in the MS SQL database.

## Architectural Patterns

*   **Modular Monolith / Microservices Lite**: Two distinct services (API and MCP) to separate concerns.
*   **Adapter Pattern**: The API Server acts as an adapter for the legacy ERP Stored Procedures.
*   **Client-Server**: The MCP Server acts as a client to the API Server.

## System Diagrams

### C4 Context Diagram

```mermaid
C4Context
    title System Context Diagram for ESO Tools

    Person(user, "User", "A user interacting with the tools via various clients")
    Person_Ext(llm, "LLM / AI Agent", "An AI agent accessing tools via MCP")

    System(eso_tools_api, "ESO Tools API Server", "Exposes ERP data via REST, GraphQL, and gRPC")
    System(eso_tools_mcp, "ESO Tools MCP Server", "Exposes ERP tools to AI agents")
    
    System_Ext(eso_erp_db, "ESO ERP Database", "MS SQL Server with Stored Procedures")
    System_Ext(auth_provider, "OAuth2 Provider", "Identity Provider (e.g. Keycloak/Entra ID)")

    Rel(user, eso_tools_api, "Uses", "REST/GraphQL (OAuth2 Token)")
    Rel(llm, eso_tools_mcp, "Uses", "MCP Protocol (OAuth2 Token)")
    Rel(eso_tools_mcp, eso_tools_api, "Uses", "gRPC/REST (OAuth2 Token)")
    Rel(eso_tools_api, eso_erp_db, "Invokes Stored Procs", "JDBC/TDS (User/Pass)")
    Rel(eso_tools_api, auth_provider, "Validates Tokens", "HTTPS")
```

### Container Diagram

```mermaid
C4Container
    title Container Diagram for ESO Tools

    Container(api_server, "API Server", "Kotlin, Ktor, Docker", "Core business logic, accesses DB, exposes generic APIs (REST, GraphQL, gRPC)")
    Container(mcp_server, "MCP Server", "Kotlin, Ktor + MCP SDK", "Specialized server for AI interactions, translates MCP requests to API calls")
    
    ContainerDb(mssql, "MS SQL Server", "SQL Server", "Existing ERP Transactional Data")

    Rel(api_server, mssql, "Reads/Executes", "JDBC (Exposed)")
    Rel(mcp_server, api_server, "Fetches Data", "gRPC or REST")
    
    Rel(user, api_server, "Queries", "HTTPS/JSON")
```

## Component Design

### API Server
The API Server is the central gateway to the ERP data. 
*   **Authentication**: OAuth2 (Bearer Token validation).
*   **Layering**:
    *   **Routes/Controllers**: Handle HTTP/gRPC requests.
    *   **Service Layer**: Orchestrates business logic.
    *   **Repository/DAO Layer**: Uses **Exposed** to call Stored Procedures.
*   **Interfaces**:
    *   **REST**: Standard JSON endpoints.
    *   **GraphQL**: For flexible querying.
    *   **gRPC**: High-performance internal API.

### MCP Server
The MCP Server defines the "Tools" available to AI agents.
*   **Authentication**: OAuth2.
*   **Framework**: Ktor + [Kotlin MCP SDK](https://github.com/modelcontextprotocol/kotlin-sdk).
*   **Tools**:
    *   `productStock`
    *   `orderStatus`
    *   `returnStatus`

*   **Implementation**: It does **not** connect to the DB directly. It proxies requests to the API Server. This ensures that business logic and security policies in the API Server are respected.

## Alternatives Considered

### Direct DB Access from MCP Server
*   *Pros*: Lower latency, simpler deployment (single service).
*   *Cons*: Duplication of logic constraints, tighter coupling between AI interface and Legacy DB.
*   *Decision*: **Rejected**. Requirements specify separate artifacts and MCP using API Server as client.

### Spring Boot
*   *Pros*: Huge ecosystem, easy integration with everything.
*   *Cons*: Explicitly forbidden by requirements; "heavier" footprint compared to Ktor (though debatable with AOT).
*   *Decision*: **Rejected** per user constraints.

### gRPC vs REST for Internal Comm (MCP -> API)
*   *Pros (gRPC)*: Type-safe, high performance, defined schema (Protobuf).
*   *Cons (gRPC)*: Slightly more setup than simple HTTP client.
*   *Verdict*: **gRPC** should be preferred for the MCP->API link as per the `AGENTS.md` suggestion ("gRPC for internal communication").
