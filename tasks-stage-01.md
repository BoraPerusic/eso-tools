# Stage 1: Design & Initialization

## A. Project Scaffolding
- [ ] Initialize Git repository
- [ ] Initialize Gradle project (Kotlin DSL)
- [ ] Configure Multi-module structure (`api-server`, `mcp-server`)
- [ ] Add dependencies (Ktor, Exposed, HikariCP, Kotest, Logback, OpenTelemetry)
- [ ] Setup `ktlint` and `detekt`

## B. Database Design
- [x] Design Stored Procedure Signatures (`database-design.md`)
- [ ] (External) User implements SPs in MS SQL

## C. Database Access Implementation (Prototype)
- [ ] Configure `docker-compose.yml` with MS SQL Server (for testing)
- [ ] Create `init.sql` with mock Schema and SPs (matching `database-design.md`)
- [ ] Implement `Exposed` Config
- [ ] Implement `ProductStockDAO` calling `sp_GetProductStock`
- [ ] Implement `OrderStatusDAO` calling `sp_GetOrderStatus`
- [ ] Implement `ReturnStatusDAO` calling `sp_GetReturnStatus`
- [ ] Verify with Integration Tests needed

## D. API Server Skeleton
- [ ] Implement Hello World Ktor route
- [ ] Implement OAuth2 Mock / Skeleton Config

## E. MCP Server Skeleton
- [ ] Initialize Ktor + Kotlin MCP SDK project
- [ ] Implement basic Tool registration skeleton
