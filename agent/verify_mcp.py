import asyncio
from src.mcp_client.client_manager import mcp_client

async def verify():
    print("Connecting...")
    async with mcp_client.connect():
        print("Connected.")
        
        # List tools
        tools = await mcp_client.get_tools()
        print(f"Tools found: {[t.name for t in tools]}")
        
        # Call tool
        print("Calling tool get_product_stock(product_id='X')...")
        result = await mcp_client.call_tool("get_product_stock", {"product_id": "X"})
        print(f"Result: {result.content[0].text}")

if __name__ == "__main__":
    asyncio.run(verify())
