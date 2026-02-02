from mcp.server.fastapi import Server
import mcp.types as types
import httpx
import os
import json

API_FUZZY_URL = os.getenv("API_FUZZY_URL", "http://api-fuzzy:8001")

server = Server("fuzzy-matcher")

@server.list_tools()
async def handle_list_tools() -> list[types.Tool]:
    return [
        types.Tool(
            name="fuzzy_search",
            description="Search for products or customers using fuzzy matching",
            inputSchema={
                "type": "object",
                "properties": {
                    "entity": {
                        "type": "string",
                        "enum": ["product", "customer"],
                        "description": "The entity type to search for (product or customer)"
                    },
                    "text": {
                        "type": "string",
                        "description": "The text to match against names"
                    }
                },
                "required": ["entity", "text"]
            }
        )
    ]

@server.call_tool()
async def handle_call_tool(
    name: str, arguments: dict | None
) -> list[types.TextContent | types.ImageContent | types.EmbeddedResource]:
    if name != "fuzzy_search":
        raise ValueError(f"Unknown tool: {name}")

    if not arguments:
        raise ValueError("Missing arguments")

    entity = arguments.get("entity")
    text = arguments.get("text")
    
    async with httpx.AsyncClient() as client:
        try:
            response = await client.post(
                f"{API_FUZZY_URL}/search",
                json={"entity": entity, "text": text},
                timeout=30.0
            )
            response.raise_for_status()
            results = response.json()
            
            # Format nicely
            return [types.TextContent(type="text", text=json.dumps(results, indent=2))]
        except Exception as e:
            return [types.TextContent(type="text", text=f"Error: {str(e)}")]

# Create the FastAPI app
app = server.create_app()
