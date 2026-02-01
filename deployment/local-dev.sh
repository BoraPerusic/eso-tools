#!/bin/bash
set -e

echo "Building Docker images..."
# Using 'docker' assuming it points to the local Rancher/containerd runtime
docker build -t eso-tools/agent:latest ./agent
 docker build -t eso-tools/mcp-server:latest ./mcp-server
 docker build -t eso-tools/api-server:latest ./api-server
# (Commented out others as I only have 'agent' source code fully ready/scoped for this task, 
# but in real usage we would build all. For now enabling agent build only to test integration phase)

echo "Appying Kubernetes manifests..."
kubectl apply -f deployment/k8s/

echo "Waiting for deployments..."
kubectl rollout status deployment/eso-agent
# kubectl rollout status deployment/eso-mcp-server

echo "Done! You can port-forward to test:"
echo "kubectl port-forward svc/eso-agent 8000:8000"
