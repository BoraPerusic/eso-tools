from mcp.server.fastmcp import FastMCP
import uvicorn

# Create a demo server
mcp = FastMCP("Demo Server")

# Add a tool
@mcp.tool()
def get_product_stock(product_id: str) -> str:
    """Get stock for a product"""
    if product_id == "X":
        return "Stock is 100"
    return "Stock is 0"

if __name__ == "__main__":
    # fastmcp exposes sse_app property
    uvicorn.run(mcp.sse_app, port=8080)
