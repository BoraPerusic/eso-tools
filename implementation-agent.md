# Implementation Plan: AI Agent (Phase 2)

## Goal
Connect the existing Agent logic to the MCP Server via SSE transport to enable real data access.

## Proposed Changes

### [agent] MCP Client Implementation

#### [NEW] [agent/src/mcp_client/client_manager.py](file:///Users/bora/Dev/eso-tools/agent/src/mcp_client/client_manager.py)
Implement `MCPClientManager` class that:
- Connects to `settings.MCP_SERVER_SSE_URL`.
- Manages the SSE session.
- Exposes a `get_tools()` method to retrieve available tools.
- Exposes a `call_tool()` method to execute tools.

#### [MODIFY] [agent/src/core/agent.py](file:///Users/bora/Dev/eso-tools/agent/src/core/agent.py)
Update the LangGraph agent to:
- Initialize `MCPClientManager` on startup.
- Fetch tools from MCP and bind them to the LLM.
- Replace the "Echo" logic with actual LLM + Tool execution.

#### [MODIFY] [agent/src/main.py](file:///Users/bora/Dev/eso-tools/agent/src/main.py)
- Manage `MCPClientManager` lifecycle (connect on startup, disconnect on shutdown).

## Verification Plan

### Automated Tests
- Mock the SSE endpoint in `pytest` to simulate tool responses without a running server.
- Verify `MCPClientManager` handles connection errors gracefully.

### Manual Verification
Since the real MCP server might not be running locally or via SSE easily without the full container setup, we will:
1.  **Mock Server**: Create a simple python script `mock_sse_server.py` that mimics the MCP SSE protocol for `get_product_stock`.
2.  **Run Agent**: Connect agent to this mock server.
3.  **Test Query**: "What is the stock of product X?" -> Should call the mock tool and return data.
