# Implementation Plan: AI Agent (Phase 3 - Kubernetes)

## Goal
Deploy the AI Agent and related services to a local Kubernetes cluster (Rancher Desktop / K3s) instead of Docker Compose.

## Strategy
1.  **Containerization**: Keep the `Dockerfile`. Use `docker` (or an alias compliant with Rancher) to build.
2.  **Orchestration**: Replace `docker-compose.yml` with Kubernetes Manifests (`deployment.yaml`, `service.yaml`).
3.  **Local Testing**:
    - Build images locally.
    - Apply manifests to the local cluster (`kubectl apply -f deployment/k8s`).
    - Port-forward to verify connectivity.

## Proposed Changes

### [deployment] Kubernetes Manifests
#### [NEW] [deployment/k8s/agent-deployment.yaml](file:///Users/bora/Dev/eso-tools/deployment/k8s/agent-deployment.yaml)
Defines the `Deployment` and `Service` for the Agent.
- **Image**: `eso-tools/agent:latest` (imagePullPolicy: Never or IfNotPresent for local dev)
- **Env**: `MCP_SERVER_SSE_URL=http://mcp-server:8080/sse`

#### [NEW] [deployment/k8s/mcp-server-deployment.yaml](file:///Users/bora/Dev/eso-tools/deployment/k8s/mcp-server-deployment.yaml)
Defines `Deployment` and `Service` for the MCP Server.
- **Image**: `eso-tools/mcp-server:latest`
- **Env**: `API_SERVER_HOST=api-server`, `API_SERVER_PORT=50051`

#### [NEW] [deployment/k8s/api-server-deployment.yaml](file:///Users/bora/Dev/eso-tools/deployment/k8s/api-server-deployment.yaml)
Defines `Deployment` and `Service` for the API Server.

#### [NEW] [deployment/k8s/sqlserver-deployment.yaml](file:///Users/bora/Dev/eso-tools/deployment/k8s/sqlserver-deployment.yaml)
Defines `StatefulSet` and `Service` for SQL Server.

### [deployment] Workflow Script
#### [NEW] [deployment/local-dev.sh](file:///Users/bora/Dev/eso-tools/deployment/local-dev.sh)
Helper script to:
1.  Build images.
2.  Apply manifests.
3.  Wait for rollout.

## Verification Plan

### Manual Verification
1.  **Build**:
    ```bash
    # Assuming 'docker' maps to the local Rancher runtime, or we use a specific build command
    docker build -t eso-tools/agent:latest ./agent
    docker build -t eso-tools/mcp-server:latest ./mcp-server
    # ... others
    ```
2.  **Deploy**:
    ```bash
    kubectl apply -f deployment/k8s/
    ```
3.  **Test**:
    ```bash
    kubectl port-forward svc/eso-agent 8000:8000
    curl http://localhost:8000/health
    ```
