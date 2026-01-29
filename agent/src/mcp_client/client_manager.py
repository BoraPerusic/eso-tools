import contextlib
import logging
from typing import AsyncIterator, Optional

from mcp import ClientSession
from mcp.client.sse import sse_client
from mcp.types import Tool

from ..config import settings

logger = logging.getLogger(__name__)

class MCPClientManager:
    """
    Manages the connection to the remote MCP Server via SSE.
    """
    def __init__(self):
        self._session: Optional[ClientSession] = None
        self._exit_stack: Optional[contextlib.AsyncExitStack] = None

    @contextlib.asynccontextmanager
    async def connect(self) -> AsyncIterator["MCPClientManager"]:
        """
        Establishes the SSE connection and initializes the MCP session.
        Usage:
            async with mcp_client.connect() as client:
                await client.get_tools()
        """
        async with contextlib.AsyncExitStack() as stack:
            logger.info(f"Connecting to MCP Server at {settings.MCP_SERVER_SSE_URL}")
            try:
                # Connect to SSE endpoint
                sse_transport = await stack.enter_async_context(
                    sse_client(settings.MCP_SERVER_SSE_URL)
                )
                
                # Initialize Session over the transport
                self._session = await stack.enter_async_context(
                    ClientSession(sse_transport[0], sse_transport[1])
                )
                
                await self._session.initialize()
                logger.info("MCP Session Initialized")
                
                self._exit_stack = stack
                yield self
            except Exception as e:
                logger.error(f"Failed to connect to MCP Server: {e}")
                raise

    async def get_tools(self) -> list[Tool]:
        """
        Retrieves the list of available tools from the MCP server.
        """
        if not self._session:
            raise RuntimeError("MCP Session not initialized. Use 'async with client.connect():'")
        
        result = await self._session.list_tools()
        return result.tools

    async def call_tool(self, name: str, arguments: dict) -> any:
        """
        Calls a specific tool on the MCP server.
        """
        if not self._session:
            raise RuntimeError("MCP Session not initialized.")
        
        result = await self._session.call_tool(name, arguments=arguments)
        return result

# Global instance (can be managed by Dependency Injection in FastAPI later)
mcp_client = MCPClientManager()
