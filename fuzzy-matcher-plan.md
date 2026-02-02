# Fuzzy Matcher Service - High Level Stage Plan (Rev 2)

This document outlines the high-level plan for implementing the Fuzzy Matcher Service as two separate components.

## Open Questions
1.  **Customers Table**: Included in plan.
2.  **Ports**: `api-fuzzy` (8001), `mcp-fuzzy` (8002).

## Stages

### Stage 1: Database & Environment Preparation
- Update `init.sql` to include `Customers` table and mock data.

### Stage 2: API Service (`api-fuzzy`)
- Create `api-fuzzy` directory.
- Implementation: FastAPI, SQLAlchemy, RapidFuzz.
- **Goal**: Functional REST API at port 8001.

### Stage 3: MCP Service (`mcp-fuzzy`)
- Create `mcp-fuzzy` directory.
- Implementation: MCP Python SDK, HTTP Client.
- **Goal**: MCP Server at port 8002 that delegates to `api-fuzzy`.

### Stage 4: Integration
- Update `docker-compose.yml` to include both services.
- Link `mcp-fuzzy` -> `api-fuzzy` -> `sqlserver`.

### Stage 5: Verification
- Verify API independently.
- Verify MCP tool chain.
