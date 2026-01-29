import os
from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    AZURE_OPENAI_API_KEY: str = "mock-key"
    AZURE_OPENAI_ENDPOINT: str = "https://mock-endpoint.openai.azure.com/"
    AZURE_OPENAI_DEPLOYMENT_NAME: str = "gpt-4o"
    MCP_SERVER_SSE_URL: str = "http://localhost:8080/sse"

    class Config:
        env_file = ".env"

settings = Settings()
