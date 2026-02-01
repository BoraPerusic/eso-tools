# Stage 1: Design & Initialization

## A. Project Scaffolding
- [x] Initialize Git repository
- [x] Initialize Gradle project (Kotlin DSL)
- [x] Configure Multi-module structure (`api-server`, `mcp-server`)
- [x] Add dependencies (Ktor, Exposed, HikariCP, Kotest, Logback, OpenTelemetry)
- [x] Setup `ktlint` and `detekt`

## B. Database Design
- [x] Design Stored Procedure Signatures (`database-design.md`)
- [ ] (External) User implements SPs in MS SQL

## C. Database Access Implementation (Prototype)
- [x] Configure `docker-compose.yml` with MS SQL Server (for testing)
- [x] Create `init.sql` with mock Schema and SPs (matching `database-design.md`)
- [x] Implement `Exposed` Config

- [x] Implement `ProductStockDAO` calling `sp_GetProductStock`
- [x] Implement `OrderStatusDAO` calling `sp_GetOrderStatus`
- [x] Implement `ReturnStatusDAO` calling `sp_GetReturnStatus`
- [ ] Verify with Integration Tests needed

## D. API Server Skeleton
- [x] Implement Hello World Ktor route
- [x] Implement OAuth2 Mock / Skeleton Config (Placeholder in module)

## E. MCP Server Skeleton
- [x] Initialize Ktor + Kotlin MCP SDK project
- [x] Implement basic Tool registration skeleton
