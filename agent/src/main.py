from contextlib import asynccontextmanager
from fastapi import FastAPI
from .api.routes import router as api_router
from .mcp_client.client_manager import mcp_client
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup: Connect to MCP
    logger.info("Starting up Agent...")
    async with mcp_client.connect():
        yield
    logger.info("Shutting down Agent...")

app = FastAPI(title="ESO Tools Agent", version="0.1.0", lifespan=lifespan)

app.include_router(api_router)

@app.get("/health")
def health_check():
    return {"status": "ok"}
