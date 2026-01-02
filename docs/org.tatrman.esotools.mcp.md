# Package: org.tatrman.esotools.mcp

## Description
The root package for the MCP Server. Defines the server entry point (Stdio transport) and the MCP Tools implementation.

## Key Classes

### `Application.kt`
- **Entry Point**: Configures the `Server` instance using `io.modelcontextprotocol.kotlin.sdk`.
- **Configuration**:
  - Connects using `StdioServerTransport` (reads from `System.in`, writes to `System.out`).
  - Registers `ServerCapabilities` (specifically, tool support).
  - Instantiates `Tools` and registers them with the server.
  - Starts the session with `createSession()` and listening loop `start()`.

### `Tools.kt`
- **Role**: Defines the available MCP Tools and their handlers.
- **Implemented Tools**:
  - `get_product_stock`: Requires `productId`. Calls `EsoApiClient.getProductStock`.
  - `check_order_status`: Requires `orderId`. Calls `EsoApiClient.getOrderStatus`.
  - `check_return_status`: Requires `customerId`. Calls `EsoApiClient.getReturnStatus`.
- **Integration**: Uses `EsoApiClient` to fetch data from the `api-server` via gRPC.
