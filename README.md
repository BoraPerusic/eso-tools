# ESO Tools

A system for managing Enterprise Service Operations (Product Stock, Order Status, Returns) using a microservices architecture. It consists of an API Server (REST/gRPC) and an MCP (Model Context Protocol) Server for LLM integration.

## Architecture
See [docs/architecture.md](docs/architecture.md) for a detailed overview.
Package-level documentation is available in the `docs/` directory.

## Prerequisites
- Java 17+
- Docker & Docker Compose (or Podman)
- Kubernetes Cluster (Optional, for K8s deployment)

## Building the Project
Build all modules and create distribution packages:
```bash
./gradlew installDist
```

## Running Locally (Docker Compose)
To run the full stack (SQL Server, API Server, MCP Server) locally:

1.  **Build the images**:
    ```bash
    docker compose build
    ```
2.  **Start the services**:
    ```bash
    docker compose up -d
    ```
3.  **Verify**:
    - **API Server**: http://localhost:8080/products/stock/P001
    - **SQL Server**: Port 1433
    - **MCP Server**: Runs in background (connected to API Server). To use it interactively via Stdio with an LLM, see the "Manual Run" section.

## Running with Podman Pods
You can use Podman to run the Kubernetes manifests locally as pods.

1.  **Build Images**:
    ```bash
    podman build -t eso-api-server:latest -f api-server/Dockerfile .
    podman build -t eso-mcp-server:latest -f mcp-server/Dockerfile .
    ```
2.  **Play Kube Manifests**:
    ```bash
    # Start SQL Server
    podman play kube deployment/k8s/mssql.yaml
    
    # Start API Server
    podman play kube deployment/k8s/api-server.yaml

    # Start MCP Server
    podman play kube deployment/k8s/mcp-server.yaml
    ```

## Deploying to Kubernetes

1.  **Build and Push Images** (or load into your cluster's registry):
    ```bash
    docker build -t eso-api-server:latest -f api-server/Dockerfile .
    docker build -t eso-mcp-server:latest -f mcp-server/Dockerfile .
    # (Optional) docker push ...
    ```
2.  **Apply Manifests**:
    ```bash
    # Apply Database
    kubectl apply -f deployment/k8s/mssql.yaml
    
    # Apply API Server
    kubectl apply -f deployment/k8s/api-server.yaml
    
    # Apply MCP Server
    kubectl apply -f deployment/k8s/mcp-server.yaml
    ```

## Using the MCP Server with LLM Clients (e.g., Claude Desktop)
To use the MCP server with an LLM client that supports Stdio transport:

1.  Ensure the **API Server** is running (e.g., via Docker Compose).
2.  Configure your client to run the MCP Server binary:
    ```json
    {
      "mcpServers": {
        "eso-tools": {
          "command": "java",
          "args": [
            "-jar",
            "/absolute/path/to/eso-tools/mcp-server/build/libs/mcp-server-all.jar" 
            # Note: You may need to configure the shadowJar task or verify the jar path.
            # Alternatively use the installDist script:
            # "/absolute/path/to/eso-tools/mcp-server/build/install/mcp-server/bin/mcp-server"
          ],
           "env": {
            "API_SERVER_HOST": "localhost",
            "API_SERVER_PORT": "50051"
          }
        }
      }
    }
    ```